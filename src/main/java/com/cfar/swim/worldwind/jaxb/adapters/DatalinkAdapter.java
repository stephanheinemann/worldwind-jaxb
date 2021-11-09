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

import com.cfar.swim.worldwind.connections.DronekitDatalink;
import com.cfar.swim.worldwind.connections.SimulatedDatalink;
import com.cfar.swim.worldwind.jaxb.scenario.Datalink;

/**
 * Adapts a datalink.
 * 
 * @author Stephan Heinemann
 *
 */
public class DatalinkAdapter extends XmlAdapter<Datalink, com.cfar.swim.worldwind.connections.Datalink> {
	
	/**
	 * Unmarshals a datalink.
	 * 
	 * @param datalink the datalink to be unmarshalled
	 * 
	 * @return the unmarshalled datalink
	 * 
	 * @throws Exception if the datalink cannot be unmarshalled
	 * 
	 * @see XmlAdapter#unmarshal(Object)
	 */
	@Override
	public com.cfar.swim.worldwind.connections.Datalink unmarshal(Datalink datalink) throws Exception {
		com.cfar.swim.worldwind.connections.Datalink unmarshalledDatalink = null;
		
		if (null != datalink.getSimulated()) {
			unmarshalledDatalink = new SimulatedDatalinkAdapter().unmarshal(datalink.getSimulated());
		} else if (null != datalink.getDronekit()) {
			unmarshalledDatalink = new DronekitDatalinkAdapter().unmarshal(datalink.getDronekit());
		} else if (null != datalink.getMavlink()) {
			// TODO: support MavlinkDatalink
			throw new UnsupportedDataTypeException("unsupported datalink");
		} else {
			throw new UnsupportedDataTypeException("unsupported datalink");
		}
		
		unmarshalledDatalink.setDownlinkPeriod(new DurationAdapter().unmarshal(datalink.getDownlinkPeriod()));
		
		return unmarshalledDatalink;
	}
	
	/**
	 * Marshals a datalink.
	 * 
	 * @param datalink the datalink to be marshalled
	 * 
	 * @return the marshalled datalink
	 * 
	 * @throws Exception if the datalink cannot be marshalled
	 * 
	 * @see XmlAdapter#unmarshal(Object)
	 */
	@Override
	public Datalink marshal(com.cfar.swim.worldwind.connections.Datalink datalink) throws Exception {
		Datalink marshalledDatalink = new Datalink();
		
		marshalledDatalink.setDownlinkPeriod(new DurationAdapter().marshal(datalink.getDownlinkPeriod()));
		
		if (datalink instanceof SimulatedDatalink) {
			marshalledDatalink.setSimulated(new SimulatedDatalinkAdapter().marshal((SimulatedDatalink) datalink));
		} else if (datalink instanceof DronekitDatalink) {
			marshalledDatalink.setDronekit(new DronekitDatalinkAdapter().marshal((DronekitDatalink) datalink));
		} else {
			// TODO: support MavlinkDatalink
			throw new UnsupportedDataTypeException("unsupported datalink");
		}
		
		return marshalledDatalink;
	}
	
}
