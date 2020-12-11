package com.harshit.stockmanagementapp.database

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteConstraintException
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteException
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log
import com.google.gson.Gson
import com.harshit.stockmanagementapp.model.OrderHistoryModel


class UsersDBHelper1(context: Context) : SQLiteOpenHelper(
    context,
    DATABASE_NAME, null,
    DATABASE_VERSION
) {
    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL(SQL_CREATE_ENTRIES)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
// This database is only a cache for online data, so its upgrade policy is
// to simply to discard the data and start over
        db.execSQL(SQL_DELETE_ENTRIES)
        onCreate(db)
    }

    override fun onDowngrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        onUpgrade(db, oldVersion, newVersion)
    }

    @Throws(SQLiteConstraintException::class)
    fun insertUser(user: OrderHistoryModel): Boolean {
        val db = writableDatabase
        val values = ContentValues()

        values.put(DBContract1.UserEntry.COLUMN_DATE, user.date)
        values.put(DBContract1.UserEntry.COLUMN_DATA, user.data)
        val newRowId = db.insert(DBContract1.UserEntry.TABLE_NAME, null, values)
        return true
    }

    fun readAllUsers(): ArrayList<OrderHistoryModel> {
        val users = ArrayList<OrderHistoryModel>()
        val db = writableDatabase
        var cursor: Cursor? = null
        try {
            cursor = db.rawQuery("select * from " + DBContract1.UserEntry.TABLE_NAME, null)
        } catch (e: SQLiteException) {
            db.execSQL(SQL_CREATE_ENTRIES)
            db.execSQL(SQL_CREATE_ENTRIES)
            db.execSQL(SQL_CREATE_ENTRIES)
            return ArrayList()
        }
        var date: String
        var data: String
        if (cursor!!.moveToFirst()) {
            while (cursor.isAfterLast == false) {
                date = cursor.getString(cursor.getColumnIndex(DBContract1.UserEntry.COLUMN_DATE))
                data = cursor.getString(cursor.getColumnIndex(DBContract1.UserEntry.COLUMN_DATA))
                users.add(
                    OrderHistoryModel(
                        date,
                        data
                    )
                )
                Log.e("user", "$users")
                cursor.moveToNext()
            }
        }
        return users
    }

    companion object {
        // If you change the database schema, you must increment the database version.
        val DATABASE_VERSION = 1
        val DATABASE_NAME = "DatabaseReader.db"
        private val SQL_CREATE_ENTRIES =
            "CREATE TABLE " + DBContract1.UserEntry.TABLE_NAME + " (" +
                    DBContract1.UserEntry.COLUMN_DATE + " TEXT," +
                    DBContract1.UserEntry.COLUMN_DATA + " TEXT)"
        private val SQL_DELETE_ENTRIES = "DROP TABLE IF EXISTS " + DBContract1.UserEntry.TABLE_NAME
    }
}
