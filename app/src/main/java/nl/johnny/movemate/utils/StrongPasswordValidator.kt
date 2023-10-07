package nl.johnny.movemate.utils

object StrongPasswordValidator {
    enum class Level {
        None,
        Weak,
        Medium,
        Strong
    }

    class ValidationResult(
        val containsThreeLowerCaseCharacters: Boolean,
        val containsOneUpperCaseCharacter: Boolean,
        val containsOneNumber: Boolean,
        val containsOneSpecialCharacter: Boolean,
        val containsEightOrMoreCharacters: Boolean
    ) {

        fun level() : Level {
            var trueCount = 0
            if(containsThreeLowerCaseCharacters) trueCount++
            if(containsOneUpperCaseCharacter) trueCount++
            if(containsOneNumber) trueCount++
            if(containsOneSpecialCharacter) trueCount++
            if(containsEightOrMoreCharacters) trueCount++

            val level = when(trueCount) {
                5 -> Level.Strong
                4,3 -> Level.Medium
                2,1 -> Level.Weak
                else -> Level.None
            }

            return level
        }

        fun valid() : Boolean = level() == Level.Strong
    }

    private val containsThreeLowerCaseCharactersRegex: Regex = Regex("^(?=(.*[a-z]){3,}).*\$")
    private val containsOneUpperCaseCharacterRegex = Regex("^(?=(.*[A-Z])+).*\$")
    private val containsOneNumberRegex = Regex("^(?=(.*[0-9])+).*\$")
    private val containsOneSpecialCharacterRegex = Regex("^(?=(.*[!@#\$%^&*()\\-__+.])+).*\$")
    private val containsEightOrMoreCharactersRegex = Regex("^.{8,}\$")

    fun validate(password: String) = ValidationResult(
        containsThreeLowerCaseCharactersRegex.matches(password),
        containsOneUpperCaseCharacterRegex.matches(password),
        containsOneNumberRegex.matches(password),
        containsOneSpecialCharacterRegex.matches(password),
        containsEightOrMoreCharactersRegex.matches(password),
    )
}