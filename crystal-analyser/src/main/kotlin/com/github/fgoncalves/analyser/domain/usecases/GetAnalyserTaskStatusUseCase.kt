package com.github.fgoncalves.analyser.domain.usecases

import com.github.fgoncalves.analyser.commons.Urn
import com.github.fgoncalves.analyser.domain.models.AnalyserTaskStatus
import com.github.fgoncalves.analyser.domain.repositories.TaskRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Singleton

internal interface GetAnalyserTaskStatusUseCase {
  fun get(urn: Urn): Flow<AnalyserTaskStatus>
}

@Singleton
internal class DefaultGetAnalyserTaskStatusUseCase(
  private val taskRepository: TaskRepository,
) : GetAnalyserTaskStatusUseCase {
  override fun get(urn: Urn): Flow<AnalyserTaskStatus> =
    taskRepository.get(urn)
      .map { it.status }
}