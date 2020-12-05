package com.github.fgoncalves.analyser.domain.mappers

import com.github.fgoncalves.analyser.commons.forRun
import com.github.fgoncalves.analyser.commons.forSuite
import com.github.fgoncalves.analyser.commons.forTestCase
import com.github.fgoncalves.analyser.commons.fromUrn
import com.github.fgoncalves.analyser.data.db.entities.*
import com.github.fgoncalves.analyser.domain.models.Result
import com.github.fgoncalves.analyser.domain.models.Run
import com.github.fgoncalves.analyser.domain.models.Suite
import com.github.fgoncalves.analyser.domain.models.TestCase

internal fun RunEntity.toRun() =
  Run(
    forRun(id),
    suites = suites.map(SuiteEntity::toSuite)
  )

internal fun SuiteEntity.toSuite() =
  Suite(
    forSuite(id),
    name!!,
    testCases.map(TestCaseEntity::toTestCase)
  )

private fun ResultType.toResult(msg: String?) =
  when (this) {
    ResultType.PASS -> Result.Pass

    ResultType.FAILED -> Result.Failed(msg!!)
  }

private fun Result.toResultType() =
  when (this) {
    is Result.Pass -> ResultType.PASS

    is Result.Failed -> ResultType.FAILED
  }

private fun TestCaseEntity.toTestCase() =
  TestCase(
    forTestCase(id),
    name!!,
    className!!,
    flaky,
    weblink!!,
    result!!.toResult(errorMessage)
  )

private fun TestCase.toTestCaseEntity(suiteEntity: SuiteEntity?) =
  testCaseEntity {
    id = fromUrn(urn)
    name = this@toTestCaseEntity.name
    className = this@toTestCaseEntity.className
    flaky = this@toTestCaseEntity.flaky
    weblink = this@toTestCaseEntity.weblink
    result = this@toTestCaseEntity.result.toResultType()
    errorMessage = if (this@toTestCaseEntity.result is Result.Failed)
      this@toTestCaseEntity.result.message
    else
      null
    suite = suiteEntity
  }

internal fun Suite.toSuiteEntity(runEntity: RunEntity) =
  suiteEntity {
    id = fromUrn(urn)
    name = this@toSuiteEntity.name
    testCases = this@toSuiteEntity.testCases.map { it.toTestCaseEntity(this) }
    run = runEntity
  }

internal fun Run.toRunEntity() =
  runEntity {
    id = fromUrn(urn)
    suites = this@toRunEntity.suites.map { it.toSuiteEntity(this) }
  }