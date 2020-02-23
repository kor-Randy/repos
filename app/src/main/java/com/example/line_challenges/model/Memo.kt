package com.example.line_challenges.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Memo(@PrimaryKey(autoGenerate = true) val id : Int = 0,
                var title : String,
                var content : String,
                var repImage : String?,
                var imageUrl : String?,
                var imagePath : String?)
