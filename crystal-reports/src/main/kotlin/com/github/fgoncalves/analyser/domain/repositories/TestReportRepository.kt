package com.github.fgoncalves.analyser.domain.repositories

import com.github.fgoncalves.analyser.commons.CompletableFlow
import com.github.fgoncalves.analyser.data.services.FileService
import javax.inject.Inject
import javax.inject.Singleton

internal interface TestReportRepository {
  fun store(filename: String, data: ByteArray): CompletableFlow
}

@Singleton
internal class DefaultTestReportRepository
@Inject
constructor(
  private val fileService: FileService,
): TestReportRepository {
  override fun store(filename: String, data: ByteArray): CompletableFlow =
    fileService.write(filename, data)
}