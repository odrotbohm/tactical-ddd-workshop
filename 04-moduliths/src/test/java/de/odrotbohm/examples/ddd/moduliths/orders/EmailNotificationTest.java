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
import lombok.Value;

import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.modulith.events.EventPublicationRegistry;
import org.springframework.modulith.test.ApplicationModuleTest;

/**
 * @author Oliver Drotbohm
 */
@Value
@ApplicationModuleTest
class EmailNotificationTest {

	OrderManagement orders;
	EventPublicationRegistry registry;
	EmailSender emails;

	@BeforeEach
	void setUp() {
		emails.setFail(false);
	}

	@Test
	void completingAnOrderUpdatesInventory() throws Exception {

		var identifier = ProductIdentifier.of(UUID.randomUUID());
		var order = orders.createOrder()
				.add(identifier, 5);

		orders.complete(order);

		// Publication registration left
		var publications = registry.findIncompletePublications();

		assertThat(publications)
				.hasSize(1)
				.allMatch(it -> it.getEvent() instanceof OrderCompleted);

		Thread.sleep(1200);

		// Publication registration left
		assertThat(registry.findIncompletePublications()).isEmpty();
	}

	@Test
	void systemCrashDuringTransactionalListenerExecutionKeepsPublicationRegistration() throws Exception {

		emails.setFail(true);

		var order = orders.createOrder()
				.add(ProductIdentifier.of(UUID.randomUUID()), 5);

		orders.complete(order);

		Thread.sleep(1200);

		// Publication registration left
		var publications = registry.findIncompletePublications();

		assertThat(publications)
				.hasSize(1)
				.allMatch(it -> it.getEvent() instanceof OrderCompleted);
	}
}
