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

import com.cfar.swim.worldwind.jaxb.scenario.Position;
import com.cfar.swim.worldwind.jaxb.scenario.Waypoint;

/**
 * Adapts a waypoint.
 * 
 * @author Stephan Heinemann
 *
 */
public class WaypointAdapter extends XmlAdapter<Waypoint, com.cfar.swim.worldwind.planning.Waypoint> {
	
	/**
	 * Unmarshals a waypoint.
	 * 
	 * @param waypoint the waypoint to be unmarshalled
	 * 
	 * @return the unmarshalled waypoint
	 * 
	 * @throws Exception if the waypoint cannot be unmarshalled
	 * 
	 * @see XmlAdapter#unmarshal(Object)
	 */
	@Override
	public com.cfar.swim.worldwind.planning.Waypoint unmarshal(Waypoint waypoint) throws Exception {
		gov.nasa.worldwind.geom.Position position = new PositionAdapter().unmarshal(waypoint);
		com.cfar.swim.worldwind.planning.Waypoint unmarshalledWaypoint =
				new com.cfar.swim.worldwind.planning.Waypoint(position);
		unmarshalledWaypoint.setDesignator(waypoint.getDesignator());
		if (null != waypoint.getCost()) {
			unmarshalledWaypoint.setCost(waypoint.getCost());
		}
		if (null != waypoint.getDtg()) {
			unmarshalledWaypoint.setDtg(waypoint.getDtg());
		}
		if (null != waypoint.getTtg()) {
			unmarshalledWaypoint.setTtg(new DurationAdapter().unmarshal(waypoint.getTtg()));
		}
		if (null != waypoint.getEto()) {
			unmarshalledWaypoint.setEto(new TimeAdapter().unmarshal(waypoint.getEto()));
		}
		if (null != waypoint.getAto()) {
			unmarshalledWaypoint.setAto(new TimeAdapter().unmarshal(waypoint.getAto()));
		}
		
		if (null != waypoint.getDepiction()) {
			unmarshalledWaypoint.setDepiction(
					new DepictionAdapter(position).unmarshal(waypoint.getDepiction()));
		}
		
		return unmarshalledWaypoint;
	}
	
	/**
	 * Marshals a waypoint.
	 * 
	 * @param waypoint the waypoint to be marshalled
	 * 
	 * @return the marshalled waypoint
	 * 
	 * @throws Exception if the waypoint cannot be marshalled
	 * 
	 * @see XmlAdapter#unmarshal(Object)
	 */
	@Override
	public Waypoint marshal(com.cfar.swim.worldwind.planning.Waypoint waypoint) throws Exception {
		Waypoint marshalledWaypoint = new Waypoint();
		
		Position position = new PositionAdapter().marshal(waypoint);
		marshalledWaypoint.setLatitude(position.getLatitude());
		marshalledWaypoint.setLongitude(position.getLongitude());
		marshalledWaypoint.setAltitude(position.getAltitude());
		marshalledWaypoint.setDesignator(waypoint.getDesignator());
		marshalledWaypoint.setCost(waypoint.getCost());
		marshalledWaypoint.setDtg(waypoint.getDtg());
		
		if (waypoint.hasTtg()) {
			marshalledWaypoint.setTtg(new DurationAdapter().marshal(waypoint.getTtg()));
		}
		if (waypoint.hasEto()) {
			marshalledWaypoint.setEto(new TimeAdapter().marshal(waypoint.getEto()));
		}
		if (waypoint.hasAto()) {
			marshalledWaypoint.setAto(new TimeAdapter().marshal(waypoint.getAto()));
		}
		if (waypoint.hasDepiction()) {
			marshalledWaypoint.setDepiction(
					new DepictionAdapter(waypoint).marshal(waypoint.getDepiction()));
		}
		
		return marshalledWaypoint;
	}
	
}
