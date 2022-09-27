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
package de.odrotbohm.examples.ddd.persistence;

import static org.assertj.core.api.Assertions.*;

import de.odrotbohm.examples.ddd.persistence.Address.ZipCode;
import de.odrotbohm.examples.ddd.persistence.Order.LineItem;
import lombok.RequiredArgsConstructor;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * @author Oliver Drotbohm
 */
@SpringBootTest
@RequiredArgsConstructor
class PersistentModelTests {

	@Autowired CustomerRepository customers;
	@Autowired OrderRepository orders;

	@Test
	void persistsCustomerAndOrder() {

		var address = new Address("41 Grey street", ZipCode.of("01234"), "New York");
		var customer = customers.save(new Customer(address));

		var order = orders.save(new Order(customer.getId()).add(new LineItem("MacBook Pro", 10)));

		assertThat(orders.findByAmountExceeding(5L)).containsExactly(order);
	}
}
