/*
 * Copyright (c) 2021, Stephan Heinemann (UVic Center for Aerospace Research)
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without modification,
 * are permitted provided that the following conditions are met:
 *
 * 1. Redistributions of source code must retain the above copyright notice, this
 * list of conditions and the following disclaimer.
 *
 * 2. Redistributions in binary form must reproduce the above copyright notice,
 * this list of conditions and the following disclaimer in the documentation
 * and/or other materials provided with the distribution.
 *
 * 3. Neither the name of the copyright holder nor the names of its contributors
 * may be used to endorse or promote products derived from this software without
 * specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE LIABLE
 * FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL
 * DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 * SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER
 * CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY,
 * OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE
 * OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */
package com.cfar.swim.worldwind.jaxb.adapters;

import javax.activation.UnsupportedDataTypeException;
import javax.xml.bind.annotation.adapters.XmlAdapter;

import com.cfar.swim.worldwind.connections.LiveSwimConnection;
import com.cfar.swim.worldwind.connections.SimulatedSwimConnection;
import com.cfar.swim.worldwind.connections.SwimConnection;
import com.cfar.swim.worldwind.jaxb.scenario.Subscriptions;
import com.cfar.swim.worldwind.jaxb.scenario.Swim;
import com.cfar.swim.worldwind.jaxb.scenario.SwimProtocol;

/**
 * Adapts a SWIM connection.
 * 
 * @author Stephan Heinemann
 *
 */
public class SwimAdapter extends XmlAdapter<Swim, SwimConnection> {
	
	/**
	 * Unmarshals a SWIM connection.
	 * 
	 * @param swim the SWIM connection to be unmarshalled
	 * 
	 * @return the unmarshalled SWIM connection
	 * 
	 * @throws Exception if the SWIM connection cannot be unmarshalled
	 * 
	 * @see XmlAdapter#unmarshal(Object)
	 */
	@Override
	public SwimConnection unmarshal(Swim swim) throws Exception {
		SwimConnection unmarshalledSwim = null;
		
		if (null != swim.getSimulated()) {
			unmarshalledSwim = new SimulatedSwimAdapter().unmarshal(swim.getSimulated());
		} else if (null != swim.getLive()) {
			unmarshalledSwim = new LiveSwimAdapter().unmarshal(swim.getLive());
		} else {
			throw new UnsupportedDataTypeException("unsupported SWIM connection");
		}
		
		if (null != swim.getSubscriptions()) {
			for (SwimProtocol protocol :swim.getSubscriptions().getProtocol()) {
				unmarshalledSwim.subscribe(com.cfar.swim.worldwind.data.SwimProtocol.valueOf(protocol.name()));
			}
		}
		
		return unmarshalledSwim;
	}
	
	/**
	 * Marshals a SWIM connection.
	 * 
	 * @param swim the SWIM connection to be marshalled
	 * 
	 * @return the marshalled SWIM connection
	 * 
	 * @throws Exception if the SWIM connection cannot be marshalled
	 * 
	 * @see XmlAdapter#unmarshal(Object)
	 */
	@Override
	public Swim marshal(SwimConnection swim) throws Exception {
		Swim marshalledSwim = new Swim();
		
		if (swim.hasSubscriptions()) {
			marshalledSwim.setSubscriptions(new Subscriptions());
			
			if (swim.hasSubscribed(com.cfar.swim.worldwind.data.SwimProtocol.AIXM)) {
				marshalledSwim.getSubscriptions().getProtocol().add(SwimProtocol.AIXM);
			}
			if (swim.hasSubscribed(com.cfar.swim.worldwind.data.SwimProtocol.AMXM)) {
				marshalledSwim.getSubscriptions().getProtocol().add(SwimProtocol.AMXM);
			}
			if (swim.hasSubscribed(com.cfar.swim.worldwind.data.SwimProtocol.FIXM)) {
				marshalledSwim.getSubscriptions().getProtocol().add(SwimProtocol.FIXM);
			}
			if (swim.hasSubscribed(com.cfar.swim.worldwind.data.SwimProtocol.IWXXM)) {
				marshalledSwim.getSubscriptions().getProtocol().add(SwimProtocol.IWXXM);
			}
			if (swim.hasSubscribed(com.cfar.swim.worldwind.data.SwimProtocol.WXXM)) {
				marshalledSwim.getSubscriptions().getProtocol().add(SwimProtocol.WXXM);
			}
		}
		
		if (swim instanceof SimulatedSwimConnection) {
			marshalledSwim.setSimulated(new SimulatedSwimAdapter().marshal((SimulatedSwimConnection) swim));
		} else if (swim instanceof LiveSwimConnection) {
			marshalledSwim.setLive(new LiveSwimAdapter().marshal((LiveSwimConnection) swim));
		} else {
			throw new UnsupportedDataTypeException("unsupported SWIM connection");
		}
		
		return marshalledSwim;
	}
	
}
