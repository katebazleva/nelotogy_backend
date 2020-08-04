package ru.netology

import com.google.gson.Gson
import ru.netology.data.*
import java.util.*

fun main() {

    val createdTime = Calendar.getInstance()
    createdTime.set(2020, Calendar.JULY, 20, 9, 35, 0)

    val postsList = mutableListOf(
            Post(
                    1,
                    "kate bazleva",
                    "Something very interesting",
                    createdTime.time,
                    likesCount = 1,
                    shareCount = 2,
                    likedByMe = true,
                    sharedByMe = true
            ),
            Post(
                    2,
                    "bzzzz",
                    "Bzz-zzzz",
                    createdTime.time,
                    likesCount = 10,
                    video = Video("https://www.youtube.com/watch?v=G-7U-FDql1A"),
                    postType = PostType.VIDEO_POST
            ),
            Post(
                    3,
                    "mikki",
                    "Third post!",
                    createdTime.time,
                    commentsCount = 3,
                    shareCount = 2,
                    commentedByMe = true,
                    address = "Moscow State University",
                    location = 55.702893 x 37.530829,
                    postType = PostType.EVENT_POST
            ),
            Post(
                    4,
                    "mouse",
                    "QWERTYUIOP[ASDFGHJKL;zxcvbnm,dhfkjehrfvcljbdnvcli ubaeowhlvbkjfzds;oifeh;oigbvavubeb;vb zjk",
                    createdTime.time,
                    likesCount = 5,
                    shareCount = 2,
                    likedByMe = true
            ),
            Post(
                    5,
                    "mur",
                    "meow :3",
                    createdTime.time,
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

    createdTime.set(2020, Calendar.JULY, 31, 15, 1, 0)
    postsList.add(
            Post(
                    6,
                    "mur-mur-mur",
                    "meow!!!",
                    createdTime.time,
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
