/*

 * Copyright 2017-2022 the original author or authors.
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
package de.odrotbohm.examples.ddd.modulith.orders;

import de.odrotbohm.examples.ddd.modulith.orders.Order.OrderIdentifier;
import lombok.RequiredArgsConstructor;

import org.jmolecules.ddd.annotation.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author Oliver Drotbohm
 */
@Service
@Transactional
@RequiredArgsConstructor
class DefaultOrderManagement implements OrderManagement {

	private final OrderRepository orders;

	/*
	 * (non-Javadoc)
	 * @see de.odrotbohm.examples.ddd.moduliths.orders.OrderManagement#createOrder()
	 */
	@Override
	public Order createOrder() {
		return orders.save(new Order());
	}

	/*
	 * (non-Javadoc)
	 * @see de.odrotbohm.examples.ddd.moduliths.orders.OrderManagement#findOrder(de.odrotbohm.examples.ddd.moduliths.orders.Order.OrderIdentifier)
	 */
	@Override
	public Order findOrder(OrderIdentifier identifier) {
		return orders.findById(identifier).orElse(null);
	}

	/*
	 * (non-Javadoc)
	 * @see de.odrotbohm.examples.ddd.moduliths.orders.OrderManagement#complete(de.odrotbohm.examples.ddd.moduliths.orders.Order)
	 */
	@Override
	public void complete(Order order) {
		orders.save(order.complete());
	}
}
