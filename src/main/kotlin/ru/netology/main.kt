package ru.netology

import com.google.gson.Gson
import ru.netology.model.*
import java.time.LocalDateTime

fun main() {

    val createdTime = LocalDateTime.of(2020, 8, 20, 10, 35, 0)

    val postsList = mutableListOf(
        PostModel(
            1,
            "kate bazleva",
            "Something very interesting",
            createdTime,
            likesCount = 1,
            shareCount = 2,
            likedByMe = true,
            sharedByMe = true
        ),
        PostModel(
            2,
            "bzzzz",
            "Bzz-zzzz",
            createdTime.plusHours(1),
            likesCount = 10,
            video = Video("https://www.youtube.com/watch?v=G-7U-FDql1A"),
            postType = PostType.VIDEO_POST
        ),
        PostModel(
            3,
            "mikki",
            "Third post!",
            createdTime.plusDays(1),
            commentsCount = 3,
            shareCount = 2,
            commentedByMe = true,
            address = "Moscow State University",
            location = 55.702893 x 37.530829,
            postType = PostType.EVENT_POST
        ),
        PostModel(
            4,
            "mouse",
            "QWERTYUIOP[ASDFGHJKL;zxcvbnm,dhfkjehrfvcljbdnvcli ubaeowhlvbkjfzds;oifeh;oigbvavubeb;vb zjk",
            createdTime.plusMonths(1),
            likesCount = 5,
            shareCount = 2,
            likedByMe = true
        ),
        PostModel(
            5,
            "mur",
            "meow :3",
            createdTime,
            likesCount = 100,
            commentsCount = 3,
            shareCount = 2,
            likedByMe = true,
            sharedByMe = true,
            advertising = Advertising(
                "https://promokody-tmall.ru/wp-content/uploads/2020/03/netology-e1585655654780.png",
                "https://netology.ru/programs/kotlindevelopment/"
            ),
            postType = PostType.ADVERTISING
        )
    )

    postsList.add(
        PostModel(
            6,
            "mur-mur-mur",
            "meow!!!",
            createdTime.minusMonths(1),
            likesCount = 2,
            commentsCount = 0,
            shareCount = 0,
            likedByMe = true,
            sharedByMe = false,
            source = postsList[4],
            postType = PostType.REPOST
        )
    )

    println(Gson().toJson(postsList))
}
