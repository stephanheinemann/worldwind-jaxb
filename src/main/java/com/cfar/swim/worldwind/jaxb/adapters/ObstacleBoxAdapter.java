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
import com.cfar.swim.worldwind.jaxb.scenario.ObstacleBox;

/**
 * Adapts an obstacle box.
 * 
 * @author Stephan Heinemann
 *
 */
public class ObstacleBoxAdapter extends XmlAdapter<ObstacleBox, com.cfar.swim.worldwind.render.airspaces.ObstacleBox> {
	
	/**
	 * Unmarshals an obstacle box.
	 * 
	 * @param obstacleBox the obstacle box to be unmarshalled
	 * 
	 * @return the unmarshalled obstacle box
	 * 
	 * @throws Exception if the obstacle box cannot be unmarshalled
	 * 
	 * @see XmlAdapter#unmarshal(Object)
	 */
	@Override
	public com.cfar.swim.worldwind.render.airspaces.ObstacleBox unmarshal(ObstacleBox obstacleBox) throws Exception {
		gov.nasa.worldwind.render.airspaces.Box box = new BoxAdapter().unmarshal(obstacleBox);
		com.cfar.swim.worldwind.render.airspaces.ObstacleBox unmarshalledObstacleBox =
				new com.cfar.swim.worldwind.render.airspaces.ObstacleBox(box);
		unmarshalledObstacleBox.setCostInterval(new CostIntervalAdapter().unmarshal(obstacleBox.getCostInterval()));
		
		if (null != obstacleBox.getDepiction()) {
			unmarshalledObstacleBox.setDepiction(new DepictionAdapter(
					box.getReferencePosition()).unmarshal(obstacleBox.getDepiction()));
		}
		
		return unmarshalledObstacleBox;
	}
	
	/**
	 * Marshals an obstacle box.
	 * 
	 * @param obstacleBox the obstacle box to be marshalled
	 * 
	 * @return the marshalled obstacle box
	 * 
	 * @throws Exception if the obstacle box cannot be marshalled
	 * 
	 * @see XmlAdapter#unmarshal(Object)
	 */
	@Override
	public ObstacleBox marshal(com.cfar.swim.worldwind.render.airspaces.ObstacleBox obstacleBox) throws Exception {
		ObstacleBox marshalledObstacleBox = new ObstacleBox();
		
		Box box = new BoxAdapter().marshal(obstacleBox);
		marshalledObstacleBox.setBegin(box.getBegin());
		marshalledObstacleBox.setEnd(box.getEnd());
		marshalledObstacleBox.setLeftWidth(box.getLeftWidth());
		marshalledObstacleBox.setRightWidth(box.getRightWidth());
		marshalledObstacleBox.setBottom(box.getBottom());
		marshalledObstacleBox.setTop(box.getTop());
		marshalledObstacleBox.setBottomDatum(box.getBottomDatum());
		marshalledObstacleBox.setTopDatum(box.getTopDatum());
		marshalledObstacleBox.setCostInterval(new CostIntervalAdapter().marshal(obstacleBox.getCostInterval()));
		
		if (obstacleBox.hasDepiction()) {
			marshalledObstacleBox.setDepiction(new DepictionAdapter(
					obstacleBox.getReferencePosition()).marshal(obstacleBox.getDepiction()));
		}
		
		return marshalledObstacleBox;
	}
	
}
