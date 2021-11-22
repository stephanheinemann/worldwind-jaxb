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

import gov.nasa.worldwind.render.airspaces.CappedCylinder;

/**
 * Adapts a cylinder.
 * 
 * @author Stephan Heinemann
 *
 */
public class CylinderAdapter extends XmlAdapter<Cylinder, CappedCylinder> {
	
	/**
	 * Unmarshals a cylinder.
	 * 
	 * @param cylinder the cylinder to be unmarshalled
	 * 
	 * @return the unmarshalled cylinder
	 * 
	 * @throws Exception if the cylinder cannot be unmarshalled
	 * 
	 * @see XmlAdapter#unmarshal(Object)
	 */
	@Override
	public CappedCylinder unmarshal(Cylinder cylinder) throws Exception {
		CappedCylinder unmarshalledCylinder = new CappedCylinder(
				new LocationAdapter().unmarshal(cylinder.getLocation()),
				cylinder.getRadius());
		
		unmarshalledCylinder.setAltitudes(cylinder.getBottom(), cylinder.getTop());
		unmarshalledCylinder.setAltitudeDatum(
				new DatumAdapter().unmarshal(cylinder.getBottomDatum()),
				new DatumAdapter().unmarshal(cylinder.getTopDatum()));
		
		return unmarshalledCylinder;
	}
	
	/**
	 * Marshals a cylinder.
	 * 
	 * @param cylinder the cylinder to be marshalled
	 * 
	 * @return the marshalled cylinder
	 * 
	 * @throws Exception if the cylinder cannot be marshalled
	 * 
	 * @see XmlAdapter#unmarshal(Object)
	 */
	@Override
	public Cylinder marshal(CappedCylinder cylinder) throws Exception {
		Cylinder marshalledCylinder = new Cylinder();
		
		marshalledCylinder.setLocation(new LocationAdapter().marshal(cylinder.getCenter()));
		marshalledCylinder.setRadius(cylinder.getRadii()[1]);
		marshalledCylinder.setBottom(cylinder.getAltitudes()[0]);
		marshalledCylinder.setTop(cylinder.getAltitudes()[1]);
		marshalledCylinder.setBottomDatum(new DatumAdapter().marshal(cylinder.getAltitudeDatum()[0]));
		marshalledCylinder.setTopDatum(new DatumAdapter().marshal(cylinder.getAltitudeDatum()[1]));
		
		return marshalledCylinder;
	}
	
}
