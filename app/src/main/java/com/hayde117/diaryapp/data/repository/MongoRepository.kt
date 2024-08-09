package com.hayde117.diaryapp.data.repository

import com.hayde117.diaryapp.model.Diary
import io.realm.kotlin.types.ObjectId
import kotlinx.coroutines.flow.Flow
import java.time.LocalDate
import java.time.ZonedDateTime


interface MongoRepository {

    fun configureTheRealm()

}