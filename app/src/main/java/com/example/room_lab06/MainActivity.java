package com.example.room_lab06;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    ListStudentAdapter adapter;
    ListView lv;
    Button btnAdd;
    Button btnRemove;
    Button btnCancel;
    EditText pltNhap;
    List<Student> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout1);
        AppDatabase db = Room.databaseBuilder(getApplicationContext(),
                AppDatabase.class, "student1")
                .allowMainThreadQueries()
                .build();

        StudentDao studentDao = db.studentDao();
        list= studentDao.getAll();
        lv = findViewById(R.id.lv1);
        adapter= new ListStudentAdapter(this, R.layout.item1, (ArrayList<Student>) list);
        lv.setAdapter(adapter);
        btnAdd= findViewById(R.id.btnAdd);
        btnRemove=findViewById(R.id.btnRemove);
        btnCancel=findViewById(R.id.btnCancel);
        pltNhap= findViewById(R.id.nhap);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Student st= list.get(position);
                btnRemove.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        studentDao.delete(st);
                        lv.setAdapter(new ListStudentAdapter(MainActivity.this,  R.layout.layout1,(ArrayList<Student>) studentDao.getAll()));
                    }
                });

            }
        });


        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name= pltNhap.getText().toString();
                Student st= new Student(name);
                studentDao.addStudent(st);
                lv.setAdapter(new ListStudentAdapter(MainActivity.this,  R.layout.layout1,(ArrayList<Student>) studentDao.getAll()));
            }
        });
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.exit(0);
            }
        });
    }
}