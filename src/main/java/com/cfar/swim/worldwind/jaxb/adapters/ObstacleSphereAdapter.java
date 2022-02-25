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

import com.cfar.swim.worldwind.jaxb.scenario.ObstacleSphere;
import com.cfar.swim.worldwind.jaxb.scenario.Sphere;

import gov.nasa.worldwind.render.airspaces.SphereAirspace;

/**
 * Adapts an obstacle sphere.
 * 
 * @author Stephan Heinemann
 *
 */
public class ObstacleSphereAdapter extends XmlAdapter<ObstacleSphere, com.cfar.swim.worldwind.render.airspaces.ObstacleSphere> {
	
	/**
	 * Unmarshals an obstacle sphere.
	 * 
	 * @param obstacleSphere the obstacle sphere to be unmarshalled
	 * 
	 * @return the unmarshalled obstacle sphere
	 * 
	 * @throws Exception if the obstacle sphere cannot be unmarshalled
	 * 
	 * @see XmlAdapter#unmarshal(Object)
	 */
	@Override
	public com.cfar.swim.worldwind.render.airspaces.ObstacleSphere unmarshal(ObstacleSphere obstacleSphere) throws Exception {
		SphereAirspace sphere = new SphereAdapter().unmarshal(obstacleSphere);
		com.cfar.swim.worldwind.render.airspaces.ObstacleSphere unmarshalledObstacleSphere =
				new com.cfar.swim.worldwind.render.airspaces.ObstacleSphere(
						sphere.getReferencePosition(),
						sphere.getRadius());
		
		unmarshalledObstacleSphere.setAltitudeDatum(sphere.getAltitudeDatum()[0], sphere.getAltitudeDatum()[1]);
		unmarshalledObstacleSphere.setCostInterval(new CostIntervalAdapter().unmarshal(obstacleSphere.getCostInterval()));
		
		if (null != obstacleSphere.getDepiction()) {
			unmarshalledObstacleSphere.setDepiction(new DepictionAdapter(
					unmarshalledObstacleSphere.getReferencePosition()).unmarshal(obstacleSphere.getDepiction()));
		}
		
		return unmarshalledObstacleSphere;
	}
	
	/**
	 * Marshals an obstacle sphere.
	 * 
	 * @param obstacleSphere the obstacle sphere to be marshalled
	 * 
	 * @return the marshalled obstacle sphere
	 * 
	 * @throws Exception if the obstacle sphere cannot be marshalled
	 * 
	 * @see XmlAdapter#unmarshal(Object)
	 */
	@Override
	public ObstacleSphere marshal(com.cfar.swim.worldwind.render.airspaces.ObstacleSphere obstacleSphere) throws Exception {
		ObstacleSphere marshalledObstacleSphere = new ObstacleSphere();
		
		Sphere sphere = new SphereAdapter().marshal(obstacleSphere);
		marshalledObstacleSphere.setCenter(sphere.getCenter());
		marshalledObstacleSphere.setRadius(sphere.getRadius());
		marshalledObstacleSphere.setCenterDatum(sphere.getCenterDatum());
		marshalledObstacleSphere.setCostInterval(new CostIntervalAdapter().marshal(obstacleSphere.getCostInterval()));
		
		if (obstacleSphere.hasDepiction()) {
			marshalledObstacleSphere.setDepiction(new DepictionAdapter(
					obstacleSphere.getReferencePosition()).marshal(obstacleSphere.getDepiction()));
		}
		
		return marshalledObstacleSphere;
	}
	
}
