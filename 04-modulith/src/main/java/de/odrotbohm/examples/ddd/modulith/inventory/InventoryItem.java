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

import de.odrotbohm.examples.ddd.modulith.catalog.Product;
import de.odrotbohm.examples.ddd.modulith.catalog.Product.ProductIdentifier;
import de.odrotbohm.examples.ddd.modulith.inventory.InventoryItem.InventoryItemIdentifier;
import lombok.Getter;

import java.util.UUID;

import org.jmolecules.ddd.types.AggregateRoot;
import org.jmolecules.ddd.types.Association;
import org.jmolecules.ddd.types.Identifier;
import org.jmolecules.event.types.DomainEvent;
import org.springframework.data.domain.AbstractAggregateRoot;

/**
 * @author Oliver Drotbohm
 */
@Getter
class InventoryItem extends AbstractAggregateRoot<InventoryItem>
		implements AggregateRoot<InventoryItem, InventoryItemIdentifier> {

	private InventoryItemIdentifier id;
	private Association<Product, ProductIdentifier> product;
	private long amount;

	InventoryItem(ProductIdentifier productIdentifier, long amount) {

		this.id = new InventoryItemIdentifier(UUID.randomUUID());
		this.product = Association.forId(productIdentifier);
		this.amount = amount;
	}

	InventoryItem refillBy(long amount) {

		this.amount = this.amount + amount;

		return this;
	}

	InventoryItem reduceStockBy(long amount) {

		if (this.amount < amount) {
			registerEvent(new OutOfStock(product));
		}

		this.amount = this.amount - amount;

		return this;
	}

	public record InventoryItemIdentifier(UUID itemId) implements Identifier {}

	public record OutOfStock(Association<Product, ProductIdentifier> product) implements DomainEvent {}
}
