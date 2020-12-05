package com.github.fgoncalves.analyser.domain.repositories

import com.github.fgoncalves.analyser.commons.CompletableFlow
import com.github.fgoncalves.analyser.commons.Urn
import com.github.fgoncalves.analyser.commons.complete
import com.github.fgoncalves.analyser.commons.forTask
import com.github.fgoncalves.analyser.domain.models.AnalyserTask
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.io.File
import java.util.*
import javax.inject.Singleton

internal interface TaskRepository {
  fun create(name: String, file: File): Flow<AnalyserTask>

  fun update(task: AnalyserTask): CompletableFlow

  fun get(urn: Urn): Flow<AnalyserTask>

  fun delete(urn: Urn): CompletableFlow
}

internal class TaskNotFoundException(urn: Urn) : RuntimeException("Cannot find task ${urn.raw}")

@Singleton
internal class DefaultTaskRepository : TaskRepository {
  // Let's keep this in memory for now
  private val tasks = mutableMapOf<Urn, AnalyserTask>()

  override fun create(name: String, file: File): Flow<AnalyserTask> =
    flow {
      val task = AnalyserTask(
        createUrn(),
        name,
        file,
      )

      tasks[task.urn] = task

      emit(task)
    }

  override fun get(urn: Urn): Flow<AnalyserTask> =
    flow {
      val task = tasks[urn] ?: throw TaskNotFoundException(urn)

      emit(task)
    }

  override fun delete(urn: Urn): CompletableFlow =
    flow {
      tasks.remove(urn)
    }

  override fun update(task: AnalyserTask): CompletableFlow =
    flow {
      tasks[task.urn] ?: throw TaskNotFoundException(task.urn)

      tasks[task.urn] = task

      complete()
    }

  private fun createUrn() = forTask(UUID.randomUUID().toString())
}