package com.github.fgoncalves.analyser.domain.schedulers

import com.github.fgoncalves.analyser.data.services.AnalyserParserService
import com.github.fgoncalves.analyser.domain.models.AnalyserTask
import com.github.fgoncalves.analyser.domain.models.AnalyserTaskStatus
import com.github.fgoncalves.analyser.domain.repositories.TaskRepository
import io.micronaut.scheduling.TaskScheduler
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flatMapMerge
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.runBlocking
import java.time.Duration
import javax.inject.Singleton

internal interface AnalyserTaskScheduler {
  fun schedule(task: AnalyserTask)
}

@Singleton
internal class DefaultAnalyserTaskScheduler(
  private val taskScheduler: TaskScheduler,
  private val taskRepository: TaskRepository,
  // Should probably be a repo
  private val analyserParserService: AnalyserParserService,
) : AnalyserTaskScheduler {

  @FlowPreview
  override fun schedule(task: AnalyserTask) {
    taskScheduler.schedule(Duration.ZERO) {
      val started = task.copy(
        status = AnalyserTaskStatus.Running,
      )

      runBlocking {
        coroutineScope {
          taskRepository.update(started)
            .flatMapMerge {
              analyserParserService.analyse(started.file.toURI())
            }
            .onEach {
              val finished = task.copy(
                status = AnalyserTaskStatus.Done,
              )
              taskRepository.update(finished).collect()
            }
            .onCompletion {
              if (it != null) {
                val errored = task.copy(
                  status = AnalyserTaskStatus.Error(it.stackTraceToString())
                )
                taskRepository.update(errored).collect()
              }
            }
            .collect { }
        }
      }
    }
  }
}