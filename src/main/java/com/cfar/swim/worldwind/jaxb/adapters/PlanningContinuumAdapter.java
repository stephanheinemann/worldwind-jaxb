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

import com.cfar.swim.worldwind.geom.Box;
import com.cfar.swim.worldwind.jaxb.scenario.GeometricBox;
import com.cfar.swim.worldwind.jaxb.scenario.PlanningContinuum;

import gov.nasa.worldwind.globes.Globe;

/**
 * Adapts a planning continuum.
 * 
 * @author Stephan Heinemann
 *
 */
public class PlanningContinuumAdapter extends XmlAdapter<PlanningContinuum, com.cfar.swim.worldwind.environments.PlanningContinuum> {
	
	/** the globe of this planning continuum adapter */
	private final Globe globe;
	
	/**
	 * Constructs a new planning continuum adapter for a globe.
	 * 
	 * @param globe the globe
	 */
	public PlanningContinuumAdapter(Globe globe) {
		this.globe = globe;
	}
	
	/**
	 * Unmarshals a planning continuum.
	 * 
	 * @param continuum the planning continuum to be unmarshalled
	 * 
	 * @return the unmarshalled planning continuum
	 * 
	 * @throws Exception if the planning continuum cannot be unmarshalled
	 * 
	 * @see XmlAdapter#unmarshal(Object)
	 */
	@Override
	public com.cfar.swim.worldwind.environments.PlanningContinuum unmarshal(PlanningContinuum continuum) throws Exception {
		Box box = new GeometricBoxAdapter(this.globe).unmarshal(continuum);
		com.cfar.swim.worldwind.environments.PlanningContinuum unmarshalledContinuum =
				new com.cfar.swim.worldwind.environments.PlanningContinuum(box);
		unmarshalledContinuum.setGlobe(this.globe);
		unmarshalledContinuum.setNormalizer(box.getDiameter());
		return unmarshalledContinuum;
	}
	
	/**
	 * Marshals a planning continuum.
	 * 
	 * @param continuum the planning continuum to be marshalled
	 * 
	 * @return the marshalled planning continuum
	 * 
	 * @throws Exception if the planning continuum cannot be marshalled
	 * 
	 * @see XmlAdapter#unmarshal(Object)
	 */
	@Override
	public PlanningContinuum marshal(com.cfar.swim.worldwind.environments.PlanningContinuum continuum) throws Exception {
		PlanningContinuum marshalledContinuum = new PlanningContinuum();
		
		GeometricBox box = new GeometricBoxAdapter(continuum.getGlobe()).marshal(continuum);
		marshalledContinuum.setSouthWest(box.getSouthWest());
		marshalledContinuum.setNorthEast(box.getNorthEast());
		marshalledContinuum.setBottom(box.getBottom());
		marshalledContinuum.setTop(box.getTop());
		marshalledContinuum.setResolution(continuum.getResolution());
		
		return marshalledContinuum;
	}
	
}
