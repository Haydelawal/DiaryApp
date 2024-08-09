package com.hayde117.diaryapp.data.repository

import com.hayde117.diaryapp.model.Diary
import com.hayde117.diaryapp.utils.Constants.APP_ID
import io.realm.kotlin.Realm
import io.realm.kotlin.ext.query
import io.realm.kotlin.mongodb.App
import io.realm.kotlin.mongodb.sync.SyncConfiguration

object MongoDB: MongoRepository {

    private val app = App.create(APP_ID)
    private val user = app.currentUser
    private lateinit var realm: Realm

/**    hilt is not being used for netweok requests because the sdk handles that
    add more explanation here..... */

    init {
        configureTheRealm()
    }

    override fun configureTheRealm() {
        if (user != null) {
            val config = SyncConfiguration.Builder(user, setOf(Diary::class))
                .initialSubscriptions { sub ->
                    add(
                        query = sub.query<Diary>(query = "ownerId == $0", user.identity),
                        name = "User's Diaries"
                    )
                }
//                .log(LogLevel.ALL)
                .build()
            realm = Realm.open(config)
        }
    }
}