/*
 * Copyright 2012-2013 Podval Group.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.podval.iot.i2c.device

import org.podval.iot.i2c.core.Bus


/**
 * Adafruit's HT16K33-base LED backpack.
 */
class LedBackpack(bus: Bus, number: Int) {

  require(0 <= number && number <= 7, "Invalid LED backpack address: " + number)


  private[this] val address = bus.address(0x70 + number)


  // XXX remove "buffer"
  private[this] val buffer = new Array[Int](8)
//  private[this] val bytes = new Array[Byte](16)


  // Turn the oscillator on
  writeByte0(LedBackpack.SYSTEM_SETUP_REGISTER | 0x01)

  // Turn blink off
  setBlinkRate(LedBackpack.BlinkOff)

  // Set maximum brightness
  setBrightness(15)

  // Clear the screen
  clear
  update


  def clear {
    // XXX:   bytes.fill(0)
    for (i <- 0 until buffer.length) {
      buffer(i) = 0x00
    }
//    for (i <- 0 until bytes.length) {
//      bytes(i) = 0x00.toByte
//    }
  }


  def setChar(number: Int, value: Int) = buffer(number) = value


  def setBit(charNumber: Int, bit: Int, value: Boolean) =
    buffer(charNumber) = (buffer(charNumber) & ~bit) | (if (value) bit else 0x00)
//  def setBit(byteNumber: Int, bit: Int, value: Boolean) =
//    bytes(byteNumber) = ((bytes(byteNumber) & ~bit) | (if (value) bit else 0x00)).toByte


//  def setByte(number: Int, value: Byte) {
//    // XXX check range?
//    bytes(number*2+1) = value
//  }
//
//
//  def setWord(number: Int, value: Short) {
//    // XXX check range?
//    bytes(number*2  ) = ( value       & 0xff).toByte
//    bytes(number*2+1) = ((value >> 8) & 0xff).toByte
//  }


  def update {
    val bytes = new Array[Byte](buffer.length*2)
    for (i <- 0 until buffer.length) {
      bytes(i*2  ) = ( buffer(i)       & 0xff).toByte
      bytes(i*2+1) = ((buffer(i) >> 8) & 0xff).toByte
    }

    address.writeBlockDataI2c(0, bytes)
  }


  def setBrightness(value: Int) {
    require(0 <= value && value <= 15, "Brightness must be between 0 and 15, not " + value)
    writeByte0(LedBackpack.DIMMING_REGISTER | value)
  }


  def setBlinkRate(value: LedBackpack.BlinkRate) =
    writeByte0(LedBackpack.DISPLAY_SETUP_REGISTER | 0x01 | (value.code << 1))


  private[this] def writeByte0(command: Int) = address.writeByteData(command.toByte, 0x00)
}


object LedBackpack {
  private val SYSTEM_SETUP_REGISTER : Int = 0x20
  private val DIMMING_REGISTER      : Int = 0xe0
  private val DISPLAY_SETUP_REGISTER: Int = 0x80

  sealed class BlinkRate(val code: Byte)
  case object BlinkOff    extends BlinkRate(0x00)
  case object Blink1Hz    extends BlinkRate(0x01)
  case object Blink2Hz    extends BlinkRate(0x02)
  case object BlinkHalfHz extends BlinkRate(0x03)
}
