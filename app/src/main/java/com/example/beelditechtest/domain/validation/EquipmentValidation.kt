package com.example.beelditechtest.domain.validation

object EquipmentValidation {
    fun validateBrand(value: String): String? =
        when {
            value.isEmpty() -> null
            value.length > 25 -> "La marque ne doit pas dépasser 25 caractères"
            value.isNotEmpty() && !value.first().isUpperCase() -> "La marque doit commencer par une majuscule"
            else -> null
        }

    fun validateModel(value: String): String? =
        when {
            value.isEmpty() -> null
            value.length > 50 -> "Le modèle ne doit pas dépasser 50 caractères"
            else -> null
        }

    fun validateSerialNumber(value: String): String? =
        when {
            value.isEmpty() -> null

            value.length > 50 -> "Le numéro de série ne doit pas dépasser 50 caractères"

            value.contains(" ") -> "Le numéro de série ne peut pas contenir d'espaces"

            !value.matches(
                Regex("^[A-Z0-9-]*$"),
            ) -> "Le numéro de série doit contenir uniquement des chiffres, des lettres majuscules et des tirets"

            else -> null
        }
}
