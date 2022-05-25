/*
 * Copyright 2017-2021 the original author or authors.
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

import static org.assertj.core.api.Assertions.*;

import de.odrotbohm.examples.ddd.moduliths.catalog.Product.ProductIdentifier;
import de.odrotbohm.examples.ddd.moduliths.orders.Order.OrderCompleted;
import lombok.RequiredArgsConstructor;

import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.moduliths.test.ModuleTest;
import org.moduliths.test.PublishedEvents;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author Oliver Drotbohm
 */
@ModuleTest
@Transactional
@RequiredArgsConstructor
class OrderManagementIntegrationTests {

	private final OrderManagement orders;

	@Test
	void completingAnOrderPublishesEvent(PublishedEvents events) {

		var order = orders.createOrder()
				.add(ProductIdentifier.of(UUID.randomUUID().toString()), 5);

		orders.complete(order);

		assertThat(events.ofType(OrderCompleted.class)
				.matchingMapped(OrderCompleted::getOrder, order::equals));
	}
}
