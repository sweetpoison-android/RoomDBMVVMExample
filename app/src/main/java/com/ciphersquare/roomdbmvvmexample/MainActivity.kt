package com.ciphersquare.roomdbmvvmexample

import android.os.Bundle
import android.text.Editable
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView.ItemDecoration
import androidx.recyclerview.widget.RecyclerView.LayoutManager
import androidx.recyclerview.widget.RecyclerView.VERTICAL
import com.ciphersquare.roomdbmvvmexample.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

   private lateinit var binding: ActivityMainBinding

   private var strNotes:String = ""

   var boolean = false

    var id:Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        val notesDao = NotesDataBase.getDatabase(applicationContext).notesDao()
        val notesRepository = NotesRepository(notesDao)
        val mainViewModel = ViewModelProvider(this, MainViewModelFactory(notesRepository)).get(MainViewModel::class.java)

        mainViewModel.getNotes().observe(this) { i->

            val adapter = NotesAdapter(i, this, itemClickListener = object :
                ItemClickListener {
                override fun onItemClicked(notesModel: NotesModel) {
                    mainViewModel.deleteNotes(notesModel)
                }

                override fun onItemUpdateClicked(strUpdate: String, bool: Boolean, id: Int) {
                   binding.etNotes.setText(strUpdate)
                    boolean = bool
                    this@MainActivity.id = id

                    if (boolean){
                        binding.btnSubmit.text = "Update"
                    }

                }
            })
            val layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)

            binding.recyclerView.adapter = adapter
            binding.recyclerView.layoutManager = layoutManager
            binding.recyclerView.addItemDecoration(DividerItemDecoration(this,VERTICAL))

            strNotes = ""

            binding.tvData.text = ""
            for (j in i){

                val s = ""+j.id+". "+j.title+"\n"+strNotes
                 strNotes = s

            }

            binding.tvData.text = strNotes

        }

        binding.apply {
            binding.tvData.text = " "

            btnSubmit.setOnClickListener {

                if (!boolean)
                {
                    mainViewModel.insertNotes(NotesModel(0, binding.etNotes.text.toString()))
                    binding.etNotes.text = null

                    Toast.makeText(this@MainActivity, "Data Added", Toast.LENGTH_LONG).show()
                }
                else{
                    mainViewModel.updateNotes(NotesModel(id, binding.etNotes.text.toString()))

                    Toast.makeText(this@MainActivity, "Data Updated", Toast.LENGTH_LONG).show()
                    boolean = false
                    etNotes.text = null
                    btnSubmit.text = "Submit"
                }

            }


        }

    }
}