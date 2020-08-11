package ru.netology.model

import java.util.*

data class PostModel(
    val id: Int,
    val author: String,
    val content: String = "",
    val created: Date = Date(),
    var likesCount: Int = 0,
    var commentsCount: Int = 0,
    var shareCount: Int = 0,
    var likedByMe: Boolean = false,
    var commentedByMe: Boolean = false,
    var sharedByMe: Boolean = false,
    val address: String? = null,
    val location: Location? = null,
    val video: Video? = null,
    val advertising: Advertising? = null,
    val source: PostModel? = null,
    val postType: PostType = PostType.SIMPLE_POST,
    var isHidden: Boolean = false
) {
}
