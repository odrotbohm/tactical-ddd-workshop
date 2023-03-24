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

import static org.mockito.Mockito.*;

import de.odrotbohm.examples.ddd.moduliths.catalog.Product.ProductAdded;
import de.odrotbohm.examples.ddd.moduliths.catalog.Product.ProductIdentifier;

import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

/**
 * A unit test for {@link InventoryListener} to make sure it triggers the right functionality on event consumption.
 *
 * @author Oliver Drotbohm
 */
@ExtendWith(MockitoExtension.class)
class InventoryListenerUnitTests {

	@InjectMocks InventoryListener listener;
	@Mock Inventory inventory;

	@Test
	void registersShipmentForNewProduct() {

		// Given
		var id = new ProductIdentifier(UUID.randomUUID());
		var event = new ProductAdded(id);

		when(inventory.hasItemFor(id)).thenReturn(false);

		// When
		listener.onProductAdded(event);

		// Then
		verify(inventory).registerShipment(id, 0);
	}
}
