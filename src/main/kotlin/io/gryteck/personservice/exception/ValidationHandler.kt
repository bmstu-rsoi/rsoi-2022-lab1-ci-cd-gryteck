package io.gryteck.personservice.exception

import io.gryteck.personservice.dto.ErrorResponse
import io.gryteck.personservice.dto.ValidationErrorResponse
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestControllerAdvice
import org.springframework.web.bind.support.WebExchangeBindException


@RestControllerAdvice
class ValidationHandler {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(WebExchangeBindException::class)
    fun handleException(e: WebExchangeBindException): ValidationErrorResponse {
        val errors = e.bindingResult.fieldErrors.associateBy(
            {
                it.field
            }, {
                ValidationErrorResponse.FieldMessage(
                    rejectedValue = it.rejectedValue,
                    defaultMessage = it.defaultMessage
                )
            }
        )

        return ValidationErrorResponse("Binding failure", errors)
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(EntityNotFoundException::class)
    fun handleEntityNotFoundException(e: EntityNotFoundException) = ErrorResponse(e.message)

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(RuntimeException::class)
    fun handleRuntimeException(e: RuntimeException) = ErrorResponse(e.message)


}