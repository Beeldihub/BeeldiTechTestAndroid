package com.example.beelditechtest.domain.validation

import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertNull
import org.junit.Test

class EquipmentValidationTest {
    @Test
    fun `validateBrand should return null for empty string`() {
        val result = EquipmentValidation.validateBrand("")
        assertNull(result)
    }

    @Test
    fun `validateBrand should return error when length exceeds 25 characters`() {
        val longBrand = "A".repeat(26)
        val result = EquipmentValidation.validateBrand(longBrand)
        assertEquals("La marque ne doit pas dépasser 25 caractères", result)
    }

    @Test
    fun `validateBrand should return error when does not start with uppercase`() {
        val result = EquipmentValidation.validateBrand("apple")
        assertEquals("La marque doit commencer par une majuscule", result)
    }

    @Test
    fun `validateBrand should return null for valid brand`() {
        val result = EquipmentValidation.validateBrand("Apple")
        assertNull(result)
    }

    @Test
    fun `validateBrand should return null for brand with exactly 25 characters`() {
        val validBrand = "A".repeat(25)
        val result = EquipmentValidation.validateBrand(validBrand)
        assertNull(result)
    }

    @Test
    fun `validateModel should return null for empty string`() {
        val result = EquipmentValidation.validateModel("")
        assertNull(result)
    }

    @Test
    fun `validateModel should return error when length exceeds 50 characters`() {
        val longModel = "M".repeat(51)
        val result = EquipmentValidation.validateModel(longModel)
        assertEquals("Le modèle ne doit pas dépasser 50 caractères", result)
    }

    @Test
    fun `validateModel should return null for valid model`() {
        val result = EquipmentValidation.validateModel("iPhone 15 Pro Max")
        assertNull(result)
    }

    @Test
    fun `validateModel should return null for model with exactly 50 characters`() {
        val validModel = "M".repeat(50)
        val result = EquipmentValidation.validateModel(validModel)
        assertNull(result)
    }

    @Test
    fun `validateSerialNumber should return null for empty string`() {
        val result = EquipmentValidation.validateSerialNumber("")
        assertNull(result)
    }

    @Test
    fun `validateSerialNumber should return error when length exceeds 50 characters`() {
        val longSerial = "A".repeat(51)
        val result = EquipmentValidation.validateSerialNumber(longSerial)
        assertEquals("Le numéro de série ne doit pas dépasser 50 caractères", result)
    }

    @Test
    fun `validateSerialNumber should return error when contains spaces`() {
        val result = EquipmentValidation.validateSerialNumber("ABC 123")
        assertEquals("Le numéro de série ne peut pas contenir d'espaces", result)
    }

    @Test
    fun `validateSerialNumber should return error when contains lowercase letters`() {
        val result = EquipmentValidation.validateSerialNumber("abc123")
        assertEquals(
            "Le numéro de série doit contenir uniquement des chiffres, des lettres majuscules et des tirets",
            result,
        )
    }

    @Test
    fun `validateSerialNumber should return error when contains special characters`() {
        val result = EquipmentValidation.validateSerialNumber("ABC@123")
        assertEquals(
            "Le numéro de série doit contenir uniquement des chiffres, des lettres majuscules et des tirets",
            result,
        )
    }

    @Test
    fun `validateSerialNumber should return null for valid serial number with uppercase letters and numbers`() {
        val result = EquipmentValidation.validateSerialNumber("ABC123")
        assertNull(result)
    }

    @Test
    fun `validateSerialNumber should return null for valid serial number with dashes`() {
        val result = EquipmentValidation.validateSerialNumber("ABC-123-XYZ")
        assertNull(result)
    }

    @Test
    fun `validateSerialNumber should return null for serial number with exactly 50 characters`() {
        val validSerial = "A".repeat(50)
        val result = EquipmentValidation.validateSerialNumber(validSerial)
        assertNull(result)
    }

    @Test
    fun `validateSerialNumber should return null for serial number with only numbers`() {
        val result = EquipmentValidation.validateSerialNumber("123456")
        assertNull(result)
    }
}
