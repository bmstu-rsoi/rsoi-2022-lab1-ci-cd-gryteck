package io.gryteck.personservice.domain

import io.gryteck.personservice.dto.PersonRequest

object PersonRequestMother {
    fun default() = PersonRequestBuilder().apply {
        name = "Aidana"
        age = 19
        address = "Omsk, Russia"
        work = "MOPK"
    }
}

class PersonRequestBuilder : Builder<PersonRequest> {
    var name: String = ""
    var age: Int? = null
    var address: String? = null
    var work: String? = null

    override fun build() = PersonRequest(
        name = name,
        age = age,
        address = address,
        work = work
    )

}