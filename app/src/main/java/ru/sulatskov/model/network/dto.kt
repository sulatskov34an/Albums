package ru.sulatskov.model.network

class Album(
    val userId: Int? = 0,
    val id: Int? = 0,
    val title: String? = ""
)

class Photo(
    var albumId: Int? = 0,
    var id: Int? = 0,
    var title: String? = "",
    var url: String? = "",
    var thumbnailUrl: String? = ""
)