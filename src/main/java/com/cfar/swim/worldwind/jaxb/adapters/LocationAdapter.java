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

import com.cfar.swim.worldwind.jaxb.scenario.Location;

import gov.nasa.worldwind.geom.Angle;
import gov.nasa.worldwind.geom.LatLon;

/**
 * Adapts a location.
 * 
 * @author Stephan Heinemann
 *
 */
public class LocationAdapter extends XmlAdapter<Location, LatLon> {
	
	/**
	 * Unmarshals a location.
	 * 
	 * @param location the location to be unmarshalled
	 * 
	 * @return the unmarshalled location
	 * 
	 * @throws Exception if the location cannot be unmarshalled
	 * 
	 * @see XmlAdapter#unmarshal(Object)
	 */
	@Override
	public LatLon unmarshal(Location location) throws Exception {
		return new LatLon(
				Angle.fromDegrees(location.getLatitude()),
				Angle.fromDegrees(location.getLongitude())); 
	}
	
	/**
	 * Marshals a location.
	 * 
	 * @param location the location to be marshalled
	 * 
	 * @return the marshalled location
	 * 
	 * @throws Exception if the location cannot be marshalled
	 * 
	 * @see XmlAdapter#unmarshal(Object)
	 */
	@Override
	public Location marshal(LatLon location) throws Exception {
		Location marshalledLocation = new Location();
		
		marshalledLocation.setLatitude(location.getLatitude().getDegrees());
		marshalledLocation.setLongitude(location.getLongitude().getDegrees());
		
		return marshalledLocation;
	}
	
}
