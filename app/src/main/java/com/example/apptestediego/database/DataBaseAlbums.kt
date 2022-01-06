package com.example.apptestediego.database

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.example.apptestediego.model.AlbumsResponseModel
import com.example.apptestediego.model.PostsResponseModel
import java.lang.Exception
import java.util.ArrayList

class DataBaseAlbums(ctx: Context) : SQLiteOpenHelper(ctx, DB_NAME, null, DB_VERSION) {
    override fun onCreate(db: SQLiteDatabase?) {
        val CREATE_TABLE =
                "CREATE TABLE $TABLE_NAME ($TITLE TEXT, $ID INTEGER PRIMARY KEY, $USERID INTEGER);"
        db?.execSQL(CREATE_TABLE)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        val DROP_TABLE = "DROP TABLE IF EXISTS $TABLE_NAME"
        db?.execSQL(DROP_TABLE)
        onCreate(db)
    }

    fun addItem(item: AlbumsResponseModel): Boolean {
        try {
            val db = this.writableDatabase
            val values = ContentValues()

            values.put(USERID, item.userId)
            values.put(ID, item.id)
            values.put(TITLE, item.title)

            val _success = db.insert(TABLE_NAME, null, values)
            return (("$_success").toInt() != -1)
        } catch (ex: java.lang.Exception) {
            ex.printStackTrace()
            return false
        }
    }

    @SuppressLint("Range")
    fun getItem(_id: Int): AlbumsResponseModel {
        val item = AlbumsResponseModel()
        val db = writableDatabase
        val selectQuery = "SELECT * FROM $TABLE_NAME WHERE $ID = $_id"
        val cursor = db.rawQuery(selectQuery, null)
        cursor?.moveToFirst()

        item.userId = cursor.getInt(cursor.getColumnIndex(USERID))
        item.id = cursor.getInt(cursor.getColumnIndex(ID))
        item.title = cursor.getString(cursor.getColumnIndex(TITLE))

        cursor.close()
        return item
    }

    @SuppressLint("Range")
    fun listaItens(): ArrayList<AlbumsResponseModel> {
        val itemList = ArrayList<AlbumsResponseModel>()
        try {
            val db = writableDatabase
            val selectQuery = "SELECT * FROM $TABLE_NAME"
            val cursor = db.rawQuery(selectQuery, null)
            if (cursor != null) {
                if (cursor.moveToFirst()) {
                    do {
                        val item = AlbumsResponseModel()
                        item.userId = cursor.getInt(cursor.getColumnIndex(USERID))
                        item.id = cursor.getInt(cursor.getColumnIndex(ID))
                        item.title = cursor.getString(cursor.getColumnIndex(TITLE))

                        itemList.add(item)
                    } while (cursor.moveToNext())
                }
            }
            cursor.close()
            return itemList
        }catch (e:Exception){
            e.printStackTrace()
            return itemList
        }
    }

    fun updateItem(item: AlbumsResponseModel): Boolean {
        val db = this.writableDatabase
        val values = ContentValues().apply {
            put(USERID, item.userId)
            put(ID, item.id)
            put(TITLE, item.title)
        }
        val _success = db.update(TABLE_NAME, values, "$ID=?", arrayOf(item.id.toString())).toLong()
        db.close()
        return ("$_success").toInt() != -1
    }

    fun deleteItem(_id: Int): Boolean {
        val db = this.writableDatabase
        val _success = db.delete(TABLE_NAME, "$ID=?", arrayOf(_id.toString())).toLong()
        return ("$_success").toInt() != -1
    }

    fun deleteAll(): Boolean {
        try {
            val db = this.writableDatabase
            val _success = db.delete(TABLE_NAME, null, null).toLong()
            db.close()
            return ("$_success").toInt() != -1
        } catch (ex: Exception) {
            ex.printStackTrace()
            return false
        }
    }

    companion object {
        private val DB_VERSION = 1
        private val DB_NAME = "Testeframework2"
        private val TABLE_NAME = "DadosAlbuns"

        private val USERID = "userId"
        private val ID = "Id"
        private val TITLE = "Title"
    }
}