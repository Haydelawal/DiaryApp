package com.hayde117.diaryapp.data.repository

import com.hayde117.diaryapp.model.Diary
import com.hayde117.diaryapp.utils.RequestState
import io.realm.kotlin.types.ObjectId
import kotlinx.coroutines.flow.Flow
import java.time.LocalDate
import java.time.ZonedDateTime

typealias Diaries = RequestState<Map<LocalDate, List<Diary>>>

interface MongoRepository {

    fun configureTheRealm()

    fun getAllDiaries(): Flow<Diaries>

    fun getSelectedDiary(diaryId: ObjectId): RequestState<Diary>

    suspend fun insertDiary(diary: Diary): RequestState<Diary>


}