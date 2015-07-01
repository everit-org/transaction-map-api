/*
 * Copyright (C) 2011 Everit Kft. (http://www.everit.org)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.everit.transaction.map;

import java.util.Map;

/**
 * {@link Map} that allows transactional operations. The {@link Map} should replay all modifications
 * during commiting the transaction. In case the implementations wraps another Map, the following
 * rules should be applied on the replay mechanism:
 *
 * <ul>
 * <li>If {@link #clear()} was called within the transaction, the same method should be replayed in
 * the beginning of the commit</li>
 * <li>If {@link #clear()} was <strong>not</strong> called within the transaction,
 * {@link #remove(Object)} functions should be replayed for all keys even if that key was not in the
 * original {@link Map} when the {@link #remove(Object)} was called or a new entry was inserted with
 * the same key later. In other words, if a key was removed then reinserted, the TransactionalMap
 * implementation should replay the remove and the last put method on the wrapped Map. This can be
 * important for invalidation caches where calling the remove function generates a synchronization
 * between nodes in a cluster.</li>
 * </ul>
 *
 * @param <K>
 *          Type of keys.
 * @param <V>
 *          Type of values.
 */
public interface TransactionalMap<K, V> extends Map<K, V> {

  /**
   * Commits the currently associated transaction.
   *
   * @throws IllegalStateException
   *           if no transaction is associated currently to the {@link TransactionalMap}.
   */
  void commitTransaction();

  /**
   * Gives back the currently associated transaction.
   *
   * @return The currently associated transaction or <code>null</code> if no transaction is
   *         associated at the moment.
   */
  Object getAssociatedTransaction();

  /**
   * Resumes a previously suspended transaction.
   *
   * @param transaction
   *          The previously suspended transaction.
   * @throws IllegalStateException
   *           if the provided transaction is not in suspended state or there is another active
   *           associated transaction.
   */
  void resumeTransaction(Object transaction);

  /**
   * Calls a rollback on the currently associated transaction.
   *
   * @throws IllegalStateException
   *           if no transaction is associated currently to the {@link TransactionalMap}.
   */
  void rollbackTransaction();

  /**
   * Starts a new transaction on the {@link TransactionalMap}.
   *
   * @param transaction
   *          The transaction that will be associated to the {@link TransactionalMap}.
   * @throws IllegalStateException
   *           if the {@link TransactionalMap} is already associated to the provided transaction
   *           (either in suspended or active state) or other transaction is currently associated to
   *           the {@link TransactionalMap} in active state.
   */
  void startTransaction(Object transaction);

  /**
   * Suspends the currently associated transaction.
   *
   * @throws IllegalStateException
   *           if no transaction is associated currently to the {@link TransactionalMap}.
   */
  void suspendTransaction();

}
