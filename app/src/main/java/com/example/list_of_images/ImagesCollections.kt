package com.example.list_of_images

import kotlin.random.Random

data class Picture (
    val id: Int,
    val author: String,
    val url: String
)

object ImagesCollections {

    fun generateSamplePictures(): List<Picture> {
        return listOf(
            Picture(1, "Mikhail Lermontov", "https://lafoy.ru/photo_l/foto-4157-1.jpg"),
            Picture(2, "Fyodor Dostoevsky", "https://lafoy.ru/photo_l/foto-4157-2.jpg"),
            Picture(3, "Mikhail Bulgakov", "https://lafoy.ru/photo_l/foto-4157-3.jpg"),
            Picture(4, "Maxim Gorky", "https://lafoy.ru/photo_l/foto-4157-4.jpg"),
            Picture(5, "Mikhail Saltykov-Shchedrin", "https://lafoy.ru/photo_l/foto-4157-5.jpg"),
            Picture(6, "Ivan Turgenev", "https://lafoy.ru/photo_l/foto-4157-6.jpg"),
            Picture(7, "Leo Tolstoy", "https://lafoy.ru/photo_l/foto-4157-7.jpg")
        )
    }

    fun generateNewPicture(): Picture {
        val id = Random.nextInt(8, 1000)
        val authors = listOf(
            "Mikhail Lermontov",
            "Fyodor Dostoevsky",
            "Mikhail Bulgakov",
            "Maxim Gorky",
            "Mikhail Saltykov-Shchedrin",
            "Ivan Turgenev",
            "Leo Tolstoy"
        )
        return Picture(
            id = id,
            author = authors.random(),
            url = "https://lafoy.ru/photo_l/foto-4157-${Random.nextInt(1, 8)}.jpg"
        )
    }
}
