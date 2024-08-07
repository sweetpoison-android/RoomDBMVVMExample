package com.ciphersquare.roomdbmvvmexample

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "notes")
data class NotesModel(
    @PrimaryKey(autoGenerate = true)

    val id:Int,
    val title:String
)
