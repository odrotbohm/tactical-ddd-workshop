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
package de.odrotbohm.examples.ddd.modulith.inventory;

import de.odrotbohm.examples.ddd.modulith.catalog.Product.ProductAdded;
import de.odrotbohm.examples.ddd.modulith.inventory.InventoryItem.OutOfStock;
import de.odrotbohm.examples.ddd.modulith.orders.OrderManagement;
import de.odrotbohm.examples.ddd.modulith.orders.Order.OrderCompleted;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.jmolecules.event.annotation.DomainEventHandler;
import org.springframework.modulith.events.ApplicationModuleListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author Oliver Drotbohm
 */
@Slf4j
@Transactional
@Component
@RequiredArgsConstructor
class InventoryListener {

	private final Inventory inventory;
	private final OrderManagement orders;

	/**
	 * Initializes the Inventory with a stock of zero for the Product just added.
	 *
	 * @param event
	 */
	@ApplicationModuleListener
	void onProductAdded(ProductAdded event) {

		var identifier = event.product();

		if (!inventory.hasItemFor(identifier)) {

			inventory.registerShipment(identifier, 0);

			log.info("Created inventory item for product {} and zero stock!", identifier);
		}
	}

	/**
	 * Updates the inventory based on the products referred to by the line items in the Order that was just completed.
	 *
	 * @param event
	 */
	@ApplicationModuleListener
	void onOrderCompleted(OrderCompleted event) {

		log.info("Received completed order {}. Triggering stock update for line items.", event.orderIdentifier());

		inventory.updateStockFor(orders.findOrder(event.orderIdentifier()));
	}

	@DomainEventHandler
	void onOutOfStock(OutOfStock event) {

		var product = event.product();
		var id = product.getId();
		var stock = inventory.getStockFor(id);

		log.info("Product {} out of stock! Current overdraw: {}.", product, stock);

		throw new InsufficientStock(id, stock);
	}
}
