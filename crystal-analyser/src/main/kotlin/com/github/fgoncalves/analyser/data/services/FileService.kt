package com.github.fgoncalves.analyser.data.services

import io.micronaut.context.annotation.Value
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import java.io.File
import javax.inject.Singleton

internal interface FileService {
  /**
   * write to file the given data
   */
  fun write(filename: String, data: ByteArray): Flow<File>
}

@Singleton
internal class DefaultFileService : FileService {
  @Value("\${reports.test.path}:.")
  var reportsPath: String = "."
    private set

  override fun write(filename: String, data: ByteArray): Flow<File> =
    flow {
      ensureReportsPathExists()

      val file = File(reportsPath, filename)
      file.bufferedWriter().use { out ->
        // All literature online seems to suggest this is safe to ignore because of the flow
        // being on IO. Let's worry about it when everything comes crashing down.
        out.write(String(data))
      }

      emit(file)
    }.flowOn(Dispatchers.IO)

  private fun ensureReportsPathExists() {
    val folder = File(reportsPath)
    if (!folder.mkdirs())
      throw ReportPathCreationException()
  }

  inner class ReportPathCreationException : RuntimeException("Failed to create folder for reports: $reportsPath")
}