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

import com.cfar.swim.worldwind.aircraft.A320;
import com.cfar.swim.worldwind.aircraft.Iris;
import com.cfar.swim.worldwind.jaxb.scenario.Aircraft;
import com.cfar.swim.worldwind.jaxb.scenario.AircraftType;
import com.cfar.swim.worldwind.jaxb.scenario.CombatIdentification;
import com.cfar.swim.worldwind.jaxb.scenario.ObstacleSphere;

/**
 * Adapts an aircraft.
 * 
 * @author Stephan Heinemann
 *
 */
public class AircraftAdapter extends XmlAdapter<Aircraft, com.cfar.swim.worldwind.aircraft.Aircraft> {
	
	/**
	 * Unmarshals an aircraft.
	 * 
	 * @param aircraft the aircraft to be unmarshalled
	 * 
	 * @return the unmarshalled aircraft
	 * 
	 * @throws Exception if the aircraft cannot be unmarshalled
	 * 
	 * @see XmlAdapter#unmarshal(Object)
	 */
	@Override
	public com.cfar.swim.worldwind.aircraft.Aircraft unmarshal(Aircraft aircraft) throws Exception {
		com.cfar.swim.worldwind.aircraft.Aircraft unmarshalledAircraft = null;
		
		switch (aircraft.getType()) {
		case A_320:
			unmarshalledAircraft = new A320(
					new PositionAdapter().unmarshal(aircraft.getCenter()),
					aircraft.getRadius(),
					com.cfar.swim.worldwind.aircraft.CombatIdentification.valueOf(aircraft.getCid().name()));
			break;
		case IRIS:
			unmarshalledAircraft = new Iris(
					new PositionAdapter().unmarshal(aircraft.getCenter()),
					aircraft.getRadius(),
					com.cfar.swim.worldwind.aircraft.CombatIdentification.valueOf(aircraft.getCid().name()));
			break;
		default:
			throw new UnsupportedDataTypeException("unsupported aircraft");
		}
		
		com.cfar.swim.worldwind.render.airspaces.ObstacleSphere obstacleSphere =
				new ObstacleSphereAdapter().unmarshal(aircraft);
		unmarshalledAircraft.setCostInterval(obstacleSphere.getCostInterval());
		unmarshalledAircraft.setDepiction(obstacleSphere.getDepiction());
		
		if (null != aircraft.getCapabilities()) {
			unmarshalledAircraft.setCapabililities(
					new CapabilitiesAdapter().unmarshal(aircraft.getCapabilities()));
		}
		
		return unmarshalledAircraft;
	}
	
	/**
	 * Marshals an aircraft.
	 * 
	 * @param aircraft the aircraft to be marshalled
	 * 
	 * @return the marshalled aircraft
	 * 
	 * @throws Exception if the aircraft cannot be marshalled
	 * 
	 * @see XmlAdapter#unmarshal(Object)
	 */
	@Override
	public Aircraft marshal(com.cfar.swim.worldwind.aircraft.Aircraft aircraft) throws Exception {
		Aircraft marshalledAircraft = new Aircraft();
		
		ObstacleSphere obstacleSphere = new ObstacleSphereAdapter().marshal(aircraft);
		marshalledAircraft.setCenter(obstacleSphere.getCenter());
		marshalledAircraft.setRadius(obstacleSphere.getRadius());
		marshalledAircraft.setCostInterval(obstacleSphere.getCostInterval());
		marshalledAircraft.setDepiction(obstacleSphere.getDepiction());
		
		if (aircraft instanceof A320) {
			marshalledAircraft.setType(AircraftType.A_320);
		} else if (aircraft instanceof Iris) {
			marshalledAircraft.setType(AircraftType.IRIS);
		} else {
			throw new UnsupportedDataTypeException("unsupported aircraft");
		}
		
		marshalledAircraft.setCid(CombatIdentification.fromValue(aircraft.getCombatIdentification().name()));
		marshalledAircraft.setCapabilities(
				new CapabilitiesAdapter().marshal(aircraft.getCapabilities()));
		
		return marshalledAircraft;
	}
	
}
