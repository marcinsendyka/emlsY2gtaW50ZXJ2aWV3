package org.interview.msendyka.items;

import lombok.Value;

@Value
class Item {
  ItemId id;
  ItemName name;
  Sku sku;
}
