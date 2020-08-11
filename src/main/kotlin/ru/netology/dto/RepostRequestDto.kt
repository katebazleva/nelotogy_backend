package ru.netology.dto

data class RepostRequestDto(
    val id: Int,
    val author: String,
    val content: String,
    val originalPostId: Int
)
