package com.github.fgoncalves.analyser.data.db.entities

import javax.persistence.*

internal enum class ResultType {
  PASS,
  FAILED,
}

@Entity
@Table(name = "test_case")
internal open class TestCaseEntity {
  @get:Id
  @get:Column(name = "id")
  @get:GeneratedValue(strategy = GenerationType.AUTO)
  open var id: Long? = null

  @get:Column(name = "name", nullable = false)
  open var name: String? = null

  @get:Column(name = "class_name", nullable = false)
  open var className: String? = null

  @get:Column(name = "flaky", nullable = false)
  open var flaky: Boolean = false

  @get:Column(name = "weblink", nullable = false)
  open var weblink: String? = null

  @get:Enumerated(EnumType.STRING)
  @get:Column(name = "result", nullable = false)
  open var result: ResultType? = null

  @get:Column(name = "error_message", nullable = true)
  open var errorMessage: String? = null

  @get:ManyToOne(fetch = FetchType.LAZY)
  @get:JoinColumn(name = "suite_id", referencedColumnName = "id", nullable = false)
  open var suite: SuiteEntity? = null
}

internal fun testCaseEntity(block: TestCaseEntity.() -> Unit) =
  TestCaseEntity().apply(block)