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

package org.podval.iot.app

import org.podval.iot.gpio.Output
import org.podval.iot.platform.raspberrypi.RaspberryPi


object Led {

  def main(args: Array[String]) {
    val pi = new RaspberryPi
    for (i <- 0 to 52) {
      println("pin " + i + " is " + pi.gpio.pin(i).level)
    }
//    val pin = pi.gpio.pin(7)
//    pin.direction = Output
//
//    while (true) {
//      pin.level = !pin.level
//      Thread.sleep(1000)
//    }
  }
}
