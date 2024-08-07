package com.ciphersquare.roomdbmvvmexample

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainViewModel(private val notesRepository: NotesRepository) : ViewModel() {

    fun insertNotes(notesModel: NotesModel){
        viewModelScope.launch(Dispatchers.IO) {
            notesRepository.insertNotes(notesModel)
        }

    }

    fun getNotes(): LiveData<List<NotesModel>>{
        return notesRepository.getNotes()
    }

    fun deleteNotes(notesModel: NotesModel){
        viewModelScope.launch(Dispatchers.IO) {
            notesRepository.deleteNotes(notesModel)
        }
    }

    fun updateNotes(notesModel: NotesModel){
        viewModelScope.launch(Dispatchers.IO) {
            notesRepository.updateNotes(notesModel)
        }
    }

}