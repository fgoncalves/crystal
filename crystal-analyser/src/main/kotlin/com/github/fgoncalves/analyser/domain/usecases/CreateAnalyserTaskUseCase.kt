package com.github.fgoncalves.analyser.domain.usecases

import com.github.fgoncalves.analyser.domain.models.AnalyserTask
import com.github.fgoncalves.analyser.domain.repositories.TaskRepository
import com.github.fgoncalves.analyser.domain.repositories.TestReportRepository
import com.github.fgoncalves.analyser.domain.schedulers.AnalyserTaskScheduler
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flatMapMerge
import kotlinx.coroutines.flow.onEach
import java.util.*
import javax.inject.Singleton

internal interface CreateAnalyserTaskUseCase {
  fun create(name: String, bytes: ByteArray): Flow<AnalyserTask>
}

@Singleton
internal class DefaultCreateAnalyserTaskUseCase(
  private val testReportRepository: TestReportRepository,
  private val taskRepository: TaskRepository,
  private val analyserTaskScheduler: AnalyserTaskScheduler,
) : CreateAnalyserTaskUseCase {
  @FlowPreview
  override fun create(name: String, bytes: ByteArray): Flow<AnalyserTask> =
    testReportRepository.store(filename(name), bytes)
      .flatMapMerge {
        taskRepository.create(name, it)
      }
      .onEach {
        analyserTaskScheduler.schedule(it)
      }

  private fun filename(name: String) = "$name-${Date().time}.xml"
}