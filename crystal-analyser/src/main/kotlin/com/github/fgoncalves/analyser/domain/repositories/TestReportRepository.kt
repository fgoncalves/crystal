package com.github.fgoncalves.analyser.domain.repositories

import com.github.fgoncalves.analyser.data.services.FileService
import kotlinx.coroutines.flow.Flow
import java.io.File
import javax.inject.Singleton

internal interface TestReportRepository {
  fun store(filename: String, data: ByteArray): Flow<File>
}

@Singleton
internal class DefaultTestReportRepository(
  private val fileService: FileService,
) : TestReportRepository {
  override fun store(filename: String, data: ByteArray): Flow<File> =
    fileService.write(filename, data)
}