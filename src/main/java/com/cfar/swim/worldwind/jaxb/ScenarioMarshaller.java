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
package com.cfar.swim.worldwind.jaxb;

import java.io.File;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.helpers.AbstractMarshallerImpl;
import javax.xml.transform.Result;

import com.cfar.swim.worldwind.jaxb.adapters.ScenarioAdapter;
import com.cfar.swim.worldwind.jaxb.scenario.Scenario;

/**
 * A convenience class to simplify scenario marshalling.
 * 
 * @author Stephan Heinemann
 */
public class ScenarioMarshaller extends AbstractMarshallerImpl {
	
	/** the marshaller of this scenario marshaller */
	private Marshaller scenarioMarshaller;
	
	/**
	 * Constructs a scenario marshaller.
	 * 
	 * @throws JAXBException if the scenario marshaller cannot be constructed
	 */
	public ScenarioMarshaller() throws JAXBException {
		super();
		JAXBContext scenarioContext = JAXBContext.newInstance(
				com.cfar.swim.worldwind.jaxb.scenario.ObjectFactory.class);
		scenarioMarshaller = scenarioContext.createMarshaller();
	}
	
	/**
	 * Marshals a scenario.
	 * 
	 * @param jaxbElement the scenario JAXB element to be marshalled
	 * @param result the marshalled result
	 * 
	 * @see javax.xml.bind.Marshaller#marshal(Object, Result)
	 */
	@Override
	public void marshal(Object jaxbElement, Result result) throws JAXBException {
		scenarioMarshaller.marshal(jaxbElement, result);
	}
	
	/**
	 * Marshals a scenario into an output file.
	 * 
	 * @param scenario the scenario to be marshalled
	 * @param output the output file
	 * 
	 * @throws JAXBException if the scenario cannot be marshalled
	 */
	public void marshalScenario(com.cfar.swim.worldwind.session.Scenario scenario, File output) throws JAXBException {
		try {
			Scenario marshalledScenario = new ScenarioAdapter().marshal(scenario);
			this.marshal(marshalledScenario, output);
		} catch (Exception e) {
			throw new JAXBException(e);
		}
	}
	
}
