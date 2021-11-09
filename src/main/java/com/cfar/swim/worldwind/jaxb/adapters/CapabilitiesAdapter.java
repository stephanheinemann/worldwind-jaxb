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

import com.cfar.swim.worldwind.jaxb.scenario.Capabilities;

import gov.nasa.worldwind.geom.Angle;

/**
 * Adapts capabilities.
 * 
 * @author Stephan Heinemann
 *
 */
public class CapabilitiesAdapter extends XmlAdapter<Capabilities, com.cfar.swim.worldwind.aircraft.Capabilities> {
	
	/**
	 * Unmarshals capabilities.
	 * 
	 * @param capabilities the capabilities to be unmarshalled
	 * 
	 * @return the unmarshalled capabilities
	 * 
	 * @throws Exception if the capabilities cannot be unmarshalled
	 * 
	 * @see XmlAdapter#unmarshal(Object)
	 */
	@Override
	public com.cfar.swim.worldwind.aircraft.Capabilities unmarshal(Capabilities capabilities) throws Exception {
		com.cfar.swim.worldwind.aircraft.Capabilities unmarshalledCapabilities =
				new com.cfar.swim.worldwind.aircraft.Capabilities();
		
		unmarshalledCapabilities.setApproachRateOfDescent(capabilities.getApproachRateOfDescent());
		unmarshalledCapabilities.setApproachSpeed(capabilities.getApproachSpeed());
		unmarshalledCapabilities.setCruiseClimbSpeed(capabilities.getCruiseClimbSpeed());
		unmarshalledCapabilities.setCruiseDescentSpeed(capabilities.getCruiseDescentSpeed());
		unmarshalledCapabilities.setCruiseRateOfClimb(capabilities.getCruiseRateOfClimb());
		unmarshalledCapabilities.setCruiseRateOfDescent(capabilities.getCruiseRateOfDescent());
		unmarshalledCapabilities.setCruiseSpeed(capabilities.getCruiseSpeed());
		unmarshalledCapabilities.setMaximumAngleOfClimb(Angle.fromDegrees(capabilities.getMaximumAngleOfClimb()));
		unmarshalledCapabilities.setMaximumAngleOfClimbSpeed(capabilities.getMaximumAngleOfClimbSpeed());
		unmarshalledCapabilities.setMaximumGlideSpeed(capabilities.getMaximumGlideSpeed());
		unmarshalledCapabilities.setMaximumRateOfClimb(capabilities.getMaximumRateOfClimb());
		unmarshalledCapabilities.setMaximumRateOfClimbSpeed(capabilities.getMaximumRateOfClimbSpeed());
		unmarshalledCapabilities.setMaximumRateOfDescent(capabilities.getMaximumRateOfDescent());
		unmarshalledCapabilities.setMaximumRateOfDescentSpeed(capabilities.getMaximumRateOfDescentSpeed());
		unmarshalledCapabilities.setMaximumSpeed(capabilities.getMaximumSpeed());
		
		return unmarshalledCapabilities;
	}
	
	/**
	 * Marshals capabilities.
	 * 
	 * @param capabilities the capabilities to be marshalled
	 * 
	 * @return the marshalled capabilities
	 * 
	 * @throws Exception if the capabilities cannot be marshalled
	 * 
	 * @see XmlAdapter#unmarshal(Object)
	 */
	@Override
	public Capabilities marshal(com.cfar.swim.worldwind.aircraft.Capabilities capabilities) throws Exception {
		Capabilities marshalledCapabilities = new Capabilities();
		
		marshalledCapabilities.setApproachRateOfDescent(capabilities.getApproachRateOfDescent());
		marshalledCapabilities.setApproachSpeed(capabilities.getApproachSpeed());
		marshalledCapabilities.setCruiseClimbSpeed(capabilities.getCruiseClimbSpeed());
		marshalledCapabilities.setCruiseDescentSpeed(capabilities.getCruiseDescentSpeed());
		marshalledCapabilities.setCruiseRateOfClimb(capabilities.getCruiseRateOfClimb());
		marshalledCapabilities.setCruiseRateOfDescent(capabilities.getCruiseRateOfDescent());
		marshalledCapabilities.setCruiseSpeed(capabilities.getCruiseSpeed());
		marshalledCapabilities.setMaximumAngleOfClimb(capabilities.getMaximumAngleOfClimb().getDegrees());
		marshalledCapabilities.setMaximumAngleOfClimbSpeed(capabilities.getMaximumAngleOfClimbSpeed());
		marshalledCapabilities.setMaximumGlideSpeed(capabilities.getMaximumGlideSpeed());
		marshalledCapabilities.setMaximumRateOfClimb(capabilities.getMaximumRateOfClimb());
		marshalledCapabilities.setMaximumRateOfClimbSpeed(capabilities.getMaximumRateOfClimbSpeed());
		marshalledCapabilities.setMaximumRateOfDescent(capabilities.getMaximumRateOfDescent());
		marshalledCapabilities.setMaximumRateOfDescentSpeed(capabilities.getMaximumRateOfDescentSpeed());
		marshalledCapabilities.setMaximumSpeed(capabilities.getMaximumSpeed());
		
		return marshalledCapabilities;
	}
	
}
