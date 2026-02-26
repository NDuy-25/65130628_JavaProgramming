package com.duy.mobileapp;

import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;

import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

public class ReminderReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        String taskText = intent.getStringExtra("taskText");

        // Tạo notification với âm thanh, rung, đèn giống báo thức
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, "taskChannel")
                .setSmallIcon(R.drawable.ic_launcher_foreground) // icon mặc định
                .setContentTitle("Nhắc việc")
                .setContentText(taskText)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setDefaults(NotificationCompat.DEFAULT_ALL) // bật âm thanh, rung, đèn
                .setAutoCancel(true);

        NotificationManagerCompat manager = NotificationManagerCompat.from(context);

        // Kiểm tra quyền POST_NOTIFICATIONS (Android 13+)
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.POST_NOTIFICATIONS)
                != PackageManager.PERMISSION_GRANTED) {
            return; // nếu chưa cấp quyền thì không hiển thị
        }

        manager.notify((int) System.currentTimeMillis(), builder.build());
    }
}
