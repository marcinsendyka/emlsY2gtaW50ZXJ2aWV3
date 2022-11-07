package org.interview.msendyka.items;

import static org.interview.msendyka.items.TestObjectFactory.SKU;
import static org.interview.msendyka.items.TestObjectFactory.item;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Optional;
import org.interview.msendyka.items.ItemController.NewItemDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

@SpringBootTest
@AutoConfigureMockMvc
class ItemsControllerTest {

  @Autowired private MockMvc mockMvc;

  @MockBean private ItemService service;

  @Test
  void shouldSaveItem() throws Exception {
    // given
    Sku sku = SKU;
    String payload =
        """
        {
         	"name": "name",
         	"sku": "%s"
        }
        """
            .formatted(sku.getSku());
    when(service.save(new NewItemDto("name", sku.getSku()))).thenReturn(item(sku));

    // when
    ResultActions perform =
        mockMvc.perform(post("/items").content(payload).contentType(MediaType.APPLICATION_JSON));

    // then
    perform
        .andExpect(status().isOk())
        .andExpect(
            content()
                .json(
                    """
                {
                  "id": "id",
             	    "name": "name",
             	    "sku": "%s"
            }
                """
                        .formatted(sku.getSku())));
  }

  @Test
  void shouldReturn400OnInvalidItemName() throws Exception {
    // given
    String payload =
        """
        {
         	"name": "",
         	"sku": "123"
        }
        """;

    // when
    ResultActions perform =
        mockMvc.perform(post("/items").content(payload).contentType(MediaType.APPLICATION_JSON));

    // then
    perform.andExpect(status().isBadRequest());
  }

  @Test
  void shouldReturn400OnInvalidSku() throws Exception {
    // given
    String payload = """
        {
         	"name": "name"
        }
        """;

    // when
    ResultActions perform =
        mockMvc.perform(post("/items").content(payload).contentType(MediaType.APPLICATION_JSON));

    // then
    perform.andExpect(status().isBadRequest());
  }

  @Test
  void shouldGetItem() throws Exception {
    // given
    when(service.get("id")).thenReturn(Optional.of(item(SKU)));

    // when
    ResultActions perform = mockMvc.perform(get("/items/id"));

    // then
    perform
        .andExpect(status().isOk())
        .andExpect(
            content()
                .json(
                    """
                    {
                      "id": "id",
                       "name": "name",
                       "sku": "%s"
                }
                    """
                        .formatted(SKU.getSku())));
  }

  @Test
  void shouldReturn404WhenItemDoesNotExist() throws Exception {
    // given
    when(service.get(anyString())).thenReturn(Optional.empty());

    // when
    ResultActions perform = mockMvc.perform(get("/items/non-existing-id"));

    // then
    perform.andExpect(status().isNotFound());
  }
}
