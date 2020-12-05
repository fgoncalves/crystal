package com.github.fgoncalves.analyser.domain.models

import com.github.fgoncalves.analyser.commons.Urn
import java.io.File

sealed class AnalyserTaskStatus {
  object Pending : AnalyserTaskStatus()
  object Running : AnalyserTaskStatus()
  object Done : AnalyserTaskStatus()
  data class Error(
    val errorMsg: String,
  ) : AnalyserTaskStatus()
}

data class AnalyserTask(
  val urn: Urn,
  val name: String,
  val file: File,
  val status: AnalyserTaskStatus = AnalyserTaskStatus.Pending,
)