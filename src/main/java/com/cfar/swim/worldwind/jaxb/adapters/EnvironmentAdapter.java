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

import com.cfar.swim.worldwind.environments.PlanningContinuum;
import com.cfar.swim.worldwind.environments.PlanningGrid;
import com.cfar.swim.worldwind.jaxb.scenario.Environment;

import gov.nasa.worldwind.globes.Globe;

/**
 * Adapts an environment.
 * 
 * @author Stephan Heinemann
 *
 */
public class EnvironmentAdapter extends XmlAdapter<Environment, com.cfar.swim.worldwind.environments.Environment> {
	
	/** the globe of this environment adapter */
	private final Globe globe;
	
	/**
	 * Constructs a new environment adapter for a globe.
	 * 
	 * @param globe the globe
	 */
	public EnvironmentAdapter(Globe globe) {
		this.globe = globe;
	}
	
	/**
	 * Unmarshals an environment.
	 * 
	 * @param environment the environment to be unmarshalled
	 * 
	 * @return the unmarshalled environment
	 * 
	 * @throws Exception if the environment cannot be unmarshalled
	 * 
	 * @see XmlAdapter#unmarshal(Object)
	 */
	@Override
	public com.cfar.swim.worldwind.environments.Environment unmarshal(Environment environment) throws Exception {
		com.cfar.swim.worldwind.environments.Environment unmarshalledEnvironment = null;
		
		if (null != environment.getGrid()) {
			unmarshalledEnvironment = new PlanningGridAdapter(this.globe).unmarshal(environment.getGrid());
		} else if (null != environment.getContinuum()) {
			unmarshalledEnvironment = new PlanningContinuumAdapter(this.globe).unmarshal(environment.getContinuum());
		} else {
			throw new UnsupportedDataTypeException("unsupported environment");
		}
		
		return unmarshalledEnvironment;
	}
	
	/**
	 * Marshals an environment.
	 * 
	 * @param environment the environment to be marshalled
	 * 
	 * @return the marshalled environment
	 * 
	 * @throws Exception if the environment cannot be marshalled
	 * 
	 * @see XmlAdapter#unmarshal(Object)
	 */
	@Override
	public Environment marshal(com.cfar.swim.worldwind.environments.Environment environment) throws Exception {
		Environment marshalledEnvironment = new Environment();
		
		if (environment instanceof PlanningGrid) {
			marshalledEnvironment.setGrid(
					new PlanningGridAdapter(this.globe).marshal((PlanningGrid) environment));
		} else if (environment instanceof PlanningContinuum) {
			marshalledEnvironment.setContinuum(
					new PlanningContinuumAdapter(this.globe).marshal((PlanningContinuum) environment));
		} else {
			throw new UnsupportedDataTypeException("unsupported environment");
		}
		
		return marshalledEnvironment;
	}
	
}
