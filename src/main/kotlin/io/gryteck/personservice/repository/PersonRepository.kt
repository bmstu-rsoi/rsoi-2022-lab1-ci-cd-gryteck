package io.gryteck.personservice.repository

import io.gryteck.personservice.domain.PersonEntity
import org.springframework.data.repository.kotlin.CoroutineCrudRepository
import org.springframework.stereotype.Repository

@Repository
interface PersonRepository : CoroutineCrudRepository<PersonEntity, Int>
