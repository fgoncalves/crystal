package com.github.fgoncalves.analyser.data.db.entities

import javax.persistence.*

@Entity
@Table(name = "suite")
internal open class SuiteEntity {
  @get:Id
  @get:Column(name = "id")
  @get:GeneratedValue(strategy = GenerationType.AUTO)
  open var id: Long? = null

  @get:Column(name = "name", nullable = false)
  open var name: String? = null

  @get:OneToMany(mappedBy = "suite", cascade = [CascadeType.PERSIST])
  var testCases: List<TestCaseEntity> = emptyList()

  @get:ManyToOne(fetch = FetchType.LAZY)
  @get:JoinColumn(name = "run_id", referencedColumnName = "id", nullable = false)
  open var run: RunEntity? = null
}

internal fun suiteEntity(block: SuiteEntity.() -> Unit) =
  SuiteEntity().apply(block)