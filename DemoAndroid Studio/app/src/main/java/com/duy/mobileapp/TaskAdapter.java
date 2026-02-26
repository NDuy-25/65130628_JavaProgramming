package com.duy.mobileapp;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class TaskAdapter extends ArrayAdapter<Task> {
    public TaskAdapter(Context context, ArrayList<Task> tasks) {
        super(context, 0, tasks);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Task task = getItem(position);
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.task_item, parent, false);
        }

        TextView textView = convertView.findViewById(R.id.taskText);
        TextView timeView = convertView.findViewById(R.id.taskTime);
        Button deleteBtn = convertView.findViewById(R.id.buttonDelete);

        textView.setText(task.text);
        timeView.setText(task.time);

        // Màu nền xen kẽ
        convertView.setBackgroundColor(position % 2 == 0
                ? Color.parseColor("#E3F2FD") // xanh nhạt
                : Color.parseColor("#FFF9C4")); // vàng nhạt

        // Xử lý nút Xóa
        deleteBtn.setOnClickListener(v -> {
            if (task.id != null) {
                FirebaseDatabase.getInstance().getReference("tasks")
                        .child(task.id).removeValue()
                        .addOnSuccessListener(aVoid -> Log.d("TaskAdapter", "Deleted: " + task.text))
                        .addOnFailureListener(e -> Log.e("TaskAdapter", "Delete error: " + e.getMessage()));
            }
        });

        return convertView;
    }
}
