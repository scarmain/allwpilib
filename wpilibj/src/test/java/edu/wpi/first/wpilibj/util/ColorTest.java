// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package edu.wpi.first.wpilibj.util;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

class ColorTest {
  // 12-bit accuracy for calculations, taking off 2 bit for rounding + ceil call
  static final double DELTA = 1.0 / (1 << 10);

  @Test
  void testConstructDefault() {
    var color = new Color();

    assertEquals(0.0, color.red);
    assertEquals(0.0, color.green);
    assertEquals(0.0, color.blue);
  }

  @Test
  void testConstructFromDoubles() {
    {
      var color = new Color(1.0, 0.5, 0.25);

      assertEquals(1.0, color.red, DELTA);
      assertEquals(0.5, color.green, DELTA);
      assertEquals(0.25, color.blue, DELTA);
    }

    {
      var color = new Color(1.0, 0.0, 0.0);

      // Check for exact match to ensure round-and-clamp is correct
      assertEquals(1.0, color.red);
      assertEquals(0.0, color.green);
      assertEquals(0.0, color.blue);
    }
  }

  @Test
  void testConstructFromInts() {
    var color = new Color(255, 128, 64);

    assertEquals(1.0, color.red, DELTA);
    assertEquals(128.0 / 255, color.green, DELTA);
    assertEquals(64.0 / 255, color.blue, DELTA);
  }

  @Test
  void testConstructFromHexString() {
    var color = new Color("#FF8040");

    assertEquals(1.0, color.red, DELTA);
    assertEquals(128.0 / 255, color.green, DELTA);
    assertEquals(64.0 / 255, color.blue, DELTA);

    // No leading #
    assertThrows(IllegalArgumentException.class, () -> new Color("112233"));

    // Too long
    assertThrows(IllegalArgumentException.class, () -> new Color("#11223344"));

    // Invalid hex characters
    assertThrows(IllegalArgumentException.class, () -> new Color("#$$$$$$"));
  }

  @Test
  void testFromHSV() {
    // should be RGB(32,64,64), #204040
    var color = Color.fromHSV(90, 128, 64);

    assertEquals(32.0 / 255, color.red, DELTA);
    assertEquals(64.0 / 255, color.green, DELTA);
    assertEquals(64.0 / 255, color.blue, DELTA);
  }

  @Test
  void testToHexString() {
    var color = new Color(255, 128, 64);

    assertEquals("#FF8040", color.toHexString());
    assertEquals("#FF8040", color.toString());
  }

  @Test
  void testWpiColor() {
    // base colors
    testWpiColorHelper(0, 255, 255); // red
    testWpiColorHelper(60, 255, 255); // lime
    testWpiColorHelper(120, 255, 255); // blue

    // half colors
    testWpiColorHelper(0, 255, 128); // maroon
    testWpiColorHelper(60, 255, 128); // green
    testWpiColorHelper(120, 255, 128); // navy

    // test random colors, all hues are within each 15* boundary
    testWpiColorHelper(12, 78, 128);
    testWpiColorHelper(17, 17, 17);
    testWpiColorHelper(30, 76, 23);
    testWpiColorHelper(42, 42, 42);
    testWpiColorHelper(57, 57, 57);
    testWpiColorHelper(66, 66, 66);
    testWpiColorHelper(82, 83, 84);
    testWpiColorHelper(95, 95, 95);
    testWpiColorHelper(111, 111, 111);
    testWpiColorHelper(115, 23, 15);
    testWpiColorHelper(121, 121, 121);
    testWpiColorHelper(138, 138, 138);
    testWpiColorHelper(164, 23, 240);
    testWpiColorHelper(171, 171, 171);
  }

  private void testWpiColorHelper(int hue, int sat, int value) {
    Color color = Color.fromHSV(hue, sat, value);
    assertEquals(hue, color.hue);
    assertEquals(sat, color.sat);
    assertEquals(value, color.value);
  }
}
