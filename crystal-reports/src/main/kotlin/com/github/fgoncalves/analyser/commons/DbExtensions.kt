package com.github.fgoncalves.analyser.commons

import javax.persistence.EntityExistsException
import javax.persistence.EntityManager
import javax.persistence.EntityManagerFactory

fun EntityManagerFactory.transactional(block: EntityManager.() -> Unit) {
  val entityManager = createEntityManager()
  entityManager.transaction.begin()
  try {
    entityManager.block()
    entityManager.transaction.commit()
  } catch (t: Throwable) {
    entityManager.transaction.rollback()
    throw t
  }
}

fun <V> EntityManager.persistOrMerge(entity: V) {
  try {
    persist(entity)
  } catch (e: EntityExistsException) {
    merge(entity)
  }
}