package ru.netology.repository

import ru.netology.model.PostModel

class PostRepositoryInMemoryImpl : PostRepository {
    private var nextId = 1
    private val items = mutableListOf<PostModel>()

    override suspend fun getAll(): List<PostModel> {
        return items.reversed()
    }

    override suspend fun getById(id: Int): PostModel? {
        return items.find { it.id == id }
    }

    override suspend fun save(item: PostModel): PostModel {
        return when (val index = items.indexOfFirst { it.id == item.id }) {
            -1 -> {
                val copy = item.copy(id = nextId++)
                items.add(copy)
                copy
            }
            else -> {
                items[index] = item
                item
            }
        }
    }

    override suspend fun removeById(id: Int) {
        items.removeIf { it.id == id }
    }

    override suspend fun likeById(id: Int): PostModel? {
        return when (val index = items.indexOfFirst { it.id == id }) {
            -1 -> null
            else -> {
                val item = items[index]
                item.likesCount++
                item
            }
        }
    }

    override suspend fun dislikeById(id: Int): PostModel? {
        return when (val index = items.indexOfFirst { it.id == id }) {
            -1 -> null
            else -> {
                val item = items[index]
                if (item.likesCount > 0) item.likesCount--
                item
            }
        }
    }
}
