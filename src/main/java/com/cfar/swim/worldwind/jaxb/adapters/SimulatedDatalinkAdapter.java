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

import javax.xml.bind.annotation.adapters.XmlAdapter;

import com.cfar.swim.worldwind.jaxb.scenario.Errors;
import com.cfar.swim.worldwind.jaxb.scenario.SimulatedDatalink;

/**
 * Adapts a simulated datalink.
 * 
 * @author Stephan Heinemann
 *
 */
public class SimulatedDatalinkAdapter extends XmlAdapter<SimulatedDatalink, com.cfar.swim.worldwind.connections.SimulatedDatalink> {
	
	/**
	 * Unmarshals a simulated datalink.
	 * 
	 * @param datalink the simulated datalink to be unmarshalled
	 * 
	 * @return the unmarshalled simulated datalink
	 * 
	 * @throws Exception if the simulated datalink cannot be unmarshalled
	 * 
	 * @see XmlAdapter#unmarshal(Object)
	 */
	@Override
	public com.cfar.swim.worldwind.connections.SimulatedDatalink unmarshal(SimulatedDatalink datalink) throws Exception {
		com.cfar.swim.worldwind.connections.SimulatedDatalink unmarshalledDatalink =
				new com.cfar.swim.worldwind.connections.SimulatedDatalink();
		
		if (null != datalink.getErrorProbability()) {
			unmarshalledDatalink.setErrorProbablity(datalink.getErrorProbability());
		}
		if (null != datalink.getErrors()) {
			unmarshalledDatalink.setMaxTrackError(new TrackErrorAdapter().unmarshal(datalink.getErrors().getMaxTrackError()));
			// TODO: support track point errors
		}
		unmarshalledDatalink.setUplinkDelay(new DurationAdapter().unmarshal(datalink.getUplinkDelay()));
		
		return unmarshalledDatalink;
	}
	
	/**
	 * Marshals a simulated datalink.
	 * 
	 * @param datalink the simulated datalink to be marshalled
	 * 
	 * @return the marshalled simulated datalink
	 * 
	 * @throws Exception if the simulated datalink cannot be marshalled
	 * 
	 * @see XmlAdapter#unmarshal(Object)
	 */
	@Override
	public SimulatedDatalink marshal(com.cfar.swim.worldwind.connections.SimulatedDatalink datalink) throws Exception {
		SimulatedDatalink marshalledDatalink = new SimulatedDatalink();
		
		marshalledDatalink.setErrorProbability(datalink.getErrorProbablilty());
		Errors errors = new Errors();
		errors.setMaxTrackError(new TrackErrorAdapter().marshal(datalink.getMaxTrackError()));
		// TODO: support track point errors
		errors.setMaxTakeOffError(new TrackPointErrorAdapter().marshal(com.cfar.swim.worldwind.tracks.AircraftTrackPointError.ZERO));
		errors.setMaxLandingError(new TrackPointErrorAdapter().marshal(com.cfar.swim.worldwind.tracks.AircraftTrackPointError.ZERO));
		marshalledDatalink.setErrors(errors);
		marshalledDatalink.setUplinkDelay(new DurationAdapter().marshal(datalink.getUplinkDelay()));
		
		return marshalledDatalink;
	}
	
}
