package com.assignment.rnt.util

/**
 * Utils for validation of user input.
 */
object InputUtils {

    fun validateNumberInput(stringToValidate: String): Boolean {
        val validatedNumber = stringToValidate.toIntOrNull()
        return validatedNumber?.let { true } ?: false
    }
}