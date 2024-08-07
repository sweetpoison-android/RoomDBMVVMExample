package com.ciphersquare.roomdbmvvmexample

 interface ItemClickListener {
    fun onItemClicked( notesModel: NotesModel)
    fun onItemUpdateClicked( strUpdate:String, bool:Boolean, id:Int)
}