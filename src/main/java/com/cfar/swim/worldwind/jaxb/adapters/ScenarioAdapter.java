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

import com.cfar.swim.worldwind.jaxb.scenario.Scenario;

/**
 * Adapts a scenario.
 * 
 * @author Stephan Heinemann
 *
 */
public class ScenarioAdapter extends XmlAdapter<Scenario, com.cfar.swim.worldwind.session.Scenario> {
	
	/**
	 * Unmarshals a scenario.
	 * 
	 * @param scenario the scenario to be unmarshalled
	 * 
	 * @return the unmarshalled scenario
	 * 
	 * @throws Exception if the scenario cannot be unmarshalled
	 * 
	 * @see XmlAdapter#unmarshal(Object)
	 */
	@Override
	public com.cfar.swim.worldwind.session.Scenario unmarshal(Scenario scenario) throws Exception {
		com.cfar.swim.worldwind.session.Scenario unmarshalledScenario =
				new com.cfar.swim.worldwind.session.Scenario(scenario.getId());
		
		if (null != scenario.getTime()) {
			unmarshalledScenario.setTime(new TimeAdapter().unmarshal(scenario.getTime()));
		}
		unmarshalledScenario.setThreshold(scenario.getThreshold());
		unmarshalledScenario.setGlobe(new GlobeAdapter().unmarshal(scenario.getGlobe()));
		if (null != scenario.getEnvironment().getGrid()) {
			unmarshalledScenario.setSector(new SectorAdapter().unmarshal(scenario.getEnvironment().getGrid()));
		} else if (null != scenario.getEnvironment().getContinuum()) {
			unmarshalledScenario.setSector(new SectorAdapter().unmarshal(scenario.getEnvironment().getContinuum()));
		}
		unmarshalledScenario.setEnvironment(new EnvironmentAdapter(
				unmarshalledScenario.getGlobe()).unmarshal(scenario.getEnvironment()));
		if (null != scenario.getObstacles()) {
			unmarshalledScenario.submitAddObstacles(new ObstaclesAdapter().unmarshal(scenario.getObstacles()));
		}
		if (null != scenario.getAircraft()) {
			unmarshalledScenario.setAircraft(new AircraftAdapter().unmarshal(scenario.getAircraft()));
		}
		if (null != scenario.getWaypoints()) {
			unmarshalledScenario.addWaypoints(new WaypointsAdapter().unmarshal(scenario.getWaypoints()));
		}
		if (null != scenario.getTrajectory()) {
			unmarshalledScenario.setTrajectory(new TrajectoryAdapter().unmarshal(scenario.getTrajectory()));
		}
		
		unmarshalledScenario.setPlanner(new PlannerAdapter(
				unmarshalledScenario.getAircraft(),
				unmarshalledScenario.getEnvironment()).unmarshal(scenario.getPlanner()));
		unmarshalledScenario.setDatalink(new DatalinkAdapter().unmarshal(scenario.getDatalink()));
		unmarshalledScenario.setSwimConnection(new SwimAdapter().unmarshal(scenario.getSwim()));
		
		return unmarshalledScenario;
	}
	
	/**
	 * Marshals a scenario.
	 * 
	 * @param scenario the scenario to be marshalled
	 * 
	 * @return the marshalled scenario
	 * 
	 * @throws Exception if the scenario cannot be marshalled
	 * 
	 * @see XmlAdapter#unmarshal(Object)
	 */
	@Override
	public Scenario marshal(com.cfar.swim.worldwind.session.Scenario scenario) throws Exception {
		Scenario marshalledScenario = new Scenario();
		
		marshalledScenario.setId(scenario.getId());
		marshalledScenario.setTime(new TimeAdapter().marshal(scenario.getTime()));
		marshalledScenario.setThreshold(scenario.getThreshold());
		marshalledScenario.setGlobe(new GlobeAdapter().marshal(scenario.getGlobe()));
		marshalledScenario.setEnvironment(new EnvironmentAdapter(scenario.getGlobe()).marshal(scenario.getEnvironment()));
		
		if (scenario.hasObstacles()) {
			marshalledScenario.setObstacles(new ObstaclesAdapter().marshal(scenario.getObstacles()));
		}
		if (scenario.hasAircraft()) {
			marshalledScenario.setAircraft(new AircraftAdapter().marshal(scenario.getAircraft()));
		}
		if (scenario.hasWaypoints()) {
			marshalledScenario.setWaypoints(new WaypointsAdapter().marshal(scenario.getWaypoints()));
		}
		if (scenario.hasTrajectory()) {
			marshalledScenario.setTrajectory(new TrajectoryAdapter().marshal(scenario.getTrajectory()));
		}
		marshalledScenario.setPlanner(new PlannerAdapter(
				scenario.getAircraft(), scenario.getEnvironment()).marshal(scenario.getPlanner()));
		marshalledScenario.setDatalink(new DatalinkAdapter().marshal(scenario.getDatalink()));
		marshalledScenario.setSwim(new SwimAdapter().marshal(scenario.getSwimConnection()));
		
		return marshalledScenario;
	}
	
}
