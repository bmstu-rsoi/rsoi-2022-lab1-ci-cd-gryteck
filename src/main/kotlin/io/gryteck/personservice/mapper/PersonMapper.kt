package io.gryteck.personservice.mapper

import io.gryteck.personservice.domain.PersonEntity
import io.gryteck.personservice.dto.PatchPersonRequest
import io.gryteck.personservice.dto.PersonRequest
import io.gryteck.personservice.dto.PersonResponse

fun PersonEntity.toPersonResponse() = PersonResponse(
    id = id,
    name = name,
    age = age,
    address = address,
    work = work
)

fun PersonRequest.toPersonEntity(id: Int = 0) = PersonEntity(
    id = id,
    name = name,
    age = age,
    address = address,
    work = work
)

fun PatchPersonRequest.toPersonEntity(id: Int = 0, prevState: PersonEntity) = PersonEntity(
    id = id,
    name = name ?: prevState.name,
    age = age ?: prevState.age,
    address = address ?: prevState.address,
    work = work ?: prevState.work
)
