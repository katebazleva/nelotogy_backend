package ru.netology.dto

import ru.netology.model.Advertising
import ru.netology.model.Location
import ru.netology.model.PostType
import ru.netology.model.Video

data class PostRequestDto(
    val id: Int,
    val author: String,
    val content: String,
    val address: String?,
    val location: Location?,
    val video: Video?,
    val advertising: Advertising?,
    val postType: PostType?
)
