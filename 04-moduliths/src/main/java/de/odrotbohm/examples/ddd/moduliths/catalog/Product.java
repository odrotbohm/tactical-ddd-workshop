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
package de.odrotbohm.examples.ddd.moduliths.catalog;

import de.odrotbohm.examples.ddd.moduliths.catalog.Product.ProductIdentifier;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Value;

import java.math.BigDecimal;
import java.util.UUID;

import org.jmolecules.ddd.types.AggregateRoot;
import org.jmolecules.ddd.types.Identifier;
import org.jmolecules.event.types.DomainEvent;
import org.springframework.data.domain.AbstractAggregateRoot;

/**
 * @author Oliver Gierke
 */
@Getter
public class Product extends AbstractAggregateRoot<Product> implements AggregateRoot<Product, ProductIdentifier> {

	private final ProductIdentifier id;
	private String name;
	private BigDecimal price;

	public Product(String name, BigDecimal price) {

		this.id = new ProductIdentifier(UUID.randomUUID().toString());
		this.name = name;
		this.price = price;

		registerEvent(ProductAdded.of(id));
	}

	@Value
	public static class ProductIdentifier implements Identifier {
		String id;
	}

	@Value
	@RequiredArgsConstructor(staticName = "of")
	@NoArgsConstructor(access = AccessLevel.PRIVATE, force = true)
	public static class ProductAdded implements DomainEvent {
		ProductIdentifier product;
	}
}
