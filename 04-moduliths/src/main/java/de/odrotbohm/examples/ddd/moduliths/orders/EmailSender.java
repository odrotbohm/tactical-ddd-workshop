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

import de.odrotbohm.examples.ddd.moduliths.orders.Order.OrderCompleted;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import org.springframework.modulith.events.ApplicationModuleListener;
import org.springframework.stereotype.Component;

/**
 * @author Oliver Drotbohm
 */
@Slf4j
@Component
class EmailSender {

	private @Setter boolean fail = false;

	/**
	 * Sends out an email to the customer who placed the order on their completion.
	 *
	 * @param event
	 */
	@ApplicationModuleListener
	void on(OrderCompleted event) {

		log.info("Sending email for order {}.", event.orderIdentifier());

		try {
			Thread.sleep(1000);
		} catch (InterruptedException o_O) {}

		if (fail) {
			log.info("Failing to sent email for order {}.", event.orderIdentifier());
			throw new RuntimeException("(╯°□°）╯︵ ┻━┻");
		}

		log.info("Successfully sent email for order {}.", event.orderIdentifier());
	}
}
