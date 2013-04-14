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

package org.podval.iot.i2c


final class Address(val bus: Bus, val address: Int) {

  if (address < 0 || address > 0xff) throw new IllegalArgumentException("Invalid i2c address " + address)


  override def toString = "address " + address + " on " + bus


  def register(value: Int): Register = new Register(this, value)


  def writeQuick(data: Int) = bus.writeQuick(address, data)
  def readByte: Int = bus.readByte(address)
  def writeByte(data: Int) = bus.writeByte(address, data)
  def readByteData(command: Int): Int = bus.readByteData(address, command)
  def writeByteData(command: Int, data: Int) = bus.writeByteData(address, command, data)
  def readWordData(command: Int): Int = bus.readWordData(address, command)
  def writeWordData(command: Int, data: Int) = bus.writeWordData(address, command, data)
  def processCall(command: Int, data: Int): Int = bus.processCall(address, command, data)
  def readBlockData(command: Int): Seq[Byte] = bus.readBlockData(address, command)
  def writeBlockData(command: Int, data: Seq[Int]) = bus.writeBlockData(address, command, data)

  def readByteSimple: Byte = bus.readByteSimple(address)
  def writeByteSimple(data: Int) = bus.writeByteSimple(address, data)
  def readShort: Short = bus.readShort(address)
  def writeShort(data: Int) = bus.writeShort(address, data)
  def writeByteSimple(register: Int, data: Int) = bus.writeByteSimple(address, register, data)
  def writeShort(register: Int, data: Int) = bus.writeShort(address, register, data)
  def writeBytes(register: Int, data: Seq[Int]): Unit = bus.writeBytes(address, register, data)
  def writeBytes(data: Seq[Int]): Unit = bus.writeBytes(address, data)
  def readBytes(length: Int): Seq[Byte] = bus.readBytes(address, length)

  def setSlaveAddress: Unit = bus.setSlaveAddress(address)
}
