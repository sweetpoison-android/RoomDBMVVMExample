package com.ciphersquare.roomdbmvvmexample

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "notes")
data class NotesModel(
    @PrimaryKey(autoGenerate = true)

    @ColumnInfo(name = "ID")
    var id:Int = 0,

    @ColumnInfo(name = "Notes")
    var title:String = "",

    @ColumnInfo(name = "Image", typeAffinity = ColumnInfo.BLOB)
    var byte: ByteArray = ByteArray(0)
)
