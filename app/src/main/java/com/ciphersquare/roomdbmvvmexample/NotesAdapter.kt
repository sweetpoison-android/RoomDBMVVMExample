package com.ciphersquare.roomdbmvvmexample

import android.content.Context
import android.graphics.BitmapFactory
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.ciphersquare.roomdbmvvmexample.databinding.NotesItemBinding

class NotesAdapter(private var arrayList: List<NotesModel>, private val context: Context, private var itemClickListener: ItemClickListener) : RecyclerView.Adapter<NotesAdapter.NotesViewHolder>() {

    class NotesViewHolder(val binding: NotesItemBinding) : ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NotesViewHolder {
       val binding = NotesItemBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return NotesViewHolder(binding)
    }

    override fun getItemCount(): Int {
       return arrayList.size
    }

    override fun onBindViewHolder(holder: NotesViewHolder, position: Int) {
        holder.binding.apply {
            tvNotes.text =""+arrayList[position].id+".\t"+ arrayList[position].title

            if (position%2 == 0){
                mainLayout.setBackgroundColor(context.resources.getColor(R.color.white))
            }

            val imageByte = arrayList[position].byte
            val bitmap = BitmapFactory.decodeByteArray(imageByte, 0, imageByte.size);
            holder.binding.imgPhoto.setImageBitmap(bitmap);

            imgDelete.setOnClickListener {
              itemClickListener.onDeleteItemClicked(arrayList[position])
            }

            imgUpdate.setOnClickListener {

                itemClickListener.onUpdateItemClicked(arrayList[position].title, true, arrayList[position].id, arrayList[position].byte )

            }

        }

    }

}