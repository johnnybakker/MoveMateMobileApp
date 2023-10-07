package nl.johnny.movemate.utils

import org.junit.Assert
import org.junit.Test

class StrongPasswordValidatorTest {

    @Test
    fun `Test password contains one uppercase character` () {

        Assert.assertTrue(
            StrongPasswordValidator
                .validate("Password")
                .containsOneUpperCaseCharacter
        )

        Assert.assertFalse(
            StrongPasswordValidator
                .validate("password")
                .containsOneUpperCaseCharacter
        )

    }

    @Test
    fun `Test password contains three lowercase characters` () {

        Assert.assertTrue(
            StrongPasswordValidator
                .validate("abc")
                .containsThreeLowerCaseCharacters
        )

        Assert.assertFalse(
            StrongPasswordValidator
                .validate("ab")
                .containsThreeLowerCaseCharacters
        )

    }

    @Test
    fun `Test password contains one number` () {

        Assert.assertTrue(
            StrongPasswordValidator
                .validate("password1")
                .containsOneNumber
        )

        Assert.assertFalse(
            StrongPasswordValidator
                .validate("password")
                .containsOneNumber
        )

    }

    @Test
    fun `Test password contains 8 or more characters` () {

        Assert.assertTrue(
            StrongPasswordValidator
                .validate("12345678")
                .containsEightOrMoreCharacters
        )

        Assert.assertFalse(
            StrongPasswordValidator
                .validate("123")
                .containsEightOrMoreCharacters
        )

    }

    @Test
    fun `Test password contains one special character` () {

        Assert.assertTrue(
            StrongPasswordValidator
                .validate("password!")
                .containsOneSpecialCharacter
        )

        Assert.assertFalse(
            StrongPasswordValidator
                .validate("password")
                .containsOneSpecialCharacter
        )

    }


    @Test
    fun `Test password levels` () {
        Assert.assertTrue(
            StrongPasswordValidator
                .validate("password")
                .level() == StrongPasswordValidator.Level.Weak
        )

        Assert.assertTrue(
            StrongPasswordValidator
                .validate("password123")
                .level() ===StrongPasswordValidator.Level.Medium
        )

        Assert.assertTrue(
            StrongPasswordValidator
                .validate("Password123!")
                .level() ===StrongPasswordValidator.Level.Strong
        )
    }




}