package com.duy.mobileapp;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.AlarmManager;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.google.firebase.database.*;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {
    DatabaseReference database;
    ArrayList<Task> tasks;
    TaskAdapter adapter;
    String selectedTime = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Tạo channel cho notification (Android 8+)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(
                    "taskChannel",
                    "Task Reminders",
                    NotificationManager.IMPORTANCE_HIGH
            );
            NotificationManager manager = getSystemService(NotificationManager.class);
            manager.createNotificationChannel(channel);
        }

        // Xin quyền POST_NOTIFICATIONS (Android 13+)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            ActivityCompat.requestPermissions(
                    this,
                    new String[]{Manifest.permission.POST_NOTIFICATIONS},
                    1
            );
        }

        EditText editText = findViewById(R.id.editTextTask);
        Button buttonAdd = findViewById(R.id.buttonAdd);
        Button buttonPickTime = findViewById(R.id.buttonPickTime);
        ListView listView = findViewById(R.id.listViewTasks);

        database = FirebaseDatabase.getInstance().getReference("tasks");
        tasks = new ArrayList<>();
        adapter = new TaskAdapter(this, tasks);
        listView.setAdapter(adapter);

        // Nút chọn giờ
        buttonPickTime.setOnClickListener(v -> {
            Calendar calendar = Calendar.getInstance();
            int hour = calendar.get(Calendar.HOUR_OF_DAY);
            int minute = calendar.get(Calendar.MINUTE);

            TimePickerDialog timePicker = new TimePickerDialog(
                    MainActivity.this,
                    (view, hourOfDay, minuteOfHour) -> {
                        selectedTime = String.format(Locale.getDefault(), "%02d:%02d", hourOfDay, minuteOfHour);
                        buttonPickTime.setText("Giờ: " + selectedTime);
                    },
                    hour,
                    minute,
                    true
            );
            timePicker.show();
        });

        // Nút thêm công việc
        buttonAdd.setOnClickListener(v -> {
            String taskText = editText.getText().toString();
            if (taskText.isEmpty()) {
                Toast.makeText(this, "Nhập nội dung công việc", Toast.LENGTH_SHORT).show();
                return;
            }
            if (selectedTime.isEmpty()) {
                Toast.makeText(this, "Bạn phải chọn giờ trước!", Toast.LENGTH_SHORT).show();
                return;
            }

            Task taskObj = new Task();
            taskObj.text = taskText;
            taskObj.time = selectedTime;
            taskObj.done = false; // mặc định chưa hoàn thành

            // Tạo node mới trong Firebase và gán id
            DatabaseReference newTaskRef = database.push();
            taskObj.id = newTaskRef.getKey();

            newTaskRef.setValue(taskObj)
                    .addOnSuccessListener(aVoid -> {
                        Log.d("MainActivity", "Task saved: " + taskText);
                        setReminder(taskObj); // đặt lịch nhắc
                    })
                    .addOnFailureListener(e -> Log.e("MainActivity", "Error: " + e.getMessage()));

            editText.setText("");
            buttonPickTime.setText("Chọn giờ");
            selectedTime = "";
        });

        // Lắng nghe dữ liệu từ Firebase
        database.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                tasks.clear();
                for (DataSnapshot taskSnapshot : snapshot.getChildren()) {
                    Task task = taskSnapshot.getValue(Task.class);
                    if (task != null) {
                        task.id = taskSnapshot.getKey();
                        tasks.add(task);
                    }
                }

                // Sắp xếp: chưa hoàn thành lên trước, đã hoàn thành xuống cuối
                tasks.sort((t1, t2) -> {
                    if (t1.done != t2.done) {
                        return t1.done ? 1 : -1;
                    }
                    return t1.time.compareTo(t2.time);
                });

                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.e("MainActivity", "Firebase error: " + error.getMessage());
            }
        });

        // Xóa bằng cách click vào item (ngoài nút Xóa trong adapter)
        listView.setOnItemClickListener((parent, view, position, id) -> {
            Task task = tasks.get(position);
            if (task.id != null) {
                database.child(task.id).removeValue()
                        .addOnSuccessListener(aVoid -> Log.d("MainActivity", "Task deleted: " + task.text))
                        .addOnFailureListener(e -> Log.e("MainActivity", "Delete error: " + e.getMessage()));
            }
        });
    }

    // Hàm đặt lịch nhắc
    @SuppressLint("ScheduleExactAlarm")
    private void setReminder(Task task) {
        try {
            String[] parts = task.time.split(":");
            int hour = Integer.parseInt(parts[0]);
            int minute = Integer.parseInt(parts[1]);

            Calendar calendar = Calendar.getInstance();
            calendar.set(Calendar.HOUR_OF_DAY, hour);
            calendar.set(Calendar.MINUTE, minute);
            calendar.set(Calendar.SECOND, 0);

            long triggerAtMillis = calendar.getTimeInMillis();

            // Nếu giờ đã qua, cộng thêm 1 ngày để tránh lỗi
            if (triggerAtMillis < System.currentTimeMillis()) {
                calendar.add(Calendar.DAY_OF_YEAR, 1);
                triggerAtMillis = calendar.getTimeInMillis();
            }

            Intent intent = new Intent(this, ReminderReceiver.class);
            intent.putExtra("taskText", task.text);

            PendingIntent pendingIntent = PendingIntent.getBroadcast(
                    this,
                    task.id.hashCode(),
                    intent,
                    PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE
            );

            AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
            alarmManager.setExact(AlarmManager.RTC_WAKEUP, triggerAtMillis, pendingIntent);
        } catch (Exception e) {
            Log.e("MainActivity", "Reminder error: " + e.getMessage());
        }
    }
}
