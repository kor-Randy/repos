package com.example.line_challenges.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.room.Room
import com.example.line_challenges.AppDatabase
import com.example.line_challenges.base.BaseAndroidViewModel
import com.example.line_challenges.model.Memo
import com.google.gson.Gson
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.functions.Consumer
import io.reactivex.schedulers.Schedulers
import org.json.JSONArray
import kotlin.collections.ArrayList

class MainViewModel(application : Application) : BaseAndroidViewModel(application)
{
    private val TAG = "MainViewModel.kt"
    var fragNum : MutableLiveData<Int> = MutableLiveData(0)
    private val db = Room.databaseBuilder(
        application,
        AppDatabase::class.java, "database-name"
    ).build()
    var memoList : MutableLiveData<List<Memo>> = MutableLiveData()
    var nowMemo : Memo = Memo(0,"","",null,null,null)
    var memoSize : Int=0
    var nowPathList : ArrayList<String> = ArrayList()
    var nowPath : MutableLiveData<String> = MutableLiveData()
    var jsonList :  JSONArray = JSONArray()
    var isFocusable : MutableLiveData<Boolean> = MutableLiveData(false)

    fun getAll()
    {
        addDisposable(db.memoDao().getAll()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {
                    memoList.value = it
                    memoSize = it.size
                },
                { error ->
                    Log.e(TAG,error.toString())
                })
        )
    }

    fun insetMemo()
    {
        nowMemo.imagePath = Gson().toJson(nowPathList)
        if(nowPathList.size==0)
            nowMemo.repImage = null
        else
            nowMemo.repImage = nowPathList[0]
        addDisposable(db.memoDao().insert(nowMemo)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe{
                fragNum.value=0
            })

    }

    fun deleteMemo()
    {
        addDisposable(db.memoDao().delete(nowMemo.id)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                memoSize--
                moveFrag()
            })
    }

    fun newMemo()
    {
        isFocusable.value=true
        nowPathList.clear()
        nowMemo = Memo(0,"","",null,null,null)
        fragNum.value=1
    }

    fun selectMemo(index : Int)
    {
        nowPathList.clear()
        addDisposable(db.memoDao().select(memoList.value!![index].id)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(Consumer {
                nowMemo = it
                jsonList=JSONArray(nowMemo.imagePath)
                if(jsonList.length()==1)
                    nowPathList.add(jsonList[0] as String)
                else
                {
                    for (i in 0..jsonList.length() - 1)
                        nowPathList.add(jsonList[i] as String)
                }
                isFocusable.value=false
                moveFrag()
            }))

    }

    fun getFocus()
    {
        isFocusable.value = isFocusable.value==false
    }

    fun moveFrag()
    {
        if(fragNum.value==0)
            fragNum.value=1
        else
            fragNum.value=0
    }

    fun putImagePath(path : String)
    {
        nowPathList.add(path)
        nowPath.value = path
    }
}