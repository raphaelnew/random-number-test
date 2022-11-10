package com.assignment.rnt.util

import com.assignment.rnt.ui.viewmodel.ComparisonResult

/**
 * Utils for validation of user input.
 */
object InputUtils {

    fun validateNumberInput(stringToValidate: String): Boolean {
        val validatedNumber = stringToValidate.toIntOrNull()
        return validatedNumber?.let { true } ?: false
    }

    fun compareNumbers(number1: Int, number2: Int): ComparisonResult {
        return if (number1 == number2) {
            ComparisonResult.EQUALS
        } else if (number1 > number2) {
            ComparisonResult.HIGHER
        } else {
            ComparisonResult.LOWER
        }
    }
}