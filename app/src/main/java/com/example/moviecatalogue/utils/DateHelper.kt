package com.example.moviecatalogue.utils

import java.text.SimpleDateFormat
import java.util.*

object DateHelper {

    fun formatDateToMatch(date: Date): String {
        return SimpleDateFormat("EE, dd MMMM yyyy", Locale.getDefault()).format(date)
    }
}