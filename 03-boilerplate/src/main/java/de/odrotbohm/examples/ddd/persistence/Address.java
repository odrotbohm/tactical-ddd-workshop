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
package de.odrotbohm.examples.ddd.persistence;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Value;

import java.util.regex.Pattern;

import org.jmolecules.ddd.types.ValueObject;

/**
 * @author Oliver Drotbohm
 */
@Value
@Embeddable // remove
@RequiredArgsConstructor
@NoArgsConstructor(force = true, access = AccessLevel.PRIVATE) // remove
class Address implements ValueObject {

	String street;
	ZipCode zip;
	String city;

	@Value
	@Embeddable // remove
	@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
	@NoArgsConstructor(force = true, access = AccessLevel.PRIVATE) // remove
	static class ZipCode implements ValueObject {

		private static final Pattern REGEX = Pattern.compile("[0-9]{5}");

		@Column(name = "zipCode") String value;

		public static ZipCode of(String source) {

			if (!REGEX.matcher(source).matches()) {
				throw new IllegalArgumentException("Invalid zip code %s!".formatted(source));
			}

			return new ZipCode(source);
		}
	}
}
