package org.interview.msendyka.items;

import lombok.Value;

@Value
class ItemName {
  String name;

  static ItemName fromString(String name) {
    return new ItemName(name);
  }

}
