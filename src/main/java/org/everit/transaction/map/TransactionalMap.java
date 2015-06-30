package org.everit.transaction.map;

import java.util.Map;

/**
 * {@link Map} that allows transactional operations.
 *
 * @param <K>
 *          Type of keys.
 * @param <V>
 *          Type of values.
 * @param <T>
 *          type of the objects that identify transactions.
 */
public interface TransactionalMap<K, V, T> extends Map<K, V> {

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
  T getAssociatedTransaction();

  /**
   * Resumes a previously suspended transaction.
   *
   * @param transaction
   *          The previously suspended transaction.
   * @throws IllegalStateException
   *           if the provided transaction is not in suspended state.
   */
  void resumeTransaction(T transaction);

  /**
   * Calls a rollback on the currently associated transaction.
   *
   * @throws if
   *           no transaction is associated currently to the {@link TransactionalMap}.
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
  void startTransaction(T transaction);

  /**
   * Suspends the currently associated transaction.
   * 
   * @throws if
   *           no transaction is associated currently to the {@link TransactionalMap}.
   */
  void suspendTransaction();

}
