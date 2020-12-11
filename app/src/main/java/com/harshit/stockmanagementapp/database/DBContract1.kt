package com.harshit.stockmanagementapp.database

import android.provider.BaseColumns

object DBContract1 {
    class UserEntry : BaseColumns {
        companion object {
            val TABLE_NAME = "users"
            val COLUMN_DATE = "date"
            val COLUMN_DATA = "data"
        }
    }
}