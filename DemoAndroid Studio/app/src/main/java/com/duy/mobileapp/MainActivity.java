package com.duy.mobileapp;

import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import androidx.appcompat.app.AppCompatActivity;
import com.google.firebase.database.*;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {
    DatabaseReference database;
    ArrayList<Task> tasks;
    TaskAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        EditText editText = findViewById(R.id.editTextTask);
        Button button = findViewById(R.id.buttonAdd);
        ListView listView = findViewById(R.id.listViewTasks);

        database = FirebaseDatabase.getInstance().getReference("tasks");
        tasks = new ArrayList<>();
        adapter = new TaskAdapter(this, tasks);
        listView.setAdapter(adapter);

        // Thêm công việc mới
        button.setOnClickListener(v -> {
            String taskText = editText.getText().toString();
            if (!taskText.isEmpty()) {
                String timestamp = new SimpleDateFormat("HH:mm dd/MM/yyyy", Locale.getDefault()).format(new Date());
                Task taskObj = new Task();
                taskObj.text = taskText;
                taskObj.time = timestamp;
                database.push().setValue(taskObj)
                        .addOnSuccessListener(aVoid -> Log.d("MainActivity", "Task saved: " + taskText))
                        .addOnFailureListener(e -> Log.e("MainActivity", "Error: " + e.getMessage()));
                editText.setText("");
            }
        });

        // Lắng nghe dữ liệu từ Firebase
        database.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                tasks.clear();
                for (DataSnapshot taskSnapshot : snapshot.getChildren()) {
                    Task task = taskSnapshot.getValue(Task.class);
                    if (task != null) {
                        task.id = taskSnapshot.getKey(); // lưu key để xóa
                        tasks.add(task);
                    }
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError error) {
                Log.e("MainActivity", "Firebase error: " + error.getMessage());
            }
        });

        // Nếu muốn xóa bằng cách click vào item (ngoài nút Xóa trong adapter)
        listView.setOnItemClickListener((parent, view, position, id) -> {
            Task task = tasks.get(position);
            if (task.id != null) {
                database.child(task.id).removeValue()
                        .addOnSuccessListener(aVoid -> Log.d("MainActivity", "Task deleted: " + task.text))
                        .addOnFailureListener(e -> Log.e("MainActivity", "Delete error: " + e.getMessage()));
            }
        });
    }
}
