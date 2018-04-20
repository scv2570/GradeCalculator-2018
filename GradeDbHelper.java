package com.example.scv2570.gradecalculator;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * SQL Helper class
 * Created by svetr on 4/19/2018.
 */

public class GradeDbHelper extends SQLiteOpenHelper {

        private static final String TEXT_TYPE = " TEXT";  // SQL data type

        private static final String COMMA_SEP = ",";      // SQL comma

        // SQL command to create database
        private static final String SQL_CREATE_ENTRIES =
                "CREATE TABLE " + gradeContract.Section.TABLE_NAME + " (" +
                        gradeContract.Section._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                        gradeContract.Section.COLUMN_NAME_CLASS_NAME + TEXT_TYPE + COMMA_SEP +
                        gradeContract.Section.COLUMN_NAME_SECTION_PERCENTAGE + TEXT_TYPE + COMMA_SEP +
                        gradeContract.Section.COLUMN_NAME_STUDENT_PERCENTAGE+ TEXT_TYPE + " );";

        // SQL command to delete database
        private static final String SQL_DELETE_ENTRIES =
                "DROP TABLE IF EXISTS " + gradeContract.Section.TABLE_NAME;

        /**
         * all purpose constructor
         *
         * @param context app context
         */
        public GradeDbHelper(Context context) {
            super(context, gradeContract.DATABASE_NAME, null,
                    gradeContract.DATABASE_VERSION);
        }

        /**
         * creates database when it doesn't exist
         *
         * @param db the database
         */
        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL(SQL_CREATE_ENTRIES);
        }

        /**
         * called when DATABASE_VERSION (above) is bigger than the version in the db
         * brain dead update routine: deletes everything, then create new db
         *
         * @param db         the database
         * @param oldVersion what's in the db: something less than DATABASE_VERSION
         * @param newVersion new version number e.g. DATABASE_VERSION
         */
        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL(SQL_DELETE_ENTRIES);
            onCreate(db);
        }


        /**
         * called if DATABASE_VERSION is less than what's in the database
         *
         * @param db         the database
         * @param oldVersion what's in the db: something more than DATABASE_VERSION
         * @param newVersion probably DATABASE_VERSION
         */
        public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            onUpgrade(db, oldVersion, newVersion);
        }
    }

