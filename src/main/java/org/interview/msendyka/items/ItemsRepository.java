package org.interview.msendyka.items;

import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
class ItemsRepository {

  private final ItemsSqlRepository itemsSqlRepository;

  void save(Item item) {
    itemsSqlRepository.save(toEntity(item));
  }

  private ItemEntity toEntity(Item item) {
    return new ItemEntity(item.getId().id(), item.getName().getName(), item.getSku().getSku());
  }

  Optional<Item> get(String id) {
    Optional<ItemEntity> dto = itemsSqlRepository.findById(id);
    return dto.map(this::toItem);
  }

  private Item toItem(ItemEntity itemEntity) {
    return new Item(
        new ItemId(itemEntity.getId()),
        ItemName.fromString(itemEntity.getName()),
        Sku.fromString(itemEntity.getSku()));
  }
}
