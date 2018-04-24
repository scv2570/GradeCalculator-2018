package com.example.scv2570.gradecalculator;

import android.provider.BaseColumns;

/**
 * Contract class for the grade database
 * contains database related constants
 * Created by svetr on 4/19/2018.
 */

public class gradeContract {
    public gradeContract(){

    }
    // name of the database in SQLite
    public static final String DATABASE_NAME = "Class Sections";

    // this number should change if the database schema (design) changes
    public static final int DATABASE_VERSION = 1;

    // Inner class defines the table contents
    public static abstract class Section implements BaseColumns {
        public static final String TABLE_NAME = "class_sections";
        public static final String COLUMN_NAME_ID = "_id";
        public static final String COLUMN_NAME_CLASS_NAME = "class_name";
        public static final String COLUMN_NAME_SECTION_PERCENTAGE = "section_percentage";
        public static final String COLUMN_NAME_STUDENT_PERCENTAGE = "student_percentage";
    }

}
