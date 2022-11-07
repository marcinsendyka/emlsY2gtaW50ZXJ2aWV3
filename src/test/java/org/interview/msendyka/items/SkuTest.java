package org.interview.msendyka.items;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

class SkuTest {

  @ParameterizedTest
  @CsvSource(
      delimiter = '|',
      useHeadersInDisplayName = true,
      textBlock =
          """
              sku       | expectedSeries
              123456    | 5
              abcdef    | e
              123def    | e
              12345678  | 7
              abcdefgh  | g
              """)
  void shouldCreateValidSku(String sku, String expectedSeries) {
    // when
    Sku result = Sku.fromString(sku);

    // then
    assertThat(result.getSeries()).isEqualTo(expectedSeries);
    assertThat(result.getSku()).isEqualTo(sku);
  }

  @ParameterizedTest
  @CsvSource(
      delimiter = '|',
      useHeadersInDisplayName = true,
      nullValues = {"null"},
      textBlock =
          """
              sku
              null
              way-too-long
              small
              21312213132
              """)
  void shouldFailWithIllegalArgumentException(String sku) {
    // when & then
    assertThatThrownBy(() -> Sku.fromString(sku)).isInstanceOf(IllegalArgumentException.class);
  }
}
