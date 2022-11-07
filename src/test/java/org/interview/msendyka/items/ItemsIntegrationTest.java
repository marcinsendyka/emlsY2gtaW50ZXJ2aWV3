package org.interview.msendyka.items;

import static org.assertj.core.api.Assertions.assertThat;

import javax.inject.Inject;
import org.interview.msendyka.items.ItemController.ItemDto;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
class ItemsIntegrationTest {

  @LocalServerPort private int port;

  private final TestRestTemplate restTemplate = new TestRestTemplate();

  @Inject private ItemsSqlRepository itemsSqlRepository;

  @Test
  void postShouldSave() {
    // given
    String payload =
        """
        {
        	"name": "name",
        	"sku": "sku456"
        }
        """;
    HttpHeaders headers = new HttpHeaders();
    headers.set("Content-type", "Application/json");
    HttpEntity<String> request = new HttpEntity<>(payload, headers);
    // when

    ResponseEntity<ItemDto> result =
        restTemplate.postForEntity(
            getUrl("items/"), request, ItemDto.class);

    // then
    assertThat(result.getStatusCodeValue()).isEqualTo(200);
    ItemDto dto = result.getBody();
    assertThat(dto.name()).isEqualTo("name");
    assertThat(dto.sku()).isEqualTo("sku456");
    Iterable<ItemEntity> entities = itemsSqlRepository.findAll();
    assertThat(entities).hasSize(1);
    assertThat(entities).extracting("name").containsOnly("name");
    assertThat(entities).extracting("sku").containsOnly("sku456");
  }

  @Test
  void getShouldReturnExistingItem() {
    // given
    itemsSqlRepository.save(new ItemEntity("id", "name", "123456"));

    // when
    ResponseEntity<ItemDto> result =
        restTemplate.getForEntity(getUrl("items/id"), ItemDto.class);

    // then
    assertThat(result.getStatusCodeValue()).isEqualTo(200);
    assertThat(result.getBody()).isEqualTo(new ItemDto("id", "name", "123456"));
  }

  private String getUrl(String endpoint) {
    return "http://localhost:%d/%s/".formatted(port, endpoint);
  }
}
