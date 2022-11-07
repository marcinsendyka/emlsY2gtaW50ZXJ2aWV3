package org.interview.msendyka.items;

import lombok.Value;
import org.springframework.data.annotation.Id;
import org.springframework.data.domain.Persistable;
import org.springframework.data.relational.core.mapping.Table;

@Value
@Table(name = "ITEMS")
class ItemEntity implements Persistable<String> {

  @Id String id;
  String name;
  String sku;

  @Override
  public boolean isNew() {
    return true; // TODO fixme
  }
}
