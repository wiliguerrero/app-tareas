package com.example.apptareas;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import net.llira.taskfirebase.models.Task;

import java.util.ArrayList;
import java.util.List;

public class ListTaksActivity extends AppCompatActivity {
    private ListView lsvTasks;
    private FloatingActionButton fabNew;
    FirebaseDatabase fd;
    DatabaseReference dr;
    private List<Task> taskList = new ArrayList<Task>();
    ArrayAdapter<Task> taskArrayAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_taks);

        //Inflate
        lsvTasks = findViewById(R.id.lsv_tasks);
        startFirebase();
        listData();

        fabNew = findViewById(R.id.fab_new);
        fabNew.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "Fab here!", Toast.LENGTH_LONG).show();
                Intent iNew = new Intent(getApplicationContext(), NewTaskActivity.class);
                startActivity(iNew);
            }
        });
    }

    private void startFirebase() {
        FirebaseApp.initializeApp(this);
        fd = FirebaseDatabase.getInstance();
        dr = fd.getReference();
    }

    private void listData() {
        dr.child("Task").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                taskList.clear();
                for (DataSnapshot objSnapshot : dataSnapshot.getChildren()) {
                    Task t = objSnapshot.getValue(Task.class);
                    taskList.add(t);
                    taskArrayAdapter = new ArrayAdapter<Task>(ListTaksActivity.this,
                            android.R.layout.simple_list_item_1, taskList);
                    lsvTasks.setAdapter(taskArrayAdapter);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
    }

}
