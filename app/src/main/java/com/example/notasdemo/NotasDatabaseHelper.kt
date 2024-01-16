package com.example.notasdemo

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class NotasDatabaseHelper(context: Context):SQLiteOpenHelper(context,DATABASE_NAME,null,DATABASE_VERSION) {
    companion object{
        private const val DATABASE_NAME="notasapp.db"
        private const val DATABASE_VERSION=1
        private const val TABLE_NAME="mis_notas"
        private const val COLUMN_ID="id"
        private const val COLUMN_TITLE="title"
        private const val COLUMN_CONTENT="content"
    }

    override fun onCreate(db: SQLiteDatabase?) {
        val createTableQuery="CREATE TABLE $TABLE_NAME($COLUMN_ID INTEGER PRIMARY KEY,$COLUMN_TITLE TEXT, $COLUMN_CONTENT TEXT)"
        db?.execSQL(createTableQuery)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
      val dropTableQuery="DROP TABLE IF EXISTS $TABLE_NAME"
      db?.execSQL(dropTableQuery)
      onCreate(db)

    }

    fun insertarNota(nota: Nota){
        val db=writableDatabase
        val values=ContentValues().apply {
            put(COLUMN_TITLE,nota.title)
            put(COLUMN_CONTENT,nota.content)
        }
        db.insert(TABLE_NAME,null,values)
        db.close()
    }

    fun getNotas():List<Nota>{
        val listNotas= mutableListOf<Nota>()
        val db=readableDatabase
        val query="SELECT * FROM $TABLE_NAME"
        val cursor=db.rawQuery(query,null)
        while(cursor.moveToNext()){

            val id=cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_ID))
            val title=cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_TITLE))
            val content=cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_CONTENT))
            val nota=Nota(id, title, content)
            listNotas.add(nota)
        }
        cursor.close()
        db.close()
        return listNotas
    }

    fun updateNota(nota:Nota){
        val db=writableDatabase
        val values=ContentValues().apply {
            put(COLUMN_TITLE,nota.title)
            put(COLUMN_CONTENT,nota.content)
        }
        val whereClause="$COLUMN_ID = ?"
        val whereArgs = arrayOf(nota.id.toString())
        db.update(TABLE_NAME,values,whereClause,whereArgs)
        db.close()

    }
    fun getNotaPorID(notaId:Int):Nota{
        val db=readableDatabase
        val query="SELECT * FROM $TABLE_NAME WHERE $COLUMN_ID = $notaId"
        val cursor=db.rawQuery(query,null)
        cursor.moveToFirst()
        val id=cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_ID))
        val title=cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_TITLE))
        val content=cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_CONTENT))
        cursor.close()
        db.close()
        return Nota(id,title,content)
    }

    fun deleteNota(notaId:Int){
        val db=writableDatabase
        val whereClause="$COLUMN_ID = ?"
        val whereArgs= arrayOf(notaId.toString())
        db.delete(TABLE_NAME,whereClause,whereArgs)
        db.close()

    }

}