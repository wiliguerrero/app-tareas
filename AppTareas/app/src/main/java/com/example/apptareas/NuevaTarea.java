package com.example.apptareas;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.weiwangcn.betterspinner.library.material.MaterialBetterSpinner;

import net.llira.taskfirebase.models.Task;

import java.util.UUID;

public class NewTaskActivity extends AppCompatActivity {
    private EditText txtTaskKey, txtDescription, txtDate;
    private MaterialBetterSpinner spnPriorities, spnStatus;
    private Button btnSave;
    FirebaseDatabase fd;
    DatabaseReference dr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_task);

        //Inflate
        txtTaskKey = findViewById(R.id.txt_task_key);
        txtDescription = findViewById(R.id.txt_description);
        txtDate = findViewById(R.id.txt_date);
        btnSave = findViewById(R.id.btn_save);
        spnPriorities = findViewById(R.id.spn_priorities);
        spnStatus = findViewById(R.id.spn_status);

        //Combo
        ArrayAdapter<CharSequence> arrayPriority = ArrayAdapter.createFromResource(this,
                R.array.cmbPriorities, android.R.layout.simple_dropdown_item_1line);
        spnPriorities.setAdapter(arrayPriority);
        ArrayAdapter<CharSequence> arrayStatus = ArrayAdapter.createFromResource(this,
                R.array.cmbStatus, android.R.layout.simple_dropdown_item_1line);
        spnStatus.setAdapter(arrayStatus);
        startFirebase();

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Task t = new Task();
                t.setTaskId(UUID.randomUUID().toString());
                t.setTaskKey(txtTaskKey.getText().toString());
                t.setDescription(txtDescription.getText().toString());
                t.setPriority(spnPriorities.getText().toString());
                t.setDate(txtDate.getText().toString());
                t.setStatus(spnStatus.getText().toString());
                dr.child("Task").child(t.getTaskId()).setValue(t);
                Toast.makeText(getApplicationContext(), "Saved!", Toast.LENGTH_LONG).show();
                clean();
                System.exit(0);
            }
        });
    }

    private void startFirebase() {
        FirebaseApp.initializeApp(this);
        fd = FirebaseDatabase.getInstance();
        dr = fd.getReference();
    }

    public void clean() {
        txtTaskKey.setText("");
        txtDescription.setText("");
        spnPriorities.setText("");
        txtDate.setText("");
        spnStatus.setText("");
    }

}
