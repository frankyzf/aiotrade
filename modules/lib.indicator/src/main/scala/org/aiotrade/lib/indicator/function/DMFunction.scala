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
package org.aiotrade.lib.indicator.function

import org.aiotrade.lib.math.timeseries.Null
import org.aiotrade.lib.math.timeseries.TSer

/**
 *
 * @author Caoyuan Deng
 */
class DMFunction extends AbstractFunction {
    
  val _dmPlus  = TVar[Float]()
  val _dmMinus = TVar[Float]()
    
  override def set(baseSer: TSer, args: Any*): Unit = {
    super.set(baseSer)
  }
    
  protected def computeSpot(i: Int): Unit = {
    if (i == 0) {
            
      _dmPlus (i) = Null.Float
      _dmMinus(i) = Null.Float
            
    } else {
            
      if (H(i) > H(i - 1) && L(i) > L(i - 1)) {
        _dmPlus (i) = H(i) - H(i - 1)
        _dmMinus(i) = 0f
      } else if (H(i) < H(i - 1) && L(i) < L(i - 1)) {
        _dmPlus (i) = 0f
        _dmMinus(i) = L(i - 1) - L(i)
      } else if (H(i) > H(i - 1) && L(i) < L(i - 1)) {
        if (H(i) - H(i - 1) > L(i - 1) - L(i)) {
          _dmPlus (i) = H(i) - H(i - 1)
          _dmMinus(i) = 0f
        } else {
          _dmPlus (i) = 0f
          _dmMinus(i) = L(i - 1) - L(i)
        }
      } else if (H(i) < H(i - 1) && L(i) > L(i - 1)) {
        _dmPlus (i) = 0f
        _dmMinus(i) = 0f
      } else if (H(i) == H(i - 1) && L(i) == L(i - 1)) {
        _dmPlus (i) = 0f
        _dmMinus(i) = 0f
      } else if (L(i) > H(i - 1)) {
        _dmPlus (i) = H(i) - H(i)
        _dmMinus(i) = 0f
      } else if (H(i) < L(i - 1)) {
        _dmPlus (i) = 0f
        _dmMinus(i) = L(i - 1) - L(i)
      } else {
        _dmPlus (i) = 0f
        _dmMinus(i) = 0f
      }
            
    }
  }
    
  def dmPlus(sessionId: Long, idx: Int): Float = {
    computeTo(sessionId, idx)
        
    _dmPlus(idx)
  }
    
  def dmMinus(sessionId: Long, idx: Int): Float = {
    computeTo(sessionId, idx)
        
    _dmMinus(idx)
  }
}
