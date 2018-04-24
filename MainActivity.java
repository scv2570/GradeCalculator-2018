package com.example.scv2570.gradecalculator;



import android.app.Activity;
import android.app.ListActivity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CursorAdapter;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

public class MainActivity extends Activity {
    public static final String ROW_ID = "row_id"; // Intent extra key

    private CursorAdapter adapter;                // adapter for ListView
    private GradeDbHelper dbHelper;               // manages database
    private SQLiteDatabase db;                    // the database object


@Override
protected  void  onCreate(final Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.content_main);
}
public void setButton(View view){
    Button button= (Button) findViewById(R.id.addClass);
    button.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent(MainActivity.this,AddSection.class);
            startActivity(intent);
        }
    });

}

    @Override
    protected void onStart() {
        super.onStart(); // call super's onResume method

        // setup the database helper
        dbHelper = new GradeDbHelper(this);

        // opens (creates and/or updates, as required) the database
        db = dbHelper.getWritableDatabase();

    }
    // performs database query outside GUI thread



    // handle choice from options menu
    public boolean onAddSectionButton() {

                // create a new Intent to launch the AddSection Activity
                Intent addNewSection = new Intent(MainActivity.this, AddSection.class);
                startActivity(addNewSection);               // start the AddEditMovie Activity
                return true;
        }
    }





