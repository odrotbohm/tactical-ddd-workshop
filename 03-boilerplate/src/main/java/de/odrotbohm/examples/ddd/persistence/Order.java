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

import de.odrotbohm.examples.ddd.persistence.Customer.CustomerId;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Embeddable;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * @author Oliver Drotbohm
 */
@Getter
@Entity // TODO: 30 O - Remove
@Table(name = "SAMPLE_ORDER")
@EqualsAndHashCode(of = "id") // TODO: 30 O - Remove
@NoArgsConstructor(force = true, access = AccessLevel.PRIVATE) // TODO: 30 O - Remove
class Order { // TODO: 10 O - implements AggregateRoot<Order, OrderId> {

	private final @EmbeddedId OrderId id; // TODO: 30 O - Remove annotation
	private final CustomerId customer; // TODO: 30 O - Replace with Association<Customer, CustomerId>

	@OneToMany(cascade = CascadeType.ALL, orphanRemoval = true) // TODO: 30 O - Remove annotation
	private final List<LineItem> lineItems;

	Order(CustomerId customerId) {

		this.id = new OrderId(UUID.randomUUID().toString());
		this.customer = customerId;
		this.lineItems = new ArrayList<>();
	}

	Order add(LineItem item) {

		this.lineItems.add(item);

		return this;
	}

	// TODO: 40 O - Replace with record
	@Embeddable // TODO: 30 O - Remove
	@EqualsAndHashCode
	@RequiredArgsConstructor
	@NoArgsConstructor(force = true) // TODO: 30 O - Remove
	static class OrderId implements Serializable { // TODO: 10 O - , Identifier { // TODO: 30 O - Remove Serializable

		private static final long serialVersionUID = 1009997590119941755L; // TODO: 30 O - Remove
		private final String orderId; // TODO: 40 O - Switch to UUID
	}

	@Entity // TODO: 30 O - Remove
	@Getter
	@NoArgsConstructor(force = true, access = AccessLevel.PRIVATE) // TODO: 30 O - Remove
	static class LineItem { // TODO: 10 O - implements org.jmolecules.ddd.types.Entity<Order, LineItemId> {

		private @EmbeddedId LineItemId id; // TODO: 30 O - Remove annotation
		private String description;
		private long amount;

		LineItem(String description, long amount) {

			this.id = new LineItemId(UUID.randomUUID().toString());
			this.description = description;
			this.amount = amount;
		}

		// TODO: 40 O - Replace with record
		@Embeddable // TODO: 30 O - Remove
		@EqualsAndHashCode
		@RequiredArgsConstructor
		@NoArgsConstructor(force = true, access = AccessLevel.PRIVATE) // TODO: 30 O - Remove
		static class LineItemId implements Serializable { // TODO: 10 O - , Identifier { // TODO: 30 O - Remove Serializable

			private static final long serialVersionUID = 1009997590119941755L; // TODO: 30 O - Remove
			private final String lineItemId; // TODO: 40 O - Switch to UUID
		}
	}
}
