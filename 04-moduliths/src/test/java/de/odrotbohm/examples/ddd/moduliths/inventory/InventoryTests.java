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
package de.odrotbohm.examples.ddd.moduliths.inventory;

import static org.assertj.core.api.Assertions.*;

import de.odrotbohm.examples.ddd.moduliths.catalog.Product.ProductIdentifier;
import de.odrotbohm.examples.ddd.moduliths.inventory.InventoryItem.OutOfStock;
import de.odrotbohm.examples.ddd.moduliths.orders.OrderManagement;
import lombok.RequiredArgsConstructor;

import java.util.UUID;

import org.jmolecules.ddd.types.Association;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.modulith.test.ApplicationModuleTest;

/**
 * @author Oliver Drotbohm
 */
@ApplicationModuleTest
@RequiredArgsConstructor
class InventoryTests {

	private final Inventory inventory;
	private final InventoryListener listener;

	@MockBean OrderManagement orders;

	@Test
	void throwsInsufficientStockOnOutOfStock() {

		var productId = ProductIdentifier.of(UUID.randomUUID().toString());
		inventory.registerShipment(productId, 0);

		assertThatExceptionOfType(InsufficientStock.class)
				.isThrownBy(() -> listener.onOutOfStock(OutOfStock.of(Association.forId(productId))));
	}
}
