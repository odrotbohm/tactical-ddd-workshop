/*
 * Copyright 2017 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package de.odrotbohm.examples.ddd.moduliths.orders;

import de.odrotbohm.examples.ddd.moduliths.catalog.Product.ProductIdentifier;
import de.odrotbohm.examples.ddd.moduliths.orders.Order.OrderIdentifier;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Value;
import lombok.With;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.jmolecules.ddd.types.AggregateRoot;
import org.jmolecules.ddd.types.Identifier;
import org.jmolecules.event.types.DomainEvent;
import org.springframework.data.domain.AbstractAggregateRoot;

import com.fasterxml.jackson.annotation.JsonCreator;

/**
 * @author Oliver Gierke
 */
@Table(name = "ORDERS")
@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Order extends AbstractAggregateRoot<Order> implements AggregateRoot<Order, OrderIdentifier> {

	private OrderIdentifier id;
	private List<LineItem> lineItems;
	private @With Status status;

	Order() {

		this.id = new OrderIdentifier(UUID.randomUUID().toString());
		this.lineItems = new ArrayList<>();
		this.status = Status.SUBMITTED;
	}

	public Order add(ProductIdentifier identifier, long amount) {

		lineItems.stream()
				.filter(it -> it.belongsToProduct(identifier))
				.findFirst()
				.ifPresentOrElse(
						it -> it.increaseQuantityBy(amount),
						() -> lineItems.add(new LineItem(identifier, amount)));

		return this;
	}

	Order complete() {

		return withStatus(Status.COMPLETED) //
				.andEventsFrom(this) //
				.andEvent(OrderCompleted.of(id));
	}

	public enum Status {
		SUBMITTED, COMPLETED;
	}

	@Value(staticConstructor = "of")
	public static class OrderIdentifier implements Identifier {
		String orderId;
	}

	@Value
	@RequiredArgsConstructor(staticName = "of",
			access = AccessLevel.PRIVATE,
			onConstructor = @__(@JsonCreator))
	public static class OrderCompleted implements DomainEvent {
		OrderIdentifier orderIdentifier;
	}
}
