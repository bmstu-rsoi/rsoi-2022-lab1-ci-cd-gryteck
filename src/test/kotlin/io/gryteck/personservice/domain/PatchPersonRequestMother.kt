package io.gryteck.personservice.domain

import io.gryteck.personservice.dto.PatchPersonRequest

object PatchPersonRequestMother {
    fun empty() = PatchPersonRequestBuilder()
}

class PatchPersonRequestBuilder: Builder<PatchPersonRequest> {
    var name: String = ""
    var age: Int? = null
    var address: String? = null
    var work: String? = null

    override fun build() = PatchPersonRequest(
        name = name,
        age = age,
        address = address,
        work = work
    )
}
