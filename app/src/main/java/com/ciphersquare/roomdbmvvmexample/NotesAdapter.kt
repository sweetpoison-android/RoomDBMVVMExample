package com.ciphersquare.roomdbmvvmexample

import android.content.Context
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

            imgDelete.setOnClickListener {
              itemClickListener.onItemClicked(arrayList[position])
            }

            imgUpdate.setOnClickListener {
                itemClickListener.onItemUpdateClicked(arrayList[position].title, true, arrayList[position].id)

            }

        }

    }

}