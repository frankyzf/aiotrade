/*
 * Copyright (c) 2006-2007, AIOTrade Computing Co. and Contributors
 * All rights reserved.
 * 
 * Redistribution and use in source and binary forms, with or without 
 * modification, are permitted provided that the following conditions are met:
 * 
 *  o Redistributions of source code must retain the above copyright notice, 
 *    this list of conditions and the following disclaimer. 
 *    
 *  o Redistributions in binary form must reproduce the above copyright notice, 
 *    this list of conditions and the following disclaimer in the documentation 
 *    and/or other materials provided with the distribution. 
 *    
 *  o Neither the name of AIOTrade Computing Co. nor the names of 
 *    its contributors may be used to endorse or promote products derived 
 *    from this software without specific prior written permission. 
 *    
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" 
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, 
 * THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR 
 * PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR 
 * CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, 
 * EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, 
 * PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; 
 * OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, 
 * WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR 
 * OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, 
 * EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */
package org.aiotrade.lib.charting.widget



/**
 *
 * @author  Caoyuan Deng
 * @version 1.0, November 29, 2006, 5:27 PM
 * @since   1.0.4
 */
class Arrow extends PathWidget {
  final class Model extends WidgetModel {
    var x: Double = _
    var y: Double = _
    var up: Boolean = _
    var filled: Boolean = _
        
    def set(x: Double, y: Double, up: Boolean, filled: Boolean) {
      this.x = x
      this.y = y
      this.up = up
      this.filled = filled
    }
  }

  type M = Model
    
  override protected def createModel = new Model

  private val wTrunk = 3 // half width
  private val hTop = 3 //
  private val hTrunk = 14 - hTop

  override protected def plotWidget {
    val m = model
    val path = getPath
    path.reset
        
    if (m.up) {
      path.moveTo(m.x, m.y)
      path.lineTo(m.x - wTrunk - 1, m.y + hTop)
      path.lineTo(m.x - wTrunk, m.y + hTop)
      path.lineTo(m.x - wTrunk, m.y + hTrunk)
      path.lineTo(m.x + wTrunk, m.y + hTrunk)
      path.lineTo(m.x + wTrunk, m.y + hTop)
      path.lineTo(m.x + wTrunk + 1, m.y + hTop)
      path.closePath
    } else {
      path.moveTo(m.x, m.y)
      path.lineTo(m.x - wTrunk - 1, m.y - hTop)
      path.lineTo(m.x - wTrunk, m.y - hTop)
      path.lineTo(m.x - wTrunk, m.y - hTrunk)
      path.lineTo(m.x + wTrunk, m.y - hTrunk)
      path.lineTo(m.x + wTrunk, m.y - hTop)
      path.lineTo(m.x + wTrunk + 1, m.y - hTop)
      path.closePath
    }
        
  }
    
}

