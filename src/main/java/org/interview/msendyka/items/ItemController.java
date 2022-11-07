package org.interview.msendyka.items;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping(value = "/items", produces = MediaType.APPLICATION_JSON_VALUE)
class ItemController {

  private final ItemService itemService;

  @GetMapping("/{id}")
  ItemDto get(@PathVariable String id) {
    return itemService
        .get(id)
        .map(this::toDto)
        .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "item not found"));
  }

  @PostMapping
  @ResponseBody ItemDto newItem(@RequestBody @Valid NewItemDto item) {
    log.info("Received new item: {}", item);
    return toDto(itemService.save(item));
  }

  private ItemDto toDto(Item item) {
    return new ItemDto(
        item.getId().id(),
        item.getName().getName(),
        item.getSku().getSku()); // TODO use map-struct or improve in other way
  }

  record ItemDto(String id, String name, String sku) {}

  record NewItemDto(
      @NotBlank(message = "name not be blank") String name,
      @NotBlank(message = "sku not be blank") String sku) {}
}
