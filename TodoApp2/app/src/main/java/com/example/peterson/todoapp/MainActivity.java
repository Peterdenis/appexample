package com.example.peterson.todoapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;

public class MainActivity extends AppCompatActivity {

    ArrayList<String> todoItems;
    ArrayAdapter<String> arrToDoAdapter;
    ListView lvItems;
    private EditText editText;
    private final int REQUEST_CODE = 20;
//    int selectedPosition = -1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        populateListViewItems();
        lvItems = (ListView) findViewById(R.id.lvItems);
        lvItems.setAdapter(arrToDoAdapter);
        editText = (EditText) findViewById(R.id.edtTextItem);

        removeListViewListener();

        lvItems.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //Get Position clicked on the list
                String gettingTheValue = (String) lvItems.getItemAtPosition(position);
//                int pos = lvItems.getId();
                Intent intent = new Intent(MainActivity.this, SecondActivity.class);
                intent.putExtra("passingEdit",gettingTheValue);
//                intent.putExtra("val", pos);
                startActivityForResult(intent, REQUEST_CODE);
            }
        });

//        //removing duplicate task to do
//        HashSet<String> hashSet = new HashSet<String>();
//        hashSet.addAll(todoItems);
//        todoItems.clear(); // mwen pap clear li map pito bloke enregistreman poum di moun nan ke valeur sa sezi deja nan yon toast
//        todoItems.addAll(hashSet);
//
//        //Alphabetic Sorting
//        Collections.sort(todoItems);


        //Receive the update of the second view



    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK && requestCode == REQUEST_CODE) {
            String name = data.getExtras().getString("return");
//            todoItems.set(selectedPosition, name);
//            todoItems.remove()
            todoItems.add(name);
            arrToDoAdapter.notifyDataSetChanged();
            Toast.makeText(this, name, Toast.LENGTH_LONG).show();
            writeItems();
        }
    }

    public void populateListViewItems() {
        readItems();
        arrToDoAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, todoItems);
    }

    private void removeListViewListener() {
        lvItems.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapter, View item, int pos, long id) {
                todoItems.remove(pos);
                arrToDoAdapter.notifyDataSetChanged(); // has adapter look back at the array list and refresh it's data and repopulate the view
                writeItems();
                return true;
            }
        });
    }


    private void readItems() {
        File filesDir = getFilesDir();
        File file = new File(filesDir, "todo.txt");
        try {
            todoItems = new ArrayList(FileUtils.readLines(file));
       } catch (FileNotFoundException e) {
            todoItems = new ArrayList();
        } catch (IOException e) {

        }
    }


    private void writeItems() {
        File filesDir = getFilesDir();
        File file = new File(filesDir, "todo.txt");
        try {
            FileUtils.writeLines(file, todoItems);
        } catch (IOException e) {

        }
    }

    public void onClickAdd(View view) {
        arrToDoAdapter.add(editText.getText().toString());
        editText.setText("");
        writeItems();
    }
}
