package io.gryteck.personservice.service

import io.gryteck.personservice.domain.*
import io.gryteck.personservice.exception.EntityNotFoundException
import io.gryteck.personservice.mapper.toPersonEntity
import io.gryteck.personservice.mapper.toPersonResponse
import io.gryteck.personservice.repository.PersonRepository
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.confirmVerified
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import io.mockk.spyk
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.junit.jupiter.api.function.Executable

@ExtendWith(MockKExtension::class)
internal class PersistentPersonServiceTest {

    @MockK(relaxed = true)
    private lateinit var _personRepository: PersonRepository
    private lateinit var _personService: PersonService

    @BeforeEach
    fun setup() {
        _personService = spyk(PersistentPersonService(_personRepository))
    }

    @Test
    fun `createPerson - calls PersonRepository_save and returns id returned from repository`() {
        // Arrange
        val request = PersonRequestMother.default().build()
        val givenId = 1
        coEvery { _personRepository.save(any()) } returns request.toPersonEntity(id = givenId)

        // Act
        val actualId = runBlocking {
            _personService.createPerson(request)
        }

        // Assert
        coVerify {
            _personRepository.save(any())
        }
        Assertions.assertEquals(givenId, actualId, "Id should be the same with id returned from repository")

        confirmVerified(_personRepository)
    }

    @Test
    fun `getAllPeople - calls PersonRepository_findAll and returns the same people returned from repository`() {
        // Arrange
        val givenPeople = PersonEntityMother.multiple().build()
        coEvery { _personRepository.findAll() } returns givenPeople.asFlow()
        val expected = givenPeople.map { it.toPersonResponse() }

        // Act
        val actual = runBlocking {
            _personService.getAllPeople().toList()
        }
        // Assert
        coVerify {
            _personRepository.findAll()
        }
        Assertions.assertEquals(expected, actual, "People should be the same with people returned from repository")

        confirmVerified(_personRepository)
    }

    @Test
    fun `getPerson - calls PersonRepository_findById and returns the same person returned from repository`() {
        // Arrange
        val givenPersonEntity = PersonEntityMother.aidana().build()
        coEvery { _personRepository.findById(givenPersonEntity.id) } returns givenPersonEntity
        val expected = givenPersonEntity.toPersonResponse()

        // Act
        val actual = runBlocking {
            _personService.getPerson(givenPersonEntity.id)
        }

        // Assert
        coVerify {
            _personRepository.findById(givenPersonEntity.id)
        }
        Assertions.assertEquals(expected, actual, "Person should be the same with person returned from repository")

        confirmVerified(_personRepository)
    }

    @Test
    fun `updatePerson - when patching name updates only name of the person`() {
        // Arrange
        val givenName = "TestName"
        val givenId = 1
        val givenRequest = create(PatchPersonRequestMother.empty()) {
            name = givenName
        }
        val savedPerson = create(PersonEntityMother.aidana()) {
            id = givenId
        }
        val expected = savedPerson.toPersonResponse().copy(name = givenName)
        coEvery { _personRepository.findById(givenId) } returns savedPerson
        coEvery { _personRepository.save(any()) } returnsArgument 0

        // Act
        val actual = runBlocking {
            _personService.updatePerson(givenId, givenRequest)
        }

        // Assert
        coVerify {
            _personRepository.findById(givenId)
            _personRepository.save(any())
        }
        Assertions.assertEquals(expected, actual, "Only name should be updated")

        confirmVerified(_personRepository)
    }

    @Test
    fun `updatePerson - when person id is not found throws exception`() {
        // Arrange
        val givenId = 1
        val givenRequest = create(PatchPersonRequestMother.empty())

        coEvery { _personRepository.findById(givenId) } returns null

        // Act
        val act = Executable {
            runBlocking {
                _personService.updatePerson(givenId, givenRequest)
            }
        }

        // Assert
        Assertions.assertThrows(EntityNotFoundException::class.java, act)
        coVerify { _personRepository.findById(any()) }

        confirmVerified(_personRepository)
    }

    @Test
    fun `deletePerson - calls personRepository_deleteById`() {
        // Arrange
        val givenId = 1

        // Act
        runBlocking {
            _personService.deletePerson(givenId)
        }
        // Assert
        coVerify {
            _personRepository.deleteById(givenId)
        }

        confirmVerified(_personRepository)
    }
}
