package com.duy.mobileapp;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
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
        CheckBox checkDone = convertView.findViewById(R.id.checkDone);

        textView.setText(task.text);
        timeView.setText(task.time);

        // Reset listener trước khi setChecked
        checkDone.setOnCheckedChangeListener(null);
        checkDone.setChecked(task.done);

        // Hiển thị trạng thái
        if (task.done) {
            textView.setTextColor(Color.GRAY);
            textView.setPaintFlags(textView.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
        } else {
            textView.setTextColor(Color.BLACK);
            textView.setPaintFlags(textView.getPaintFlags() & (~Paint.STRIKE_THRU_TEXT_FLAG));
        }

        // Listener sau khi setChecked
        checkDone.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (task.id != null) {
                FirebaseDatabase.getInstance().getReference("tasks")
                        .child(task.id).child("done").setValue(isChecked);
            }
        });

        // Nút Xóa
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
