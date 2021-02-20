package com.agnelselvan.myapplication.DB

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.widget.Toast
import com.agnelselvan.myapplication.Models.Notes

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
                COLOR + " VARCHAR(256), ";
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

}