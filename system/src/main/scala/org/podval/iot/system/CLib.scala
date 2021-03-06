/*
 * Copyright 2012 Podval Group.
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

package org.podval.iot.system

import com.sun.jna.{Native, Library, Pointer, NativeLong, Structure}


trait CLib extends Library {
  
  def ioctl(fd: Int, command: Int, data: Int): Int


  def ioctl(fd: Int, command: Int, data: Structure): Int


  def malloc(length: NativeLong): NativeLong


  def mmap(address: Pointer, length: NativeLong, prot: Int, flags: Int, fd: Int, offset: NativeLong): Pointer
  
  
  def munmap(address: Pointer, length: NativeLong): Int
}


object CLib {

  lazy val library: CLib = Native.loadLibrary("c", classOf[CLib]).asInstanceOf[CLib]
}
