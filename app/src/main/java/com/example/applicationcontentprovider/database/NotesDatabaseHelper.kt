package com.example.applicationcontentprovider.database

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.provider.BaseColumns._ID

class NotesDatabaseHelper(
    context: Context
): SQLiteOpenHelper(context, "databaseNotes", null, 1) {
    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL("CREATE TABLE $TABLE_NOTES (" +
                "$_ID INTEGER NOT NULL PRIMARY KEY, " +
                "$TITLE_NOTES TEXT NOT NULL, " +
                "$DESCRIPTION_NOTES TEXT NOT NULL)")
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        TODO("Not yet implemented")
    }

    companion object{
        const val TABLE_NOTES = "NOTES"
        const val TITLE_NOTES = "title"
        const val DESCRIPTION_NOTES = "description"
    }
}