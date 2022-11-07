package org.interview.msendyka.items;

import java.util.Optional;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.interview.msendyka.items.ItemController.NewItemDto;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
class ItemService {

  private final ItemsRepository itemsRepository;

  Item save(NewItemDto itemDto) {
    Item item =
        new Item(assignId(), ItemName.fromString(itemDto.name()), Sku.fromString(itemDto.sku()));
    itemsRepository.save(item);
    return item;
  }

  private ItemId assignId() {
    return new ItemId(UUID.randomUUID().toString());
  }

  Optional<Item> get(String id) {
    return itemsRepository.get(id);
  }
}
