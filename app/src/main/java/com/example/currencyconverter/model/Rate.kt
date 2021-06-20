package com.example.currencyconverter

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "rates")
data class RateItem(
    val alphaCode: String,
    val code: String,
    val date: String,
    val inverseRate: Double,
    val name: String,
    @PrimaryKey
    val numericCode: String,
    val rate: Double
)