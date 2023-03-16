package com.example.core.business.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.core.business.data.local.model.LocalGenre
import com.example.core.business.data.local.model.LocalMovie
import com.example.core.business.data.local.model.LocalTv

@Database(
    entities = [LocalMovie::class, LocalGenre::class, LocalTv::class],
    version = 1,
    exportSchema = false
)
abstract class LocalDatabase : RoomDatabase() {
    abstract fun localDB(): LocalDao

    companion object {
        const val DATABASE_NAME: String = "local_db"
    }
}