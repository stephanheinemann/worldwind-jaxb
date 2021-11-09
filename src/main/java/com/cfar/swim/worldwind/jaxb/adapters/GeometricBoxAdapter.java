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
import com.cfar.swim.worldwind.jaxb.scenario.Location;

import gov.nasa.worldwind.geom.Sector;
import gov.nasa.worldwind.globes.Globe;

/**
 * Adapts a geometric box.
 * 
 * @author Stephan Heinemann
 *
 */
public class GeometricBoxAdapter extends XmlAdapter<GeometricBox, Box> {
	
	/** the globe of this geometric box adapter */
	private final Globe globe;
	
	/**
	 * Constructs a new geometric box adapter for a globe.
	 * 
	 * @param globe the globe
	 */
	public GeometricBoxAdapter(Globe globe) {
		this.globe = globe;
	}
	
	/**
	 * Unmarshals a geometric box.
	 * 
	 * @param box the geometric box to be unmarshalled
	 * 
	 * @return the unmarshalled geometric box
	 * 
	 * @throws Exception if the geometric box cannot be unmarshalled
	 * 
	 * @see XmlAdapter#unmarshal(Object)
	 */
	@Override
	public Box unmarshal(GeometricBox box) throws Exception {
		Sector sector = new SectorAdapter().unmarshal(box);
		return new Box(Sector.computeBoundingBox(this.globe, 1d, sector, box.getBottom(), box.getTop()));
	}
	
	/**
	 * Marshals a geometric box.
	 * 
	 * @param box the geometric box to be marshalled
	 * 
	 * @return the marshalled geometric box
	 * 
	 * @throws Exception if the geometric box cannot be marshalled
	 * 
	 * @see XmlAdapter#unmarshal(Object)
	 */
	@Override
	public GeometricBox marshal(Box box) throws Exception {
		GeometricBox marshalledBox = new GeometricBox();
		
		Location southWest = new LocationAdapter().marshal(
				this.globe.computePositionFromPoint(
						box.getCorners()[Box.CORNER_INDEX_BOTTOM_LOWER_LEFT]));
		Location northEast = new LocationAdapter().marshal(
				this.globe.computePositionFromPoint(
						box.getCorners()[Box.CORNER_INDEX_TOP_UPPER_RIGHT]));
		marshalledBox.setSouthWest(southWest);
		marshalledBox.setNorthEast(northEast);		
		marshalledBox.setBottom(this.globe.computePositionFromPoint(
				box.getCorners()[Box.CORNER_INDEX_BOTTOM_LOWER_LEFT]).getAltitude());
		marshalledBox.setTop(this.globe.computePositionFromPoint(
				box.getCorners()[Box.CORNER_INDEX_TOP_UPPER_RIGHT]).getAltitude());
		
		return marshalledBox;
	}
	
}
