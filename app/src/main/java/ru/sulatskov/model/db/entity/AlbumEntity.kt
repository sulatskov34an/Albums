package ru.sulatskov.model.db.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "album")
class AlbumEntity (
    @PrimaryKey
    var id: Int? = 0,
    var userId: Int? = 0,
    var title: String? = ""

)