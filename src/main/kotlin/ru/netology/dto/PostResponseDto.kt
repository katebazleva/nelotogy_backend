package ru.netology.dto

import ru.netology.model.*
import java.util.*

data class PostResponseDto(
    val id: Int,
    val author: String,
    val content: String,
    val created: Date,
    var likesCount: Int,
    var commentsCount: Int = 0,
    var shareCount: Int = 0,
    var likedByMe: Boolean,
    var commentedByMe: Boolean = false,
    var sharedByMe: Boolean = false,
    val address: String?,
    val location: Location?,
    val video: Video?,
    val advertising: Advertising?,
    val source: PostModel? = null,
    val postType: PostType,
    var isHidden: Boolean = false,
    var timesShown: Long
) {
    companion object {
        fun fromModel(model: PostModel) = PostResponseDto(
            id = model.id,
            author = model.author,
            content = model.content,
            created = model.created,
            likesCount = model.likesCount,
            likedByMe = model.likedByMe,
            address = model.address,
            location = model.location,
            video = model.video,
            advertising = model.advertising,
            postType = model.postType,
            timesShown = model.timesShown
        )
    }
}
