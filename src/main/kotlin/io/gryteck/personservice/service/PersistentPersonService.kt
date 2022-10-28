package io.gryteck.personservice.service

import io.gryteck.personservice.dto.PatchPersonRequest
import io.gryteck.personservice.dto.PersonRequest
import io.gryteck.personservice.dto.PersonResponse
import io.gryteck.personservice.exception.EntityNotFoundException
import io.gryteck.personservice.mapper.toPersonEntity
import io.gryteck.personservice.mapper.toPersonResponse
import io.gryteck.personservice.repository.PersonRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class PersistentPersonService(private val personRepository: PersonRepository) : PersonService {

    // region Create
    @Transactional
    override suspend fun createPerson(person: PersonRequest): Int {
        return personRepository.save(person.toPersonEntity()).id
    }
    // endregion

    // region Read
    @Transactional(readOnly = true)
    override fun getAllPeople(): Flow<PersonResponse> = personRepository.findAll().map { it.toPersonResponse() }

    @Transactional(readOnly = true)
    override suspend fun getPerson(id: Int): PersonResponse = personRepository.findById(id)
        ?.toPersonResponse()
        ?: throw EntityNotFoundException("Person with id $id not found")
    // endregion

    // region Update
    @Transactional
    override suspend fun updatePerson(id: Int, person: PatchPersonRequest): PersonResponse {
        val old = personRepository.findById(id) ?: throw EntityNotFoundException("Person with id $id not found")
        return personRepository.save(person.toPersonEntity(id, old)).toPersonResponse()
    }
    // endregion

    // region Delete
    @Transactional
    override suspend fun deletePerson(id: Int): Unit = personRepository.deleteById(id)
    // endregion

}
