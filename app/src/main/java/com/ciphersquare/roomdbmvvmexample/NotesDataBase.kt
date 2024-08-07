package com.ciphersquare.roomdbmvvmexample

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [NotesModel::class], version = 1)
abstract class NotesDataBase : RoomDatabase() {

    abstract fun notesDao() : NotesDao

    companion object{
        @Volatile
        private var INSTANCE:NotesDataBase? = null

        fun getDatabase(context: Context) : NotesDataBase{

            synchronized(this){
                if (INSTANCE == null){
                    INSTANCE = Room.databaseBuilder(context, NotesDataBase::class.java, "notesDB").build()
                }
            }

            return INSTANCE!!
        }
    }

}