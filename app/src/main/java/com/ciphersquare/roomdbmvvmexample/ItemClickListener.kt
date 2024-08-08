package com.ciphersquare.roomdbmvvmexample

 interface ItemClickListener {
    fun onDeleteItemClicked(notesModel: NotesModel)
    fun onUpdateItemClicked(strUpdate:String, bool:Boolean, id:Int, byte:ByteArray)
}