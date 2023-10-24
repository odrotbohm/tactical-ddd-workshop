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
package de.odrotbohm.examples.ddd.verification;

import static org.junit.jupiter.api.Assumptions.*;

import org.jmolecules.archunit.JMoleculesDddRules;

import com.tngtech.archunit.core.domain.JavaClasses;
import com.tngtech.archunit.core.importer.ImportOption;
import com.tngtech.archunit.junit.AnalyzeClasses;
import com.tngtech.archunit.junit.ArchTest;

/**
 * @author Oliver Drotbohm
 */
@AnalyzeClasses(
		packagesOf = AggregateStructureVerificationTests.class,
		importOptions = ImportOption.DoNotIncludeTests.class)
class AggregateStructureVerificationTests {

	/**
	 * <ol>
	 * <li>Annotate {@link Order} with {@link AggregateRoot}.</li>
	 * <li>See the test fail as you need to declare a property annotated with {@link Identity}.</li>
	 * </ol>
	 */
	@ArchTest
	void annotatedAggregateRootMustDeclareIdentifier(JavaClasses classes) {

		assumeTrue(TestUtils.isAggregateRoot(Order.class));

		JMoleculesDddRules.annotatedEntitiesAndAggregatesNeedToHaveAnIdentifier()
				.check(classes);
	}

	/**
	 * <ol>
	 * <li>Annotate {@link Customer} with {@link AggregateRoot} and its {@code id} field with {@link Identity} (the latter
	 * to satisfy {@link #annotatedAggregateRootMustDeclareIdentifier(JavaClasses)}).</li>
	 * <li>Annotate {@link Order} with {@link AggregateRoot}.</li>
	 * <li>See the test fail as {@link Order} uses an entire {@link Customer} reference. Resolve by replacing that with
	 * {@link CustomerId}.</li>
	 * <li>Switch to the type based model, i.e. implement {@link org.jmolecules.ddd.types.AggregateRoot} and
	 * {@link Identifier} instead of using the annotations.</li>
	 * <li>Replace the {@link CustomerId} with {@link Association}.</li>
	 * </ol>
	 *
	 * @param classes
	 */
	@ArchTest
	void rejectInvalidAggregateReference(JavaClasses classes) {

		assumeTrue(TestUtils.isAggregateRoot(Order.class));

		JMoleculesDddRules.aggregateReferencesShouldBeViaIdOrAssociation()
				.check(classes);
	}
}
