package com.assignment.rnt.util

import com.assignment.rnt.ui.viewmodel.ComparisonResult
import org.junit.Assert
import org.junit.Test


class InputUtilsTest {

    @Test
    fun inputUtils_validateNumberInput() {
        var result = InputUtils.validateNumberInput("1")
        Assert.assertEquals(result, true)

        result = InputUtils.validateNumberInput("-1")
        Assert.assertEquals(result, true)

        result = InputUtils.validateNumberInput("9999")
        Assert.assertEquals(result, true)

        result = InputUtils.validateNumberInput("-9999")
        Assert.assertEquals(result, true)

        result = InputUtils.validateNumberInput(" 1")
        Assert.assertEquals(result, false)

        result = InputUtils.validateNumberInput("1.5")
        Assert.assertEquals(result, false)

        result = InputUtils.validateNumberInput("text")
        Assert.assertEquals(result, false)

        result = InputUtils.validateNumberInput("123text")
        Assert.assertEquals(result, false)

        result = InputUtils.validateNumberInput("-1.1")
        Assert.assertEquals(result, false)

        result = InputUtils.validateNumberInput("-1-")
        Assert.assertEquals(result, false)

        result = InputUtils.validateNumberInput("-1-1")
        Assert.assertEquals(result, false)
    }

    @Test
    fun inputUtils_compareNumbers() {
        var result = InputUtils.compareNumbers(1, 2)
        Assert.assertEquals(result, ComparisonResult.LOWER)

        result = InputUtils.compareNumbers(1, 999)
        Assert.assertEquals(result, ComparisonResult.LOWER)

        result = InputUtils.compareNumbers(0, 999)
        Assert.assertEquals(result, ComparisonResult.LOWER)

        result = InputUtils.compareNumbers(-10, 999)
        Assert.assertEquals(result, ComparisonResult.LOWER)

        result = InputUtils.compareNumbers(-2, -2)
        Assert.assertEquals(result, ComparisonResult.EQUALS)

        result = InputUtils.compareNumbers(0, 0)
        Assert.assertEquals(result, ComparisonResult.EQUALS)

        result = InputUtils.compareNumbers(22, 22)
        Assert.assertEquals(result, ComparisonResult.EQUALS)

        result = InputUtils.compareNumbers(100, -2)
        Assert.assertEquals(result, ComparisonResult.HIGHER)

        result = InputUtils.compareNumbers(100, 0)
        Assert.assertEquals(result, ComparisonResult.HIGHER)

        result = InputUtils.compareNumbers(100, 22)
        Assert.assertEquals(result, ComparisonResult.HIGHER)
    }
}