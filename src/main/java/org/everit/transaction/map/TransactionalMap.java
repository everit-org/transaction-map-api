package org.everit.transaction.map;

import java.util.Map;

/**
 * {@link Map} that allows transactional operations.
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
   * In case this method returns <code>true</code>, remove will be replayed for all removed keys,
   * even for the ones that were re-inserted afterwards. Remove does not have to be called for keys
   * if the Map was cleared within the transaction. Calling remove-put has normally a performance
   * drawback but it is necessary in special cases (e.g.: for true-invalidation cache
   * implementations).
   *
   * @return Whether all remove operation that was called within a transaction are replayed during
   *         commiting the transaction.
   */
  boolean isAllRemoveOperationReplayedOnCommit();

  /**
   * Resumes a previously suspended transaction.
   *
   * @param transaction
   *          The previously suspended transaction.
   * @throws IllegalStateException
   *           if the provided transaction is not in suspended state.
   */
  void resumeTransaction(Object transaction);

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
  void startTransaction(Object transaction);

  /**
   * Suspends the currently associated transaction.
   *
   * @throws if
   *           no transaction is associated currently to the {@link TransactionalMap}.
   */
  void suspendTransaction();

}
