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
package com.cfar.swim.worldwind.jaxb.tests;

import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;

import javax.xml.bind.JAXBException;

import org.junit.Test;

import com.cfar.swim.worldwind.jaxb.ScenarioMarshaller;
import com.cfar.swim.worldwind.jaxb.ScenarioUnmarshaller;
import com.cfar.swim.worldwind.jaxb.scenario.Scenario;

public class RoundtripTest {
	
	private class XMLFilenameFilter implements FilenameFilter {
		
		public static final String XML_FILENAME_EXT = ".xml";
		
		@Override
		public boolean accept(File dir, String name) {
			return name.endsWith(XML_FILENAME_EXT);
		}
		
	}
	
	public static final String SCENARIO_RESOURCES_DIR = "src/test/resources/xml";
	public static final String SCENARIO_TARGET_DIR = "target/generated-test-sources/xml";
	
	@Test
	public void test() throws JAXBException, IOException {
		// create JAXB marshaller and unmarshaller
		ScenarioMarshaller scenarioMarshaller = new ScenarioMarshaller();
		ScenarioUnmarshaller scenarioUnmarshaller = new ScenarioUnmarshaller();
		
		// unmarshall and marshall test resources
		File scenarioResourcesDir = new File(SCENARIO_RESOURCES_DIR);
		File[] scenarioFiles = scenarioResourcesDir.listFiles(new XMLFilenameFilter());
		File scenarioTargetDir = new File(SCENARIO_TARGET_DIR);
		scenarioTargetDir.mkdirs();
		
		for (File scenarioFile : scenarioFiles) {
			Scenario scenarioElement = (Scenario) scenarioUnmarshaller.unmarshal(scenarioFile);
			File marshalledScenarioFile = new File(SCENARIO_TARGET_DIR, scenarioFile.getName());
			marshalledScenarioFile.createNewFile();
			scenarioMarshaller.marshal(scenarioElement, marshalledScenarioFile);
		}
		
		// all test resources could be unmarshalled and marshalled
		assertTrue(true);
	}
	
}
