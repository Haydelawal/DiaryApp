package com.hayde117.mongo.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.hayde117.util.Constants.IMAGE_TO_DELETE_TABLE

@Entity(tableName = IMAGE_TO_DELETE_TABLE)
data class ImageToDelete(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val remoteImagePath: String
)