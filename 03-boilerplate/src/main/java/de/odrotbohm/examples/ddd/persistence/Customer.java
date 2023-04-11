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

import jakarta.persistence.Embeddable;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import java.io.Serializable;
import java.util.UUID;

/**
 * @author Oliver Drotbohm
 */
@Getter
@Entity // TODO: 30 C - Remove
@NoArgsConstructor(force = true, access = AccessLevel.PRIVATE) // TODO: 30 C - Remove
class Customer { // TODO: 10 C - implements AggregateRoot<Customer, CustomerId> {

	private final @EmbeddedId CustomerId id; // TODO: 30 C - Remove annotation
	private Address address;

	Customer(Address address) {

		this.id = new CustomerId(UUID.randomUUID().toString());
		this.address = address;
	}

	// TODO: 40 C - Replace with record
	@Embeddable // TODO: 30 C - Remove
	@EqualsAndHashCode
	@RequiredArgsConstructor
	@NoArgsConstructor(force = true, access = AccessLevel.PRIVATE) // TODO: 30 C - Remove
	static class CustomerId implements Serializable { // TODO: 10 C - , Identifier { // TODO: 30 C - Remove Serializable

		private static final long serialVersionUID = 1733846413103581113L; // TODO: 30 C - Remove
		private final String customerId; // TODO: 40 C - Switch to UUID
	}
}
