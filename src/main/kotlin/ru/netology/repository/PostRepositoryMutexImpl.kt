package ru.netology.repository

import com.google.gson.Gson
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import ru.netology.model.PostModel
import java.io.File
import kotlin.coroutines.EmptyCoroutineContext

class PostRepositoryMutexImpl : PostRepository {
    private var nextId = 1
    private val items = mutableListOf<PostModel>()
    private val mutex = Mutex()

    override suspend fun getAll(): List<PostModel> = mutex.withLock {
        items.reversed()
    }

    override suspend fun getById(id: Int): PostModel? = mutex.withLock {
        items.find { it.id == id }?.also { it.timesShown++ }
    }

    override suspend fun save(item: PostModel): PostModel = mutex.withLock {
        when (val index = items.indexOfFirst { it.id == item.id }) {
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
        mutex.withLock {
            items.removeIf { it.id == id }
        }
    }

    override suspend fun likeById(id: Int): PostModel? = mutex.withLock {
        when (val index = items.indexOfFirst { it.id == id }) {
            -1 -> null
            else -> {
                val item = items[index]
                item.likesCount++
                item
            }
        }
    }

    override suspend fun dislikeById(id: Int): PostModel? = mutex.withLock {
        when (val index = items.indexOfFirst { it.id == id }) {
            -1 -> null
            else -> {
                val item = items[index]
                if (item.likesCount > 0) item.likesCount--
                item
            }
        }
    }
}

fun main() {
    repeat(10) {
        val repo = PostRepositoryMutexImpl()

        val scope = CoroutineScope(EmptyCoroutineContext + SupervisorJob())

        repeat(10_000) {
            scope.launch {
                repo.save(PostModel(id = 0, author = "Test"))
            }
        }

        Thread.sleep(1000)

        with(CoroutineScope(EmptyCoroutineContext + SupervisorJob())) {
            repeat(100_000) {
                launch {
                    repo.likeById(1)
                }
            }
        }

        with(CoroutineScope(EmptyCoroutineContext + SupervisorJob())) {
            launch {
                println(repo.getById(1))
                repo.removeById(1)
                println("After remove ${repo.getById(1)}")
            }
        }

        Thread.sleep(2500)

        runBlocking {
            val all = repo.getAll()
            println(all.size)
            File("result.json").writeText(Gson().toJson(all))
        }
    }
}
