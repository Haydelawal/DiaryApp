package com.hayde117.diaryapp.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.hayde117.diaryapp.data.database.entity.ImageToDelete
import com.hayde117.diaryapp.data.database.entity.ImageToUpload


@Database(
    entities = [ImageToUpload::class, ImageToDelete::class],
    version = 2,
    exportSchema = false
)
abstract class ImagesDatabase: RoomDatabase() {
    abstract fun imageToUploadDao(): ImageToUploadDao
    abstract fun imageToDeleteDao(): ImageToDeleteDao
}