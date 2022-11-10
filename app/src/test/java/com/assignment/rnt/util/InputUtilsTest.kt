package com.assignment.rnt.util

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
}