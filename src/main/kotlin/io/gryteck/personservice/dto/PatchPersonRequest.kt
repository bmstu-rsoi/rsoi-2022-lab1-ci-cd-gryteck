package io.gryteck.personservice.dto

import javax.validation.constraints.Max
import javax.validation.constraints.Min
import javax.validation.constraints.NotBlank

data class PatchPersonRequest(
    @field:NotBlank(message = "Name cannot be blank")
    val name: String?,
    @field:Min(0, message = "Age cannot be less than 0")
    @field:Max(110, message = "Age cannot be more than 110")
    val age: Int? = null,
    val address: String? = null,
    val work: String? = null
)
