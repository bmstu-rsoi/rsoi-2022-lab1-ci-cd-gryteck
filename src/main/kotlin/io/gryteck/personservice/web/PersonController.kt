package io.almishev.personservice.web

import io.almishev.personservice.dto.PatchPersonRequest
import io.almishev.personservice.dto.PersonRequest
import io.almishev.personservice.dto.PersonResponse
import io.almishev.personservice.service.PersonService
import kotlinx.coroutines.flow.Flow
import org.springframework.http.ResponseEntity
import org.springframework.http.server.reactive.ServerHttpRequest
import org.springframework.web.bind.annotation.*
import org.springframework.web.util.UriComponentsBuilder
import javax.validation.Valid

@RestController
@RequestMapping("/persons")
class PersonController(val personService: PersonService) {

    @GetMapping("/{id}")
    suspend fun getPerson(@PathVariable id: Int): ResponseEntity<PersonResponse> =
        ResponseEntity.ok().body(personService.getPerson(id))

    @GetMapping
    fun getAllPeople(): ResponseEntity<Flow<PersonResponse>> = ResponseEntity.ok().body(personService.getAllPeople())

    @PostMapping
    suspend fun createPerson(@Valid @RequestBody person: PersonRequest, request: ServerHttpRequest): ResponseEntity<Unit> {
        val createdPersonId = personService.createPerson(person)

        return ResponseEntity.created(
            UriComponentsBuilder.fromHttpRequest(request)
                .path("/{id}")
                .buildAndExpand(createdPersonId)
                .toUri()
        ).build()
    }

    @PatchMapping("/{id}")
    suspend fun updatePerson(
        @PathVariable id: Int,
        @Valid @RequestBody person: PatchPersonRequest
    ): ResponseEntity<PersonResponse> {
        val updatedPerson = personService.updatePerson(id, person)

        return ResponseEntity.ok()
            .body(updatedPerson)
    }

    @DeleteMapping("/{id}")
    suspend fun deletePerson(@PathVariable id: Int): ResponseEntity<Unit> {
        personService.deletePerson(id)
        return ResponseEntity.noContent().build()
    }
}