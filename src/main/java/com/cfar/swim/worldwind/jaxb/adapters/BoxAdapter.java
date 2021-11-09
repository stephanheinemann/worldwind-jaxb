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

import com.cfar.swim.worldwind.jaxb.scenario.Box;

/**
 * Adapts a box.
 * 
 * @author Stephan Heinemann
 *
 */
public class BoxAdapter extends XmlAdapter<Box, gov.nasa.worldwind.render.airspaces.Box> {
	
	/**
	 * Unmarshals a box.
	 * 
	 * @param box the box to be unmarshalled
	 * 
	 * @return the unmarshalled box
	 * 
	 * @throws Exception if the box cannot be unmarshalled
	 * 
	 * @see XmlAdapter#unmarshal(Object)
	 */
	@Override
	public gov.nasa.worldwind.render.airspaces.Box unmarshal(Box box) throws Exception {
		gov.nasa.worldwind.render.airspaces.Box unmarshalledBox =
				new gov.nasa.worldwind.render.airspaces.Box(
						new LocationAdapter().unmarshal(box.getBegin()),
						new LocationAdapter().unmarshal(box.getEnd()),
						box.getLeftWidth(), box.getRightWidth());
		unmarshalledBox.setAltitudes(box.getBottom(), box.getTop());
		
		return unmarshalledBox;
	}
	
	/**
	 * Marshals a box.
	 * 
	 * @param box the box to be marshalled
	 * 
	 * @return the marshalled box
	 * 
	 * @throws Exception if the box cannot be marshalled
	 * 
	 * @see XmlAdapter#unmarshal(Object)
	 */
	@Override
	public Box marshal(gov.nasa.worldwind.render.airspaces.Box box) throws Exception {
		Box marshalledBox = new Box();
		
		marshalledBox.setBegin(new LocationAdapter().marshal(box.getLocations()[0]));
		marshalledBox.setEnd(new LocationAdapter().marshal(box.getLocations()[1]));
		marshalledBox.setLeftWidth(box.getWidths()[0]);
		marshalledBox.setRightWidth(box.getWidths()[1]);
		marshalledBox.setBottom(box.getAltitudes()[0]);
		marshalledBox.setTop(box.getAltitudes()[1]);
		
		return marshalledBox;
	}
	
}
