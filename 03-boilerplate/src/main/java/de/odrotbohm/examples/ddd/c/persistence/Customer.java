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
package de.odrotbohm.examples.ddd.c.persistence;

import de.odrotbohm.examples.ddd.c.persistence.Customer.CustomerId;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import java.io.Serializable;
import java.util.UUID;

import javax.persistence.Embeddable;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;

import org.jmolecules.ddd.types.AggregateRoot;
import org.jmolecules.ddd.types.Identifier;

/**
 * @author Oliver Drotbohm
 */
@Getter
@Entity // remove
@NoArgsConstructor(force = true, access = AccessLevel.PRIVATE) // remove
class Customer implements AggregateRoot<Customer, CustomerId> {

	private final @EmbeddedId CustomerId id;
	private Address address;

	Customer(Address address) {

		this.id = CustomerId.of(UUID.randomUUID().toString());
		this.address = address;
	}

	@Embeddable // remove
	@EqualsAndHashCode
	@RequiredArgsConstructor(staticName = "of")
	@NoArgsConstructor(force = true, access = AccessLevel.PRIVATE) // remove
	static class CustomerId implements Serializable, Identifier {

		private static final long serialVersionUID = 1733846413103581113L;
		private final String customerId;
	}
}
