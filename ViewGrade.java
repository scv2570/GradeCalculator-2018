package com.example.scv2570.gradecalculator;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;

/**
 * Created by svetr on 4/24/2018.
 */

public class ViewGrade extends Activity {
    private long classSectionID;                 // selected sections id
    private TextView sectionNameTextView;        // displays section name
    private TextView sectionWorthTextView;     // displays section worth
    private TextView sectionGradeTextView;       // displays section grade

    private SQLiteDatabase db;          // the database object

    // called when the activity is first created
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_grade);

        // get the EditTexts
        sectionNameTextView= (TextView) findViewById(R.id.sectionNameTextView);
        sectionWorthTextView = (TextView) findViewById(R.id.sectionWorthTextView);
        sectionGradeTextView = (TextView) findViewById(R.id.sectionGradeTextView);

        // get the selected movie's row ID
        Bundle extras = getIntent().getExtras();
        classSectionID = extras.getLong(MainActivity.ROW_ID);
        // setup the database helper
        GradeDbHelper dbHelper = new GradeDbHelper(this);

        // opens (creates and/or updates, as required) the database
        db = dbHelper.getWritableDatabase();
    }

    // called when the activity is first created
    @Override
    protected void onResume() {
        super.onResume();

        // create new LoadMovieTask and execute it
        new LoadGradeTask().execute(classSectionID);
    }

    // performs database query outside GUI thread
    private class LoadGradeTask extends AsyncTask<Long, Object, Cursor> {

        // perform the database access
        @Override
        protected Cursor doInBackground(Long... params) {
            // get a cursor containing all data on given entry
            return db.query(gradeContract.Section.TABLE_NAME, null,
                    gradeContract.Section._ID + "=" + params[0], null, null, null, null);
        }

        // use the Cursor returned from the doInBackground method
        @Override
        protected void onPostExecute(Cursor result) {
            super.onPostExecute(result);

            result.moveToFirst(); // move to the first item

            // get the column index for each data item
            int sectionNameIndex =
                    result.getColumnIndex(gradeContract.Section.COLUMN_NAME_CLASS_NAME);
            int sectionWorthIndex =
                    result.getColumnIndex(gradeContract.Section.COLUMN_NAME_SECTION_PERCENTAGE);
            int sectionGradeIndex =
                    result.getColumnIndex(gradeContract.Section.COLUMN_NAME_STUDENT_PERCENTAGE);

            // fill TextViews with the retrieved data
            sectionNameTextView.setText(result.getString(sectionNameIndex));
            sectionWorthTextView.setText(result.getString(sectionWorthIndex));
            sectionGradeTextView.setText(result.getString(sectionGradeIndex));

        }
    }


    // handle choice from options menu
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

                return super.onOptionsItemSelected(item);
    }
    @Override
    protected void onDestroy() {
        db.close();                    // close the database
        super.onDestroy();
    }
}
