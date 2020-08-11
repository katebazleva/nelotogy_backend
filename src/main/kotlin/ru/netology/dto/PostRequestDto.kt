package ru.netology.dto

import ru.netology.model.Location
import ru.netology.model.PostType
import ru.netology.model.Video

data class PostRequestDto(
    val id: Int,
    val author: String,
    val content: String,
    val address: String? = null,
    val location: String? = null,
    val video: String? = null,
    val postType: String? = null
)

fun getPostTypeFromString(text: String?) = when (text) {
    "EVENT_POST" -> PostType.EVENT_POST
    "VIDEO_POST" -> PostType.VIDEO_POST
    "REPOST" -> PostType.REPOST
    "ADVERTISING" -> PostType.ADVERTISING
    else -> PostType.SIMPLE_POST
}


fun getLocationFromString(text: String): Location {
    val lat = text.substringBefore(" ").toDouble()
    val lng = text.substringAfter(" ").toDouble()
    return Location(lat, lng)
}

fun getVideoFromString(text: String) = Video(text)
