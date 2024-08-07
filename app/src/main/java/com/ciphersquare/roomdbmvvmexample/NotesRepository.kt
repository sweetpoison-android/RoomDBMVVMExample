package com.ciphersquare.roomdbmvvmexample

import androidx.lifecycle.LiveData

class NotesRepository(private val notesDao: NotesDao) {

    suspend fun insertNotes(notesModel: NotesModel){
        notesDao.insertNotes(notesModel)
    }

    suspend fun deleteNotes(notesModel: NotesModel){
        notesDao.deleteNotes(notesModel)
    }

    fun getNotes() : LiveData<List<NotesModel>>{
        return notesDao.getNotes()
    }

    fun updateNotes(notesModel: NotesModel)
    {
        notesDao.updateNotes(notesModel)
    }

}