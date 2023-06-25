package com.example.segundo_parcial

data class Joke(
    val categories: List<String?>,
    val createdAt: String,
    val iconURL: String,
    val id: String,
    val updatedAt: String,
    val url: String,
    val value: String
)
