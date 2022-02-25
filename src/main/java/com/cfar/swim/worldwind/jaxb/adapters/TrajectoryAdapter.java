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

import java.util.ArrayList;

import javax.xml.bind.annotation.adapters.XmlAdapter;

import com.cfar.swim.worldwind.javafx.TrajectoryStylist;
import com.cfar.swim.worldwind.jaxb.scenario.Trajectory;
import com.cfar.swim.worldwind.jaxb.scenario.Waypoint;

/**
 * Adapts a trajectory.
 * 
 * @author Stephan Heinemann
 *
 */
public class TrajectoryAdapter extends XmlAdapter<Trajectory, com.cfar.swim.worldwind.planning.Trajectory> {
	
	/**
	 * Unmarshals a trajectory.
	 * 
	 * @param trajectory the trajectory to be unmarshalled
	 * 
	 * @return the unmarshalled trajectory
	 * 
	 * @throws Exception if the trajectory cannot be unmarshalled
	 * 
	 * @see XmlAdapter#unmarshal(Object)
	 */
	@Override
	public com.cfar.swim.worldwind.planning.Trajectory unmarshal(Trajectory trajectory) throws Exception {
		ArrayList<com.cfar.swim.worldwind.planning.Waypoint> waypoints = new ArrayList<>();
		
		for (Waypoint waypoint : trajectory.getWaypoint()) {
			waypoints.add(new WaypointAdapter().unmarshal(waypoint));
		}
		com.cfar.swim.worldwind.planning.Trajectory unmarshalledTrajectory =
				new com.cfar.swim.worldwind.planning.Trajectory(waypoints);
		if (null != trajectory.getDepiction()) {
			unmarshalledTrajectory.setDepiction(new DepictionAdapter(
					unmarshalledTrajectory.getReferencePosition()).unmarshal(trajectory.getDepiction()));
		}
		TrajectoryStylist.styleTrajectory(unmarshalledTrajectory);
		
		return unmarshalledTrajectory;
	}
	
	/**
	 * Marshals a trajectory.
	 * 
	 * @param trajectory the trajectory to be marshalled
	 * 
	 * @return the marshalled trajectory
	 * 
	 * @throws Exception if the trajectory cannot be marshalled
	 * 
	 * @see XmlAdapter#unmarshal(Object)
	 */
	@Override
	public Trajectory marshal(com.cfar.swim.worldwind.planning.Trajectory trajectory) throws Exception {
		Trajectory marshalledTrajectory = new Trajectory();
		
		for (com.cfar.swim.worldwind.planning.Waypoint waypoint : trajectory.getWaypoints()) {
			marshalledTrajectory.getWaypoint().add(new WaypointAdapter().marshal(waypoint));
		}
		if (trajectory.hasDepiction()) {
			marshalledTrajectory.setDepiction(new DepictionAdapter(
					trajectory.getReferencePosition()).marshal(trajectory.getDepiction()));
		}
		
		return marshalledTrajectory;
	}
	
}
