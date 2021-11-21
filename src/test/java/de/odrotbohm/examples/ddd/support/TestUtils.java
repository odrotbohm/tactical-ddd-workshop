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
package de.odrotbohm.examples.ddd.support;

import javax.persistence.Entity;

import org.jmolecules.ddd.types.AggregateRoot;
import org.jmolecules.ddd.types.ValueObject;
import org.springframework.core.annotation.AnnotatedElementUtils;
import org.springframework.util.ClassUtils;

/**
 * @author Oliver Drotbohm
 */
public class TestUtils {

	public static boolean isValueObject(Class<?> type) {

		return ValueObject.class.isInstance(type)
				|| AnnotatedElementUtils.hasAnnotation(type, org.jmolecules.ddd.annotation.ValueObject.class);
	}

	public static boolean isAggregateRoot(Class<?> type) {

		return AggregateRoot.class.isAssignableFrom(type)
				|| AnnotatedElementUtils.hasAnnotation(type, org.jmolecules.ddd.annotation.AggregateRoot.class);
	}

	public static boolean isJpaAvailable() {
		return ClassUtils.isPresent("javax.persistence.EntityManager", null);
	}

	public static boolean isJpaEntity(Class<?> type) {
		return AnnotatedElementUtils.hasAnnotation(type, Entity.class);
	}
}
