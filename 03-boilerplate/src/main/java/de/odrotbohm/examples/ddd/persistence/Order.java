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
import de.odrotbohm.examples.ddd.persistence.Order.OrderId;
import de.odrotbohm.examples.ddd.persistence.Order.LineItem.LineItemId;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.persistence.CascadeType;
import javax.persistence.Embeddable;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.jmolecules.ddd.types.AggregateRoot;
import org.jmolecules.ddd.types.Identifier;

/**
 * @author Oliver Drotbohm
 */
@Getter
@Entity // remove
@Table(name = "SAMPLE_ORDER")
@EqualsAndHashCode(of = "id") // remove
@NoArgsConstructor(force = true, access = AccessLevel.PRIVATE) // remove
class Order implements AggregateRoot<Order, OrderId> {

	private final @EmbeddedId /* remove */ OrderId id;
	private final CustomerId customerId;

	@OneToMany(cascade = CascadeType.ALL) //
	private final List<LineItem> lineItems;

	Order(CustomerId customerId) {

		this.id = OrderId.of(UUID.randomUUID().toString());
		this.customerId = customerId;
		this.lineItems = new ArrayList<>();
	}

	Order add(LineItem item) {

		this.lineItems.add(item);

		return this;
	}

	@Embeddable // remove
	@EqualsAndHashCode
	@RequiredArgsConstructor(staticName = "of")
	@NoArgsConstructor(force = true) // remove
	static class OrderId implements Serializable, Identifier {

		private static final long serialVersionUID = 1009997590119941755L;
		private final String orderId;
	}

	@Entity // remove
	@Getter
	@NoArgsConstructor(force = true, access = AccessLevel.PRIVATE) // remove
	static class LineItem implements org.jmolecules.ddd.types.Entity<Order, LineItemId> {

		private @EmbeddedId /* remove */ LineItemId id;
		private String description;
		private long amount;

		LineItem(String description, long amount) {

			this.id = LineItemId.of(UUID.randomUUID().toString());
			this.description = description;
			this.amount = amount;
		}

		@Embeddable // remove
		@EqualsAndHashCode
		@RequiredArgsConstructor(staticName = "of")
		@NoArgsConstructor(force = true, access = AccessLevel.PRIVATE) // remove
		static class LineItemId implements Serializable, Identifier {

			private static final long serialVersionUID = 1009997590119941755L;
			private final String lineItemId;
		}
	}
}
