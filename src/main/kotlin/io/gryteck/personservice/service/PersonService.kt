package io.gryteck.personservice.service

import io.gryteck.personservice.dto.PatchPersonRequest
import io.gryteck.personservice.dto.PersonRequest
import io.gryteck.personservice.dto.PersonResponse
import kotlinx.coroutines.flow.Flow

interface PersonService {

    // region Create
    suspend fun createPerson(person: PersonRequest): Int
    // endregion

    // region Read
    fun getAllPeople(): Flow<PersonResponse>

    suspend fun getPerson(id: Int): PersonResponse
    // endregion

    // region Update
    suspend fun updatePerson(id: Int, person: PatchPersonRequest): PersonResponse
    // endregion

    // region Delete
    suspend fun deletePerson(id: Int)
    // endregion

}