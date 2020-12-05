package com.github.fgoncalves.analyser.data.db.entities

import javax.persistence.*

@Entity
@Table(name = "runs")
internal open class RunEntity {
  @get:Id
  @get:Column(name = "id")
  @get:GeneratedValue(strategy = GenerationType.AUTO)
  open var id: Long? = null

  @get:OneToMany(mappedBy = "run", cascade = [CascadeType.PERSIST])
  open var suites: List<SuiteEntity> = emptyList()
}

internal fun runEntity(block: RunEntity.() -> Unit) =
  RunEntity().apply(block)