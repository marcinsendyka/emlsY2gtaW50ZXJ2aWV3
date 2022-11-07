package org.interview.msendyka.items;

public class TestObjectFactory {

  public static final Sku SKU = Sku.fromString("123456");

  static Item item(Sku sku) {
    return new Item(new ItemId("id"), new ItemName("name"), sku);
  }
}
