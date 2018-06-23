package com.example.peterson.todoapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class SecondActivity extends AppCompatActivity {

    EditText edtUpdate;
    Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        edtUpdate = (EditText) findViewById(R.id.edtUpdate);
        button = (Button) findViewById(R.id.btnUpdateSave);

        String receive = getIntent().getStringExtra("passingEdit");
//        int num = getIntent().getIntExtra("");

        edtUpdate.setText(receive);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent data = new Intent();
                data.putExtra("return", edtUpdate.getText().toString());
                setResult(RESULT_OK, data);
                finish();
            }
        });

    }


}
