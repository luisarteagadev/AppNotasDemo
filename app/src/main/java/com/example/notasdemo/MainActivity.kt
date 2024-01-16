package com.example.notasdemo

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

import androidx.recyclerview.widget.LinearLayoutManager
import com.example.notasdemo.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var  binding:ActivityMainBinding
    private lateinit var db:NotasDatabaseHelper
    private lateinit var notasAdapter:NotasAdapter



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        db=NotasDatabaseHelper(this)
        notasAdapter=NotasAdapter(db.getNotas(),this)

        binding.rvNotas.layoutManager=LinearLayoutManager(this)
        binding.rvNotas.adapter=notasAdapter



        binding.addButton.setOnClickListener {

            val intent=Intent(this,AgregarNotaActivity::class.java)
            startActivity(intent)

        }
    }

    override fun onResume(){
        super.onResume()
        notasAdapter.refresheData(db.getNotas())
    }
}