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

import com.cfar.swim.worldwind.jaxb.scenario.Cylinder;
import com.cfar.swim.worldwind.jaxb.scenario.ObstacleCylinder;

import gov.nasa.worldwind.render.airspaces.CappedCylinder;

/**
 * Adapts an obstacle cylinder.
 * 
 * @author Stephan Heinemann
 *
 */
public class ObstacleCylinderAdapter extends XmlAdapter<ObstacleCylinder, com.cfar.swim.worldwind.render.airspaces.ObstacleCylinder> {
	
	/**
	 * Unmarshals an obstacle cylinder.
	 * 
	 * @param obstacleCylinder the obstacle cylinder to be unmarshalled
	 * 
	 * @return the unmarshalled obstacle cylinder
	 * 
	 * @throws Exception if the obstacle cylinder cannot be unmarshalled
	 * 
	 * @see XmlAdapter#unmarshal(Object)
	 */
	@Override
	public com.cfar.swim.worldwind.render.airspaces.ObstacleCylinder unmarshal(ObstacleCylinder obstacleCylinder) throws Exception {
		CappedCylinder cylinder = new CylinderAdapter().unmarshal(obstacleCylinder);
		com.cfar.swim.worldwind.render.airspaces.ObstacleCylinder unmarshalledObstacleCylinder =
				new com.cfar.swim.worldwind.render.airspaces.ObstacleCylinder(
						cylinder.getCenter(),
						cylinder.getAltitudes()[0],
						cylinder.getAltitudes()[1],
						cylinder.getRadii()[1]);
		
		unmarshalledObstacleCylinder.setAltitudeDatum(cylinder.getAltitudeDatum()[0], cylinder.getAltitudeDatum()[1]);
		unmarshalledObstacleCylinder.setCostInterval(new CostIntervalAdapter().unmarshal(obstacleCylinder.getCostInterval()));
		
		if (null != obstacleCylinder.getDepiction()) {
			unmarshalledObstacleCylinder.setDepiction(new DepictionAdapter(
					unmarshalledObstacleCylinder.getReferencePosition()).unmarshal(obstacleCylinder.getDepiction()));
		}
			
		return unmarshalledObstacleCylinder;
	}
	
	/**
	 * Marshals an obstacle cylinder.
	 * 
	 * @param obstacleCylinder the obstacle cylinder to be marshalled
	 * 
	 * @return the marshalled obstacle cylinder
	 * 
	 * @throws Exception if the obstacle cylinder cannot be marshalled
	 * 
	 * @see XmlAdapter#unmarshal(Object)
	 */
	@Override
	public ObstacleCylinder marshal(com.cfar.swim.worldwind.render.airspaces.ObstacleCylinder obstacleCylinder) throws Exception {
		ObstacleCylinder marshalledObstacleCylinder = new ObstacleCylinder();
		
		Cylinder cylinder = new CylinderAdapter().marshal(obstacleCylinder);
		marshalledObstacleCylinder.setLocation(cylinder.getLocation());
		marshalledObstacleCylinder.setRadius(cylinder.getRadius());
		marshalledObstacleCylinder.setBottom(cylinder.getBottom());
		marshalledObstacleCylinder.setTop(cylinder.getTop());
		marshalledObstacleCylinder.setBottomDatum(cylinder.getBottomDatum());
		marshalledObstacleCylinder.setTopDatum(cylinder.getTopDatum());
		marshalledObstacleCylinder.setCostInterval(new CostIntervalAdapter().marshal(obstacleCylinder.getCostInterval()));
		
		if (obstacleCylinder.hasDepiction()) {
			marshalledObstacleCylinder.setDepiction(
					new DepictionAdapter(obstacleCylinder.getReferencePosition()).marshal(obstacleCylinder.getDepiction()));
		}
		
		return marshalledObstacleCylinder;
	}
	
}
