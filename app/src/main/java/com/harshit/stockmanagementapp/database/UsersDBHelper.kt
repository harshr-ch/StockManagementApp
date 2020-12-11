package com.harshit.stockmanagementapp.database

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteConstraintException
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteException
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log
import com.harshit.stockmanagementapp.model.UserModel

class UsersDBHelper(context: Context) : SQLiteOpenHelper(context,
    DATABASE_NAME, null,
    DATABASE_VERSION
) {
    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL(SQL_CREATE_ENTRIES)
    }
    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL(SQL_DELETE_ENTRIES)
        onCreate(db)
    }
    override fun onDowngrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        onUpgrade(db, oldVersion, newVersion)
    }

    @Throws(SQLiteConstraintException::class)
    fun insertUser(user: UserModel): Boolean {
        val db = writableDatabase
        val values = ContentValues()
        values.put(DBContract.UserEntry.COLUMN_USER_ID, user.userid)
        values.put(DBContract.UserEntry.COLUMN_NAME, user.name)
        values.put(DBContract.UserEntry.COLUMN_QUANTITY, user.quantity)
        values.put(DBContract.UserEntry.COLUMN_PRICE, user.price)
        values.put(DBContract.UserEntry.COLUMN_IMAGE, user.image)
        val newRowId = db.insert(DBContract.UserEntry.TABLE_NAME, null, values)
        return true
    }

    @Throws(SQLiteConstraintException::class)
    fun deleteUser(name: String): Boolean {
        val db = writableDatabase
        val selection = DBContract.UserEntry.COLUMN_NAME + " LIKE ?"
        val selectionArgs = arrayOf(name)
        db.delete(DBContract.UserEntry.TABLE_NAME, selection, selectionArgs)
        return true
    }

    @Throws(SQLiteConstraintException::class)
    fun update(user: String,user1 : String) : Boolean {
        val db=writableDatabase

        var cursor:Cursor?=null
        var x = 0
        cursor = db.rawQuery("select * from " + DBContract.UserEntry.TABLE_NAME + " WHERE " + DBContract.UserEntry.COLUMN_NAME + "='" + user + "'", null)
        if (cursor!!.moveToFirst())
            x = cursor.getString(cursor.getColumnIndex(DBContract.UserEntry.COLUMN_QUANTITY))!!.toInt()
        x -= user1.toInt()

        val cv = ContentValues()
        cv.put(DBContract.UserEntry.COLUMN_QUANTITY,x)

        db.update(
            DBContract.UserEntry.TABLE_NAME,cv,
            DBContract.UserEntry.COLUMN_NAME + "=?", arrayOf(user))
        return true;
    }

    @Throws(SQLiteConstraintException::class)
    fun update1(user: String,user1 : String) : Boolean {
        val db=writableDatabase

        var cursor:Cursor?=null
        var x = 0
        cursor = db.rawQuery("select * from " + DBContract.UserEntry.TABLE_NAME + " WHERE " + DBContract.UserEntry.COLUMN_NAME + "='" + user + "'", null)
        if (cursor!!.moveToFirst())
            x = cursor.getString(cursor.getColumnIndex(DBContract.UserEntry.COLUMN_QUANTITY))!!.toInt()
        x += user1.toInt()

        val cv = ContentValues()
        cv.put(DBContract.UserEntry.COLUMN_QUANTITY,x)

        db.update(
            DBContract.UserEntry.TABLE_NAME,cv,
            DBContract.UserEntry.COLUMN_NAME + "=?", arrayOf(user))
        return true;
    }

    fun readUser(name: String): ArrayList<UserModel> {
        val users = ArrayList<UserModel>()
        val db = writableDatabase
        var cursor: Cursor? = null
        //try {
            cursor = db.rawQuery("select * from " + DBContract.UserEntry.TABLE_NAME + " WHERE " + DBContract.UserEntry.COLUMN_NAME + "='" + name + "'", null)
        //} //catch (e: SQLiteException) {
// if table not yet present, create it
            //db.execSQL(SQL_CREATE_ENTRIES)
            //return ArrayList()
        //}
        val userid: String
        val quantity: String
        val price : String
        val image : ByteArray
        if (cursor!!.moveToFirst()) {
                userid = cursor.getString(cursor.getColumnIndex(DBContract.UserEntry.COLUMN_USER_ID))
                quantity = cursor.getString(cursor.getColumnIndex(DBContract.UserEntry.COLUMN_QUANTITY))
                price = cursor.getString(cursor.getColumnIndex(DBContract.UserEntry.COLUMN_PRICE))
                image = cursor.getBlob(cursor.getColumnIndex(DBContract.UserEntry.COLUMN_IMAGE))
                users.add(
                    UserModel(
                        userid,
                        name,
                        quantity,
                        price,
                        image
                    )
                )
                cursor.moveToNext()
        }
        return users
    }

    fun readAllUsers(): ArrayList<UserModel> {
        val users = ArrayList<UserModel>()
        val db = writableDatabase
        var cursor: Cursor? = null
        try {
            cursor = db.rawQuery("select * from " + DBContract.UserEntry.TABLE_NAME, null)
        } catch (e: SQLiteException) {
            db.execSQL(SQL_CREATE_ENTRIES)
            db.execSQL(SQL_CREATE_ENTRIES)
            db.execSQL(SQL_CREATE_ENTRIES)
            return ArrayList()
        }
        var userid: String
        var name: String
        var quantity: String
        var price: String
        var image: ByteArray
        if (cursor!!.moveToFirst()) {
            while (cursor.isAfterLast == false) {
                userid = cursor.getString(cursor.getColumnIndex(DBContract.UserEntry.COLUMN_USER_ID))
                name = cursor.getString(cursor.getColumnIndex(DBContract.UserEntry.COLUMN_NAME))
                quantity = cursor.getString(cursor.getColumnIndex(DBContract.UserEntry.COLUMN_QUANTITY))
                price = cursor.getString(cursor.getColumnIndex(DBContract.UserEntry.COLUMN_PRICE))
                image = cursor.getBlob(cursor.getColumnIndex(DBContract.UserEntry.COLUMN_IMAGE))
                users.add(
                    UserModel(
                        userid,
                        name,
                        quantity,
                        price,
                        image
                    )
                )
                Log.e("user","$users")
                cursor.moveToNext()
            }
        }
        return users
    }
    companion object {
        // If you change the database schema, you must increment the database version.
        val DATABASE_VERSION = 5
        val DATABASE_NAME = "FeedReader3.db"
        private val SQL_CREATE_ENTRIES =
            "CREATE TABLE " + DBContract.UserEntry.TABLE_NAME + " (" +
                    DBContract.UserEntry.COLUMN_USER_ID + " TEXT PRIMARY KEY," +
                    DBContract.UserEntry.COLUMN_NAME + " TEXT," +
                    DBContract.UserEntry.COLUMN_QUANTITY + " TEXT," +
                    DBContract.UserEntry.COLUMN_PRICE + " TEXT," +
                    DBContract.UserEntry.COLUMN_IMAGE + " TEXT)"
        private val SQL_DELETE_ENTRIES = "DROP TABLE IF EXISTS " + DBContract.UserEntry.TABLE_NAME
    }
}
