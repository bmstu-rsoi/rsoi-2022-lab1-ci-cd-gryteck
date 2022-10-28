package io.gryteck.personservice.domain

import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Table

@Table("person")
data class PersonEntity(
    @field:Id
    val id: Int = 0,
    val name: String,
    val age: Int?,
    val address: String?,
    val work: String?
)