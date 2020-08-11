package ru.netology.repository

import com.google.gson.Gson
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import ru.netology.model.PostModel
import java.io.File
import kotlin.coroutines.EmptyCoroutineContext

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
                val copy = item.copy(likesCount = item.likesCount++)
                items[index] = copy
                copy
            }
        }
    }

    override suspend fun dislikeById(id: Int): PostModel? {
        return when (val index = items.indexOfFirst { it.id == id }) {
            -1 -> null
            else -> {
                val item = items[index]
                val copy = item.copy(likesCount = item.likesCount--)
                items[index] = copy
                copy
            }
        }
    }
}

fun main() {
    val repositoryInMemory = PostRepositoryInMemoryImpl()

    val scope = CoroutineScope(EmptyCoroutineContext + SupervisorJob())

    repeat(10) {
        scope.launch {
            repositoryInMemory.save(PostModel(id = it, author = "Test"))
        }
    }

    scope.launch {
        println(Gson().toJson(repositoryInMemory.getAll()))
    }
}
