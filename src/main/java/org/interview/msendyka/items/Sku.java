package org.interview.msendyka.items;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.Value;
import org.apache.logging.log4j.util.Strings;

@Value
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
class Sku {

  private static final String SKU_VALID_REGEX = "[A-Za-z\\d]{6,8}";
  private static final int V1_SKU_LENGTH = 6;
  private static final int V2_SKU_LENGTH = 8;
  String sku;
  String series;

  static Sku fromString(String sku) {
    validate(sku);
    if (sku.length() == V1_SKU_LENGTH) {
      String lot = getV1Series(sku);
      return new Sku(sku, lot);
    }
    if (sku.length() == V2_SKU_LENGTH) {
      String lot = getV2Series(sku);
      return new Sku(sku, lot);
    }
    throw new IllegalArgumentException();
  }

  private static String getV1Series(String sku) {
    return sku.substring(4, 5);
  }

  private static String getV2Series(String sku) {
    return sku.substring(6, 7);
  }

  static void validate(String sku) {
    if (Strings.isBlank(sku)) {
      throw new IllegalArgumentException();
    }
    if (!sku.matches(SKU_VALID_REGEX)) {
      throw new IllegalArgumentException();
    }
  }
}
