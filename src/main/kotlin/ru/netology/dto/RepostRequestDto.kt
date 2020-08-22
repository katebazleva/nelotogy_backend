package ru.netology.dto

import java.util.*

data class RepostRequestDto(
    val id: Int,
    val author: String,
    val content: String,
    val created: Date,
    val originalPostId: Int
)
