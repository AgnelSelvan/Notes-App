package com.agnelselvan.myapplication.DB

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.widget.Toast
import com.agnelselvan.myapplication.Models.Notes
import kotlinx.android.synthetic.main.fragment_create_note.*

val DB_NAME = "MyNotes"
val NOTES_TABLE_NAME = "Notes"
val ID = "id"
val TITLE = "title"
val SUB_TITLE = "subtitle"
val DATE_TIME = "dateTime"
val NOTE_TEXT = "noteText"
val IMG_PATH = "imgPath"
val WEB_LINK = "webLink"
val COLOR = "color"





class DatabaseHandler(var context: Context) : SQLiteOpenHelper(context, DB_NAME, null, 1) {
    override fun onCreate(db: SQLiteDatabase?) {
        val createNotesTable = "CREATE TABLE " + NOTES_TABLE_NAME + "(" +
                ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                TITLE + " VARCHAR(256), "+
                SUB_TITLE + " VARCHAR(256), "+
                DATE_TIME + " VARCHAR(256), "+
                NOTE_TEXT + " VARCHAR(256), "+
                IMG_PATH + " VARCHAR(256), "+
                WEB_LINK + " VARCHAR(256), "+
                COLOR + " VARCHAR(256)) ";
        db?.execSQL(createNotesTable)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        TODO("Not yet implemented")
    }

    fun insertNote(notes: Notes){
        val db = this.writableDatabase;
        var cv = ContentValues()
        cv.put(TITLE, notes.title)
        cv.put(SUB_TITLE, notes.subTitle)
        cv.put(DATE_TIME, notes.datetime)
        cv.put(NOTE_TEXT, notes.noteText)
        cv.put(IMG_PATH, notes.imgPath)
        cv.put(WEB_LINK, notes.webLink)
        cv.put(COLOR, notes.color)
        var result = db.insert(NOTES_TABLE_NAME, null, cv);
        if(result == -1.toLong()){
            Toast.makeText(context, "Failed Insertion", Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(context, "Added Successfully", Toast.LENGTH_SHORT).show();
        }
    }

    fun readNotes() : ArrayList<Notes>{
        var notesList: ArrayList<Notes> = ArrayList()
        val db = this.readableDatabase
        val query = "SELECT * FROM " + NOTES_TABLE_NAME
        val result = db.rawQuery(query, null)

        if(result.moveToFirst()){
            do {
                var notes = Notes( result.getInt(result.getColumnIndex(ID)) , result.getString(result.getColumnIndex(TITLE)), result.getString(result.getColumnIndex(SUB_TITLE)), result.getString(result.getColumnIndex(DATE_TIME)), result.getString(result.getColumnIndex(NOTE_TEXT)), result.getString(result.getColumnIndex(IMG_PATH)), result.getString(result.getColumnIndex(WEB_LINK)), result.getString(result.getColumnIndex(COLOR))  )
                notesList.add(notes)
            }while(result.moveToNext())
        }

        return notesList
    }

    fun getNoteById(noteId: Int ):Notes{
        val db = this.readableDatabase
        val query = "SELECT * FROM " + NOTES_TABLE_NAME + " WHERE id="+noteId
        val result = db.rawQuery(query, null)

        var notes = Notes()
        if(result.moveToFirst()){
            do {
                notes = Notes( result.getInt(result.getColumnIndex(ID)) , result.getString(result.getColumnIndex(TITLE)), result.getString(result.getColumnIndex(SUB_TITLE)), result.getString(result.getColumnIndex(DATE_TIME)), result.getString(result.getColumnIndex(NOTE_TEXT)), result.getString(result.getColumnIndex(IMG_PATH)), result.getString(result.getColumnIndex(WEB_LINK)), result.getString(result.getColumnIndex(COLOR))  )

            }while(result.moveToNext())
        }
        return notes
    }

    fun updateNote(notes: Notes) {
        val db = this.writableDatabase;
        var cv = ContentValues()
        cv.put(TITLE, notes.title)
        cv.put(SUB_TITLE, notes.subTitle)
        cv.put(DATE_TIME, notes.datetime)
        cv.put(NOTE_TEXT, notes.noteText)
        cv.put(IMG_PATH, notes.imgPath)
        cv.put(WEB_LINK, notes.webLink)
        cv.put(COLOR, notes.color)
        db.update(NOTES_TABLE_NAME,cv,ID + "=? ",
                arrayOf(notes.id.toString(),))

        Toast.makeText(context, "Updated Successfully", Toast.LENGTH_SHORT).show();
    }

    fun deleteNote(id: Int){
        val db = this.writableDatabase;
        db.delete(NOTES_TABLE_NAME, ID + "=" + id, null)
    }

}