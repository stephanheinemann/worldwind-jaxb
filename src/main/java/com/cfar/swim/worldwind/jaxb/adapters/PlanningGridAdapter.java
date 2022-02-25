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

import java.math.BigInteger;

import javax.xml.bind.annotation.adapters.XmlAdapter;

import com.cfar.swim.worldwind.geom.Box;
import com.cfar.swim.worldwind.geom.Cube;
import com.cfar.swim.worldwind.jaxb.scenario.GeometricBox;
import com.cfar.swim.worldwind.jaxb.scenario.PlanningGrid;

import gov.nasa.worldwind.globes.Globe;

/**
 * Adapts a planning grid.
 * 
 * @author Stephan Heinemann
 *
 */
public class PlanningGridAdapter extends XmlAdapter<PlanningGrid, com.cfar.swim.worldwind.environments.PlanningGrid> {
	
	/** the globe of this planning grid adapter */
	private final Globe globe;
	
	/**
	 * Constructs a new planning grid adapter for a globe.
	 * 
	 * @param globe the globe
	 */
	public PlanningGridAdapter(Globe globe) {
		this.globe = globe;
	}
	
	/**
	 * Unmarshals a planning grid.
	 * 
	 * @param grid the planning grid to be unmarshalled
	 * 
	 * @return the unmarshalled planning grid
	 * 
	 * @throws Exception if the planning grid cannot be unmarshalled
	 * 
	 * @see XmlAdapter#unmarshal(Object)
	 */
	@Override
	public com.cfar.swim.worldwind.environments.PlanningGrid unmarshal(PlanningGrid grid) throws Exception {
		Box box = new GeometricBoxAdapter(this.globe).unmarshal(grid);
		double side = box.getRLength() / grid.getDivision().intValue();
		Cube envCube = new Cube(box.getOrigin(), box.getUnitAxes(), side);
        int sCells = Math.max(1, (int) Math.round(box.getSLength() / side));
        int tCells = Math.max(1, (int) Math.round(box.getTLength() / side));
        com.cfar.swim.worldwind.environments.PlanningGrid unmarshalledGrid =
        		new com.cfar.swim.worldwind.environments.PlanningGrid(
        				envCube, grid.getDivision().intValue(), sCells, tCells);
		unmarshalledGrid.setGlobe(this.globe);
        unmarshalledGrid.setNormalizer(box.getDiameter());
		
		return unmarshalledGrid;
	}
	
	/**
	 * Marshals a planning grid.
	 * 
	 * @param grid the planning grid to be marshalled
	 * 
	 * @return the marshalled planning grid
	 * 
	 * @throws Exception if the planning grid cannot be marshalled
	 * 
	 * @see XmlAdapter#unmarshal(Object)
	 */
	@Override
	public PlanningGrid marshal(com.cfar.swim.worldwind.environments.PlanningGrid grid) throws Exception {
		PlanningGrid marshalledGrid = new PlanningGrid();
		
		GeometricBox box = new GeometricBoxAdapter(grid.getGlobe()).marshal(grid);
		marshalledGrid.setSouthWest(box.getSouthWest());
		marshalledGrid.setNorthEast(box.getNorthEast());
		marshalledGrid.setBottom(box.getBottom());
		marshalledGrid.setTop(box.getTop());
		
		if (grid.hasChildren()) {
			marshalledGrid.setDivision(BigInteger.valueOf(
					Math.max(1l, Math.round(grid.getRLength() / grid.getChild(0, 0, 0).getRLength()))));
		} else {
			marshalledGrid.setDivision(BigInteger.ONE);
		}
		
		return marshalledGrid;
	}
	
}
