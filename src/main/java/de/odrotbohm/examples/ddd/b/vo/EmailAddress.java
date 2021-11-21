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

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.Value;

import java.util.regex.Pattern;

/**
 * A sample value object representing an email address applying fundamental verification on construction so that all
 * code using it knows that it's a value satisfying the fundamental semantic requirements of an email address.
 *
 * @author Oliver Drotbohm
 */
@Value
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
class EmailAddress /* implements ValueObject */ {

	private static final Pattern REGEX = Pattern.compile("[^@ \\t\\r\\n]+@[^@ \\t\\r\\n]+\\.[^@ \\t\\r\\n]+");

	String value;

	static EmailAddress of(String source) {

		if (!REGEX.matcher(source).matches()) {
			throw new IllegalArgumentException("Invalid email address %s!".formatted(source));
		}

		return new EmailAddress(source);
	}
}
