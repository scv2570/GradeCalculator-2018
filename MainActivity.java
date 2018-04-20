package com.example.scv2570.gradecalculator;

import android.app.AlertDialog;
import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private long classSectionID; // id of the class section

    //editTexts for class info
    private EditText classNameEditText;
    private EditText sectionPercentEditText;
    private EditText studentPercentEditText;

    private SQLiteDatabase db;                    // the database object


    // called when the Activity is first started
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        classNameEditText = (EditText) findViewById(R.id.classNameEditText);
        sectionPercentEditText = (EditText) findViewById(R.id.sectionPercentEditText);
        studentPercentEditText = (EditText) findViewById(R.id.studentPercentEditText);

        Bundle extras = getIntent().getExtras(); // get Bundle of extras
        if (extras != null) {
            classSectionID = extras.getLong("class_section_id");
            classNameEditText.setText(extras.getString("class_name"));
            sectionPercentEditText.setText(extras.getString("section_percent"));
            studentPercentEditText.setText(extras.getString("student_percent"));
        }
        // setup the database helper
        GradeDbHelper dbHelper = new GradeDbHelper(this);

        // opens (creates and/or updates, as required) the database
        db = dbHelper.getWritableDatabase();

        Button saveSectionButton = (Button) findViewById(R.id.saveSection);
            saveSectionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (classNameEditText.getText().length() != 0 &&
                        sectionPercentEditText.getText().length() != 0
                        && studentPercentEditText.getText().length() != 0) {
                    AsyncTask<Object, Object, Object> saveSection =
                            new AsyncTask<Object, Object, Object>() {
                                @Override
                                protected Object doInBackground(Object... params) {
                                    saveSection(); // save section to the database
                                    return null;
                                }

                                @Override
                                protected void onPostExecute(Object result) {
                                    finish(); // return to the previous Activity
                                }
                            };

                    // save the section to the database using a separate thread
                    saveSection.execute((Object[]) null);
                } else {
                    // create a new AlertDialog Builder
                    AlertDialog.Builder builder =
                            new AlertDialog.Builder(MainActivity.this);

                    // set dialog title & message, and provide Button to dismiss
                    builder.setTitle(R.string.errorTitle);
                    builder.setMessage(R.string.errorMessage);
                    builder.setPositiveButton(R.string.errorButton, null);
                    builder.show(); // display the Dialog
                }
            }
        });
    }

    private void saveSection() {
        // the values to insert or update for the section
        ContentValues values = new ContentValues();

        values.put(gradeContract.Section.COLUMN_NAME_CLASS_NAME,
                classNameEditText.getText().toString());
        values.put(gradeContract.Section.COLUMN_NAME_SECTION_PERCENTAGE,
                sectionPercentEditText.getText().toString());
        values.put(gradeContract.Section.COLUMN_NAME_STUDENT_PERCENTAGE,
                studentPercentEditText.getText().toString());


        if (getIntent().getExtras() == null) {
            db.insert(gradeContract.Section.TABLE_NAME, null, values);
        } else {
            db.update(gradeContract.Section.TABLE_NAME, values,
                    gradeContract.Section._ID + "=" + classSectionID, null);
        }
    }
}