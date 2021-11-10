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
import javax.xml.bind.Unmarshaller;
import javax.xml.bind.UnmarshallerHandler;
import javax.xml.bind.helpers.AbstractUnmarshallerImpl;
import javax.xml.transform.sax.SAXSource;

import org.w3c.dom.Node;
import org.xml.sax.InputSource;
import org.xml.sax.XMLReader;

import com.cfar.swim.worldwind.jaxb.adapters.ScenarioAdapter;
import com.cfar.swim.worldwind.jaxb.scenario.Scenario;

/**
 * A convenience class to simplify scenario unmarshalling.
 * 
 * @author Stephan Heinemann
 */
public class ScenarioUnmarshaller extends AbstractUnmarshallerImpl {
	
	/** the unmarshaller of this scenario unmarshaller */
	Unmarshaller scenarioUnmarshaller;
	
	/**
	 * Constructs a scenario unmarshaller.
	 * 
	 * @throws JAXBException if the scenario unmarshaller cannot be constructed
	 */
	public ScenarioUnmarshaller() throws JAXBException {
		super();
		JAXBContext scenarioContext = JAXBContext.newInstance(
				com.cfar.swim.worldwind.jaxb.scenario.ObjectFactory.class);
		scenarioUnmarshaller = scenarioContext.createUnmarshaller();
	}
	
	/**
	 * Unmarshals a scenario.
	 * 
	 * @param node the scenario node to be unmarshalled
	 * 
	 * @return the unmarshalled scenario node
	 * 
	 * @throws JAXBException if the scenario cannot be unmarshalled
	 * 
	 * @see javax.xml.bind.Unmarshaller#unmarshal(Node)
	 */
	@Override
	public Object unmarshal(Node node) throws JAXBException {
		return scenarioUnmarshaller.unmarshal(node);
	}
	
	/**
	 * Gets the unmarshaller handler of this scenario unmarshaller.
	 * 
	 * @return the unmarshaller handler of this scenario unmarshaller
	 * 
	 * @see javax.xml.bind.Unmarshaller#getUnmarshallerHandler()
	 */
	@Override
	public UnmarshallerHandler getUnmarshallerHandler() {
		return scenarioUnmarshaller.getUnmarshallerHandler();
	}
	
	/**
	 * Unmarshals a scenario.
	 * 
	 * @param reader the XML reader of the scenario to be unmarshalled
	 * @param source the input source of the scenario to be unmarshalled
	 * 
	 * @return the unmarshalled scenario
	 * 
	 * @throws JAXBException if the scenario cannot be unmarshalled
	 * 
	 * @see javax.xml.bind.helpers.AbstractUnmarshallerImpl#unmarshal(XMLReader, InputSource)
	 */
	@Override
	protected Object unmarshal(XMLReader reader, InputSource source) throws JAXBException {
		SAXSource saxSource = new SAXSource(reader, source);
		return scenarioUnmarshaller.unmarshal(saxSource);
	}
	
	/**
	 * Unmarshals a scenario from an input file.
	 * 
	 * @param input the input file
	 * 
	 * @return the unmarshalled scenario or null
	 * 
	 * @throws JAXBExcetpion if the scenario cannot be unmarshalled
	 */
	public com.cfar.swim.worldwind.session.Scenario unmarshalScenario(File input) throws JAXBException {
		com.cfar.swim.worldwind.session.Scenario unmarshalledScenario = null;
		
		Object scenario = this.unmarshal(input);
		if (scenario instanceof Scenario) {
			try {
				unmarshalledScenario = new ScenarioAdapter().unmarshal((Scenario) scenario);
			} catch (Exception e) {
				throw new JAXBException(e);
			}
		}
		
		return unmarshalledScenario;
	}
	
}
