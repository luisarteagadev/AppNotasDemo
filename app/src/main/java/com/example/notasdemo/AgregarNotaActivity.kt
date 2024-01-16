package com.example.notasdemo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.notasdemo.databinding.ActivityAgregarNotaBinding

class AgregarNotaActivity : AppCompatActivity() {
    private lateinit var binding:ActivityAgregarNotaBinding
    private lateinit var db:NotasDatabaseHelper
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityAgregarNotaBinding.inflate(layoutInflater)
        setContentView(binding.root)

        db= NotasDatabaseHelper(this)


        binding.btnSave.setOnClickListener {
            val title=binding.etTitleNote.text.toString()
            val content=binding.etContent.text.toString()
            val nota=Nota(0,title,content)
            db.insertarNota(nota)
            finish()
            Toast.makeText(this,"Nota Guardada",Toast.LENGTH_SHORT).show()
        }
    }
}