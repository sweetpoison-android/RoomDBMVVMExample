package com.ciphersquare.roomdbmvvmexample

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Entity
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update


@Dao
interface NotesDao {
    @Insert
   suspend fun insertNotes(notesModel: NotesModel)

    @Delete
   suspend fun deleteNotes(notesModel: NotesModel)

    @Query("SELECT * FROM notes")
    fun getNotes() : LiveData<List<NotesModel>>

    @Update
    fun updateNotes(notesModel: NotesModel)

}