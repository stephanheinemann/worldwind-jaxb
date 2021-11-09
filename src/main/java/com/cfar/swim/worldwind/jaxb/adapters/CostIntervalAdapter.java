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

import com.cfar.swim.worldwind.jaxb.scenario.CostInterval;
import com.cfar.swim.worldwind.jaxb.scenario.TimeInterval;

/**
 * Adapts a cost interval.
 * 
 * @author Stephan Heinemann
 *
 */
public class CostIntervalAdapter extends XmlAdapter<CostInterval, com.cfar.swim.worldwind.planning.CostInterval> {
	
	/**
	 * Unmarshals a cost interval.
	 * 
	 * @param costInterval the cost interval to be unmarshalled
	 * 
	 * @return the unmarshalled cost interval
	 * 
	 * @throws Exception if the cost interval cannot be unmarshalled
	 * 
	 * @see XmlAdapter#unmarshal(Object)
	 */
	@Override
	public com.cfar.swim.worldwind.planning.CostInterval unmarshal(CostInterval costInterval) throws Exception {
		com.cfar.swim.worldwind.planning.CostInterval unmarshalledCostInterval =
				new com.cfar.swim.worldwind.planning.CostInterval(costInterval.getId());
		
		com.cfar.swim.worldwind.planning.TimeInterval timeInterval =
				new TimeIntervalAdapter().unmarshal(costInterval);
		unmarshalledCostInterval.setLower(timeInterval.getLower());
		unmarshalledCostInterval.setUpper(timeInterval.getUpper());
		unmarshalledCostInterval.setCost(costInterval.getCost());
		
		if (costInterval.isEnabled()) {
			unmarshalledCostInterval.enable();
		} else {
			unmarshalledCostInterval.disable();
		}
		
		return unmarshalledCostInterval;
	}
	
	/**
	 * Marshals a cost interval.
	 * 
	 * @param costInterval the cost interval to be marshalled
	 * 
	 * @return the marshalled cost interval
	 * 
	 * @throws Exception if the cost interval cannot be marshalled
	 * 
	 * @see XmlAdapter#unmarshal(Object)
	 */
	@Override
	public CostInterval marshal(com.cfar.swim.worldwind.planning.CostInterval costInterval) throws Exception {
		CostInterval marshalledCostInterval = new CostInterval();
		
		TimeInterval timeInterval = new TimeIntervalAdapter().marshal(costInterval);
		marshalledCostInterval.setStart(timeInterval.getStart());
		marshalledCostInterval.setEnd(timeInterval.getEnd());
		marshalledCostInterval.setId(costInterval.getId());
		marshalledCostInterval.setCost(costInterval.getCost());
		marshalledCostInterval.setEnabled(costInterval.isEnabled());
		
		return marshalledCostInterval;
	}
	
}
