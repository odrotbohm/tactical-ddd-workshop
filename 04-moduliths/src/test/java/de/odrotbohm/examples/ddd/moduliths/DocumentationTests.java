/*
 * Copyright 2021 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package de.odrotbohm.examples.ddd.moduliths;

import java.io.IOException;

import org.junit.jupiter.api.Test;
import org.moduliths.docs.Documenter;
import org.moduliths.docs.Documenter.CanvasOptions;
import org.moduliths.docs.Documenter.Options;
import org.moduliths.model.Modules;

/**
 * @author Oliver Drotbohm
 */
class DocumentationTests {

	@Test
	void createsDocumentation() throws IOException {

		var modules = Modules.of(ModulithsApplication.class);

		var canvasOptions = CanvasOptions.defaults().withApiBase("http://localhost:8080/javadoc");
		var options = Options.defaults();

		new Documenter(modules)

				// Overview component diagram
				.writeModulesAsPlantUml(options)

				// Component diagram per module
				.writeIndividualModulesAsPlantUml(options)

				// Module overview
				.writeModuleCanvases(canvasOptions);
	}
}
