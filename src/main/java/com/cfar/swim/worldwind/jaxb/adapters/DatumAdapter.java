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

import com.cfar.swim.worldwind.jaxb.scenario.Datum;

import gov.nasa.worldwind.avlist.AVKey;

/**
 * Adapts an altitude datum.
 * 
 * @author Stephan Heinemann
 *
 */
public class DatumAdapter extends XmlAdapter<Datum, String> {
	
	/**
	 * Unmarshals an altitude datum.
	 * 
	 * @param datum the altitude datum to be unmarshalled
	 * 
	 * @return the unmarshalled altitude datum
	 * 
	 * @throws Exception if the altitude datum cannot be unmarshalled
	 * 
	 * @see XmlAdapter#unmarshal(Object)
	 */
	@Override
	public String unmarshal(Datum datum) throws Exception {
		String unmarshalledDatum = AVKey.ABOVE_MEAN_SEA_LEVEL;
		
		switch (datum) {
		case AGL:
			unmarshalledDatum = AVKey.ABOVE_GROUND_LEVEL;
			break;
		case AGR:
			unmarshalledDatum = AVKey.ABOVE_GROUND_REFERENCE;
			break;
		case ASL:
			unmarshalledDatum = AVKey.ABOVE_MEAN_SEA_LEVEL;
			break;
		default:
			throw new UnsupportedDataTypeException("unsupported altitude datum");
		}
		
		return unmarshalledDatum;
	}
	
	/**
	 * Marshals an altitude datum.
	 * 
	 * @param datum the altitude datum to be marshalled
	 * 
	 * @return the marshalled altitude datum
	 * 
	 * @throws Exception if the altitude datum cannot be marshalled
	 * 
	 * @see XmlAdapter#unmarshal(Object)
	 */
	@Override
	public Datum marshal(String datum) throws Exception {
		Datum marshalledDatum = Datum.ASL;
		
		switch (datum) {
		case AVKey.ABOVE_GROUND_LEVEL:
			marshalledDatum = Datum.AGL;
			break;
		case AVKey.ABOVE_GROUND_REFERENCE:
			marshalledDatum = Datum.AGR;
			break;
		case AVKey.ABOVE_MEAN_SEA_LEVEL:
			marshalledDatum = Datum.ASL;
			break;
		default:
			throw new UnsupportedDataTypeException("unsupported altitude datum");
		}
		
		return marshalledDatum;
	}
	
}
