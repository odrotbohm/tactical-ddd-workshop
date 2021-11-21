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
package de.odrotbohm.examples.ddd.d.catalog;

import static org.assertj.core.api.Assertions.*;

import de.odrotbohm.examples.ddd.d.catalog.Product.ProductAdded;
import de.odrotbohm.examples.ddd.d.inventory.Inventory;
import lombok.RequiredArgsConstructor;

import java.math.BigDecimal;

import org.junit.jupiter.api.Test;
import org.moduliths.test.ModuleTest;
import org.moduliths.test.PublishedEvents;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.junit.jupiter.DisabledIf;

/**
 * @author Oliver Drotbohm
 */
@ModuleTest
@RequiredArgsConstructor
@DisabledIf(
		expression = "#{!T(de.odrotbohm.examples.ddd.support.TestUtils).isJpaEntity(T(de.odrotbohm.examples.ddd.d.catalog.Product))}",
		reason = "Product is not a JPA entity")
class CatalogIntegrationTests {

	private final Catalog catalog;
	private final ApplicationContext context;

	@Test
	void bootstrapsCatalogOnly() {

		assertThat(catalog).isNotNull();

		assertThatExceptionOfType(NoSuchBeanDefinitionException.class)
				.isThrownBy(() -> context.getBean(Inventory.class));
	}

	@Test
	void publishesEvendOnProductCreation(PublishedEvents events) {

		var product = new Product("Some product", BigDecimal.valueOf(29.99));

		catalog.save(product);

		assertThat(events.ofType(ProductAdded.class)
				.matchingMapped(ProductAdded::getProduct, it -> it.equals(product.getId())))
						.hasSize(1);
	}
}
