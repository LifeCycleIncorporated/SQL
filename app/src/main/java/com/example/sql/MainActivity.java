package com.example.sql;



import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText nameEditText, ageEditText, genderEditText, idEditText;
    private Button saveButton, showButton, update, delete;

    private int color;
    DataBaseHelper dataBaseHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dataBaseHelper = new DataBaseHelper(this);
        SQLiteDatabase sqLiteDatabase = dataBaseHelper.getWritableDatabase();


        nameEditText=findViewById(R.id.nameEditTextId);
        ageEditText=findViewById(R.id.ageEditTextId);
        genderEditText=findViewById(R.id.genderEditTextId);
        idEditText=findViewById(R.id.idEditTextId);
        saveButton=findViewById(R.id.saveButtonId);
        showButton=findViewById(R.id.showButtonId);
        update=findViewById(R.id.updateButtonId);
        delete=findViewById(R.id.deleteButtonId);

        saveButton.setOnClickListener(this);
        showButton.setOnClickListener(this);
        update.setOnClickListener(this);
        delete.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        String name = nameEditText.getText().toString();
        String age = ageEditText.getText().toString();
        String gender = genderEditText.getText().toString();
        String id = idEditText.getText().toString();






        if (view.getId()==R.id.saveButtonId)
        {
            Toast.makeText(getApplicationContext(),"Row is not inserted",Toast.LENGTH_LONG).show();


            long rowId = dataBaseHelper.insertData(name,age,gender);
                if (rowId>100)
                {
                    Toast.makeText(getApplicationContext(),"Row "+rowId+" is successful insertData",Toast.LENGTH_LONG).show();
                }



        }


        if (view.getId()==R.id.showButtonId)
        {
            Cursor cursor = dataBaseHelper.showAllData();
            if (cursor.getCount()==0)
            {
                // Data is empty
                showData("Error","Wrong");
                return;
            }
            StringBuffer stringBuffer = new StringBuffer();
            while (cursor.moveToNext())
            {
                stringBuffer.append("ID        :   "+cursor.getString(0)+"\n");
                stringBuffer.append("Name   :   "+cursor.getString(1)+"\n");
                stringBuffer.append("Age      :   "+cursor.getString(2)+"\n");
                stringBuffer.append("Gender :   "+cursor.getString(3)+"\n\n\n");
            }
            showData("Result",stringBuffer.toString());
        }


        if (view.getId()==R.id.updateButtonId)
        {
            boolean isUpdated = dataBaseHelper.updateData(id,name,age,gender);

            if (isUpdated==true)
            {
                Toast.makeText(getApplicationContext(), "Update is successful",Toast.LENGTH_SHORT).show();
            }
        }

        if (view.getId()==R.id.deleteButtonId)
        {
            int value = dataBaseHelper.deleteData(id);
            if (value>0)
            {
                Toast.makeText(getApplicationContext(),"Data is deleted",Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(getApplicationContext(),"Invalid Id",Toast.LENGTH_SHORT).show();
            }
        }
    }

    public void showData (String title, String Message)
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(title);
        builder.setMessage(Message);
        builder.setCancelable(true);
        builder.show();
    }
}