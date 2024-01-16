package com.example.notasdemo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.notasdemo.databinding.ActivityActualizarBinding

class ActualizarActivity : AppCompatActivity() {

    private lateinit var binding:ActivityActualizarBinding
    private lateinit var db:NotasDatabaseHelper
    private var notaId:Int=-1
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityActualizarBinding.inflate(layoutInflater)
        setContentView(binding.root)

        db= NotasDatabaseHelper(this)
        notaId=intent.getIntExtra("nota_id",-1)

        if(notaId==-1){
            finish()
            return
        }
        val nota=db.getNotaPorID(notaId)
        binding.etUpdateTitleNote.setText(nota.title)
        binding.etUpdateContent.setText(nota.content)

        binding.btnUpdateSave.setOnClickListener{


            val newTitle=binding.etUpdateTitleNote.text.toString()
            val newContent=binding.etUpdateContent.text.toString()
            val newNota=Nota(notaId,newTitle,newContent)
            db.updateNota(newNota)
            finish()
            Toast.makeText(this,"Cambios Guardados",Toast.LENGTH_SHORT).show()

        }

    }
}