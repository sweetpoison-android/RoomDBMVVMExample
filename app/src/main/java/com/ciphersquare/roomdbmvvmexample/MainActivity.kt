package com.ciphersquare.roomdbmvvmexample

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.provider.MediaStore
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView.VERTICAL
import com.ciphersquare.roomdbmvvmexample.databinding.ActivityMainBinding
import java.io.ByteArrayOutputStream

class MainActivity : AppCompatActivity() {

   private lateinit var binding: ActivityMainBinding

   private var strNotes:String = ""

   var boolean = false

    var id:Int = 0

    private var bitmap: Bitmap? = null

   private lateinit var imgByte:ByteArray

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        val notesDao = NotesDataBase.getDatabase(applicationContext).notesDao()
        val notesRepository = NotesRepository(notesDao)
        val mainViewModel = ViewModelProvider(this, MainViewModelFactory(notesRepository)).get(MainViewModel::class.java)

        mainViewModel.getNotes().observe(this) { i->

            val adapter = NotesAdapter(i, this, itemClickListener = object : ItemClickListener {
                override fun onDeleteItemClicked(notesModel: NotesModel) {
                    mainViewModel.deleteNotes(notesModel)
                }

                override fun onUpdateItemClicked(
                    strUpdate: String,
                    bool: Boolean,
                    id: Int,
                    byte: ByteArray
                ) {

                   binding.etNotes.setText(strUpdate)
                    boolean = bool
                    this@MainActivity.id = id
                    imgByte = byte

                    val imageByte = imgByte
                    val bitmap = BitmapFactory.decodeByteArray(imageByte, 0, imageByte.size);
                    binding.image.setImageBitmap(bitmap);

                    if (boolean){
                        binding.btnSubmit.text = "Update"
                        binding.btnImage.text = "Change"
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

            btnImage.setOnClickListener {
                val intent = Intent()
                intent.action = Intent.ACTION_GET_CONTENT
                intent.type = "image/*"
                startActivityForResult(intent, 1)

            }

            btnSubmit.setOnClickListener {

                if (!boolean) {

                    val stream = ByteArrayOutputStream();
                    if (bitmap != null) {
                        bitmap!!.compress(Bitmap.CompressFormat.JPEG, 100, stream);
                        imgByte = stream.toByteArray();
                        mainViewModel.insertNotes(NotesModel(0, binding.etNotes.text.toString(),imgByte))
                        binding.etNotes.text = null

                        Toast.makeText(this@MainActivity, "Data Added", Toast.LENGTH_LONG).show()
                    }

                }
                else {

                    val stream = ByteArrayOutputStream();
                    if (bitmap != null) {
                        bitmap!!.compress(Bitmap.CompressFormat.JPEG, 100, stream);
                        imgByte = stream.toByteArray();
                        mainViewModel.updateNotes(NotesModel(id, binding.etNotes.text.toString(),imgByte))
                        binding.etNotes.text = null

                        Toast.makeText(this@MainActivity, "Data Added", Toast.LENGTH_LONG).show()
                    }

               //     mainViewModel.updateNotes(NotesModel(id, binding.etNotes.text.toString(),imgByte))

                    Toast.makeText(this@MainActivity, "Data Updated", Toast.LENGTH_LONG).show()
                    boolean = false
                    etNotes.text = null
                    btnSubmit.text = "Submit"
                    btnImage.text = "Image"
                }

            }

        }

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (resultCode == RESULT_OK && requestCode == 1 && data!= null) {
            val selectImgUrl = data.data
            binding.image.setImageURI(selectImgUrl)
            try {
                bitmap = MediaStore.Images.Media.getBitmap(contentResolver, selectImgUrl)
                binding.image.setImageBitmap(bitmap);
            } catch ( e:RuntimeException) {
                throw RuntimeException(e)
            }
        }
    }
}