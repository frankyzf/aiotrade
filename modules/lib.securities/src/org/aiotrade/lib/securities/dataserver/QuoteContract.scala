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
package org.aiotrade.lib.securities.dataserver;

import java.awt.Image
import java.util.Calendar
import org.aiotrade.lib.math.timeseries.Frequency

/**
 * most fields' default value should be OK.
 *
 * @author Caoyuan Deng
 */
object QuoteContract {
    val folderName :String = "QuoteServers"
}

class QuoteContract extends SecDataContract[QuoteServer] {
    import QuoteContract._

    serviceClassName = "org.aiotrade.platform.modules.dataserver.basic.YahooQuoteServer"
    active = true
    /** default freq */
    freq = Frequency.DAILY
    dateFormatString = "dd-MMM-yy"
    urlString = ""
    refreshable = false
    refreshInterval = 60 // seconds
    inputStream = None

    private val cal = Calendar.getInstance
    endDate = cal.getTime
    cal.set(1970, Calendar.JANUARY, 1)
    beginDate = cal.getTime

    def icon :Option[Image] =  {
        val server = if (isServiceInstanceCreated) createdServerInstance() else lookupServiceTemplate

        server match {
            case None => None
            case Some(x) => x.icon
        }
    }

    def supportedFreqs :Array[Frequency] = {
        val server = if (isServiceInstanceCreated) createdServerInstance() else lookupServiceTemplate

        server match {
            case None => Array()
            case Some(x) => x.supportedFreqs
        }
    }

    def isFreqSupported(freq:Frequency) :Boolean = {
        //        /** check if is my default freq, if true at least support default freq */
        //        if (freq.equals(getFreq())) {
        //            return true;
        //        }

        val server = if (isServiceInstanceCreated) createdServerInstance() else lookupServiceTemplate
        server match {
            case None => false
            case Some(x) => x.isFreqSupported(freq)
        }
    }

    override
    def displayName = "Quote Data Contract"

    /**
     * @param none args are needed.
     */
    override
    def createServiceInstance(args:Object*) :Option[QuoteServer] = {
        lookupServiceTemplate match {
            case None => None
            case Some(x) => x.createNewInstance.asInstanceOf[Option[QuoteServer]]
        }
    }

    def lookupServiceTemplate :Option[QuoteServer] =  {
        val servers = PersistenceManager.getDefault.lookupAllRegisteredServices(classOf[QuoteServer], folderName)
        servers.find{x => x.getClass.getName.equalsIgnoreCase(serviceClassName)}
    }

}