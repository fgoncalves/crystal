package com.github.fgoncalves.analyser.domain.repositories

import com.github.fgoncalves.analyser.commons.fromUrn
import com.github.fgoncalves.analyser.commons.persistOrMerge
import com.github.fgoncalves.analyser.commons.transactional
import com.github.fgoncalves.analyser.data.db.entities.RunEntity
import com.github.fgoncalves.analyser.domain.mappers.toRun
import com.github.fgoncalves.analyser.domain.mappers.toRunEntity
import com.github.fgoncalves.analyser.domain.models.Run
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Singleton
import javax.persistence.EntityManagerFactory

interface RunsRepository {
  fun createOrUpdate(run: Run): Flow<Boolean>

  fun get(urn: String): Flow<Run?>

  fun runs(): Flow<Run>

  fun delete(urn: String): Flow<Boolean>
}

@Singleton
class DefaultRunsRepository(
  private val entityManagerFactory: EntityManagerFactory,
) : RunsRepository {
  override fun createOrUpdate(run: Run): Flow<Boolean> =
    flow {
      entityManagerFactory.transactional {
        persistOrMerge(run.toRunEntity())
      }

      emit(true)
    }.flowOn(Dispatchers.IO)

  override fun runs(): Flow<Run> =
    flow {
      val em = entityManagerFactory.createEntityManager()
      val query = em.criteriaBuilder.createQuery(RunEntity::class.java)

      em.createQuery(query).resultList.forEach {
        emit(it.toRun())
      }
    }

  override fun get(urn: String): Flow<Run?> =
    flow {
      val entity = entityManagerFactory.createEntityManager().find(
        RunEntity::class.java,
        fromUrn(urn),
      )

      emit(entity.toRun())
    }

  override fun delete(urn: String): Flow<Boolean> =
    flow {
      entityManagerFactory.transactional {
        val entity = find(
          RunEntity::class.java,
          fromUrn(urn),
        )

        entity?.let { remove(it) }
      }
    }
}