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

import de.odrotbohm.examples.ddd.modulith.catalog.Product.ProductIdentifier;
import lombok.EqualsAndHashCode;
import lombok.Value;

/**
 * @author Oliver Drotbohm
 */
@Value
@SuppressWarnings("serial")
@EqualsAndHashCode(callSuper = false)
public class InsufficientStock extends RuntimeException {

	ProductIdentifier productIdentifier;
	long stock;

	InsufficientStock(ProductIdentifier productIdentifier, long stock) {

		super(String.format("Insufficient stock for %s! Current stock: %s!", productIdentifier, stock));

		this.productIdentifier = productIdentifier;
		this.stock = stock;
	}
}
