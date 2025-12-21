package com.example.beelditechtest.core.util

import java.text.Normalizer

fun String.normalize(): String =
    Normalizer
        .normalize(this, Normalizer.Form.NFD)
        .replace("\\p{InCombiningDiacriticalMarks}+".toRegex(), "")
