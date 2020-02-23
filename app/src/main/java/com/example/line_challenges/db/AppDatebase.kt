package com.example.line_challenges

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.line_challenges.model.MemoDao
import com.example.line_challenges.model.Memo

@Database(entities = [Memo::class], version = 1)
abstract class AppDatabase : RoomDatabase()
{
    abstract fun memoDao(): MemoDao
}
