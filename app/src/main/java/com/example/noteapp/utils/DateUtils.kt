package com.example.noteapp.utils

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

fun Date.formatToSimpleDate(): String {
    val formatter = SimpleDateFormat("dd MMM yyyy", Locale.getDefault())
    return formatter.format(this)
}