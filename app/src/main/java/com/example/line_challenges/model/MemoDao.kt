package com.example.line_challenges.model

import androidx.room.*
import com.example.line_challenges.model.Memo
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Single

@Dao
interface MemoDao
{
    @Query("SELECT * FROM Memo")
    fun getAll() : Flowable<List<Memo>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(memo:Memo) : Completable

    @Update
    fun update(memo:Memo)

    @Query("DELETE FROM Memo WHERE id = :id")
    fun delete(id : Int) : Completable

    @Query("SELECT * FROM Memo WHERE id = :id")
    fun select(id : Int) : Single<Memo>
}