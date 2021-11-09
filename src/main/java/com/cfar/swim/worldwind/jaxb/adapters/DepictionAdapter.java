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

import com.cfar.swim.worldwind.jaxb.scenario.Depiction;
import com.cfar.swim.worldwind.jaxb.scenario.Symbology;
import com.cfar.swim.worldwind.render.annotations.DepictionAnnotation;

import gov.nasa.worldwind.geom.Position;
import gov.nasa.worldwind.symbology.TacticalPoint;
import gov.nasa.worldwind.symbology.TacticalSymbol;
import gov.nasa.worldwind.symbology.milstd2525.MilStd2525GraphicFactory;
import gov.nasa.worldwind.symbology.milstd2525.MilStd2525TacticalSymbol;

/**
 * Adapts a depiction.
 * 
 * @author Stephan Heinemann
 *
 */
public class DepictionAdapter extends XmlAdapter<Depiction, com.cfar.swim.worldwind.util.Depiction> {
	
	/** the position of this depiction adapter */
	private final Position position;
	
	/** the symbol factory of this depiction adapter */
	private final MilStd2525GraphicFactory symbolFactory = new MilStd2525GraphicFactory();
	
	/**
	 * Constructs a new depiction adapter for a position.
	 * 
	 * @param position the position
	 */
	public DepictionAdapter(Position position) {
		this.position = position;
	}
	
	/**
	 * Unmarshals a depiction.
	 * 
	 * @param depiction the depiction to be unmarshalled
	 * 
	 * @return the unmarshalled depiction
	 * 
	 * @throws Exception if the depiction cannot be unmarshalled
	 * 
	 * @see XmlAdapter#unmarshal(Object)
	 */
	@Override
	public com.cfar.swim.worldwind.util.Depiction unmarshal(Depiction depiction) throws Exception {
		com.cfar.swim.worldwind.util.Depiction unmarshalledDepiction = null;
		
		if (Symbology.TACTICAL_POINT == depiction.getSymbology()) {
			unmarshalledDepiction = new com.cfar.swim.worldwind.util.Depiction(
					this.symbolFactory.createPoint(depiction.getSidc(), this.position, null));
		} else if (Symbology.TACTICAL_SYMBOL == depiction.getSymbology()) {
			unmarshalledDepiction = new com.cfar.swim.worldwind.util.Depiction(
					new MilStd2525TacticalSymbol(depiction.getSidc(), this.position));
		} else {
			throw new UnsupportedDataTypeException("unsupported depiction");
		}
		
		if (null != depiction.getText()) {
			if (null != depiction.getImage()) {
				unmarshalledDepiction.setAnnotation(new DepictionAnnotation(
						depiction.getImage(), depiction.getText(), this.position));
			} else {
				unmarshalledDepiction.setAnnotation(new DepictionAnnotation(
						depiction.getText(), this.position));
			}
		}
		unmarshalledDepiction.setVisible(true);
		
		return unmarshalledDepiction;
	}
	
	/**
	 * Marshals a depiction.
	 * 
	 * @param depiction the depiction to be marshalled
	 * 
	 * @return the marshalled depiction
	 * 
	 * @throws Exception if the depiction cannot be marshalled
	 * 
	 * @see XmlAdapter#unmarshal(Object)
	 */
	@Override
	public Depiction marshal(com.cfar.swim.worldwind.util.Depiction depiction) throws Exception {
		Depiction marshalledDepiction = new Depiction();
		
		if (depiction.getTacticalDepiction() instanceof TacticalPoint) {
			marshalledDepiction.setSymbology(Symbology.TACTICAL_POINT);
		} else if (depiction.getTacticalDepiction() instanceof TacticalSymbol) {
			marshalledDepiction.setSymbology(Symbology.TACTICAL_SYMBOL);
		} else {
			throw new UnsupportedDataTypeException("unsupported depiction");
		}
		
		marshalledDepiction.setSidc(depiction.getSymbolIdentifier());
		if (depiction.hasAnnotation()) {
			marshalledDepiction.setText(depiction.getAnnotation().getText());
			if (null != depiction.getAnnotation().getAttributes().getImageSource()) {
				marshalledDepiction.setImage(depiction.getAnnotation().getAttributes().getImageSource().toString());
			}
		}
		
		return marshalledDepiction;
	}
	
}
