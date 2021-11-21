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
package de.odrotbohm.examples.ddd.b.vo;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assumptions.*;

import de.odrotbohm.examples.ddd.support.TestUtils;
import lombok.RequiredArgsConstructor;

import org.jmolecules.ddd.types.ValueObject;
import org.junit.jupiter.api.Test;
import org.moduliths.test.ModuleTest;

import com.fasterxml.jackson.databind.ObjectMapper;

@ModuleTest
@RequiredArgsConstructor
class EmailAddressSerializationTests {

	private final ObjectMapper mapper;

	@Test
	void serializesEmailAddressAsNestedValue() throws Exception {

		var email = EmailAddress.of("foo@bar.com");

		assumeFalse(TestUtils.isValueObject(email.getClass()));

		assertThat(mapper.writeValueAsString(EmailAddress.of("foo@bar.com")))
				.isEqualTo("{\"value\":\"foo@bar.com\"}");
	}

	@Test
	void serializesEmailAddressValueObjectAsSimpleValue() throws Exception {

		var email = EmailAddress.of("foo@bar.com");

		assumeTrue(ValueObject.class.isInstance(email));

		assertThat(mapper.writeValueAsString(email)).isEqualTo("\"foo@bar.com\"");
	}
}
