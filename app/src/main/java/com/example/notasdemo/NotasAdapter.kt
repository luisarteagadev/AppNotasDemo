package com.example.notasdemo

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView

class NotasAdapter(private var lista:List<Nota>,context:Context): RecyclerView.Adapter<NotasAdapter.NotaViewHolder>() {

    private val db:NotasDatabaseHelper = NotasDatabaseHelper(context)


        class NotaViewHolder(itemView: View):RecyclerView.ViewHolder(itemView) {
            val titleNota:TextView=itemView.findViewById(R.id.etTitleNote)
            val contentNota:TextView=itemView.findViewById(R.id.etContentNote)
            val btnEditNota:ImageView=itemView.findViewById(R.id.btnEditNota)
            val btnDeleteNota:ImageView=itemView.findViewById(R.id.btnDeleteNota)
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NotaViewHolder {
        val view=LayoutInflater.from(parent.context).inflate(R.layout.nota_item,parent,false)
        return NotaViewHolder(view)
    }

    override fun getItemCount(): Int =lista.size

    override fun onBindViewHolder(holder: NotaViewHolder, position: Int) {
        val nota=lista[position]
        holder.titleNota.text=nota.title
        holder.contentNota.text=nota.content

        holder.btnEditNota.setOnClickListener {
            val intent= Intent(holder.itemView.context,ActualizarActivity::class.java).apply {
                putExtra("nota_id",nota.id)
            }
            holder.itemView.context.startActivity(intent)

        }

        holder.btnDeleteNota.setOnClickListener{

            db.deleteNota(nota.id)
            refresheData(db.getNotas())
            Toast.makeText(holder.itemView.context,"Nota Eliminada",Toast.LENGTH_SHORT).show()
        }

    }

    fun refresheData(newList:List<Nota>){
        lista=newList
        notifyDataSetChanged()
    }


}