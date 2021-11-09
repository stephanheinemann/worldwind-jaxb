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

import java.util.HashSet;
import java.util.Set;

import javax.xml.bind.annotation.adapters.XmlAdapter;

import com.cfar.swim.worldwind.jaxb.scenario.ObstacleBox;
import com.cfar.swim.worldwind.jaxb.scenario.ObstacleCylinder;
import com.cfar.swim.worldwind.jaxb.scenario.ObstacleSphere;
import com.cfar.swim.worldwind.jaxb.scenario.Obstacles;

/**
 * Adapts obstacles.
 * 
 * @author Stephan Heinemann
 *
 */
public class ObstaclesAdapter extends XmlAdapter<Obstacles, Set<com.cfar.swim.worldwind.render.Obstacle>> {
	
	/**
	 * Unmarshals obstacles.
	 * 
	 * @param obstacles the obstacles to be unmarshalled
	 * 
	 * @return the unmarshalled obstacles
	 * 
	 * @throws Exception if the obstacles cannot be unmarshalled
	 * 
	 * @see XmlAdapter#unmarshal(Object)
	 */
	@Override
	public Set<com.cfar.swim.worldwind.render.Obstacle> unmarshal(Obstacles obstacles) throws Exception {
		Set<com.cfar.swim.worldwind.render.Obstacle> unmarshalledObstacles = new HashSet<>();
		
		for (Object obstacle : obstacles.getBoxOrCylinderOrSphere()) {
			if (obstacle instanceof ObstacleBox) {
				unmarshalledObstacles.add(new ObstacleBoxAdapter().unmarshal((ObstacleBox) obstacle));
			} else if (obstacle instanceof ObstacleCylinder) {
				unmarshalledObstacles.add(new ObstacleCylinderAdapter().unmarshal((ObstacleCylinder) obstacle));
			} else if (obstacle instanceof ObstacleSphere) {
				unmarshalledObstacles.add(new ObstacleSphereAdapter().unmarshal((ObstacleSphere) obstacle));
			}
		}
		
		return unmarshalledObstacles;
	}
	
	/**
	 * Marshals obstacles.
	 * 
	 * @param obstacles the obstacles to be marshalled
	 * 
	 * @return the marshalled obstacles
	 * 
	 * @throws Exception if the obstacles cannot be marshalled
	 * 
	 * @see XmlAdapter#unmarshal(Object)
	 */
	@Override
	public Obstacles marshal(Set<com.cfar.swim.worldwind.render.Obstacle> obstacles) throws Exception {
		Obstacles marshalledObstacles = new Obstacles();
		
		for (com.cfar.swim.worldwind.render.Obstacle obstacle : obstacles) {
			if (obstacle instanceof com.cfar.swim.worldwind.render.airspaces.ObstacleBox) {
				marshalledObstacles.getBoxOrCylinderOrSphere().add(
						new ObstacleBoxAdapter().marshal((com.cfar.swim.worldwind.render.airspaces.ObstacleBox) obstacle));
			} else if (obstacle instanceof com.cfar.swim.worldwind.render.airspaces.ObstacleCylinder) {
				marshalledObstacles.getBoxOrCylinderOrSphere().add(
						new ObstacleCylinderAdapter().marshal((com.cfar.swim.worldwind.render.airspaces.ObstacleCylinder) obstacle));
			} else if (obstacle instanceof com.cfar.swim.worldwind.render.airspaces.ObstacleSphere) {
				marshalledObstacles.getBoxOrCylinderOrSphere().add(
						new ObstacleSphereAdapter().marshal((com.cfar.swim.worldwind.render.airspaces.ObstacleSphere) obstacle));
			}
		}
		
		return marshalledObstacles;
	}
	
}
