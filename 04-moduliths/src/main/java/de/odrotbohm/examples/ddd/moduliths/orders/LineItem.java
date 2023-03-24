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

import de.odrotbohm.examples.ddd.moduliths.catalog.Product;
import de.odrotbohm.examples.ddd.moduliths.catalog.Product.ProductIdentifier;
import de.odrotbohm.examples.ddd.moduliths.orders.LineItem.LineItemIdentifier;
import lombok.Getter;

import java.util.UUID;

import org.jmolecules.ddd.types.Association;
import org.jmolecules.ddd.types.Entity;
import org.jmolecules.ddd.types.Identifier;
import org.springframework.lang.Nullable;

/**
 * @author Oliver Drotbohm
 */
@Getter
public class LineItem implements Entity<Order, LineItemIdentifier> {

	private final LineItemIdentifier id;
	private Association<Product, ProductIdentifier> product;
	private @Nullable String description;
	private long amount;

	LineItem(ProductIdentifier productIdentifier, long amount) {

		this.id = new LineItemIdentifier(UUID.randomUUID().toString());
		this.product = Association.forId(productIdentifier);
		this.amount = amount;
	}

	public boolean belongsToProduct(ProductIdentifier identifier) {
		return this.product.pointsTo(identifier);
	}

	public LineItem increaseQuantityBy(long amount) {

		this.amount = this.amount + amount;

		return this;
	}

	public record LineItemIdentifier(String lineItemId) implements Identifier {}
}
