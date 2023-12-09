package com.company.sneakership.model

data class Sneaker(
    val id: Int,
    val name: String,
    val price: Double
)

data class SneakerDetails(
    val id: Int,
    val name: String,
    val price: Double,
    val description: String
)
