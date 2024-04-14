package com.example.pictsearch.logic

import android.Manifest
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.example.pictsearch.R

class NotificationReceiver : BroadcastReceiver() {

    companion object {
        private const val NOTIFICATION_ID = 1
        private const val CHANNEL_ID = "daily_notification"
    }

    override fun onReceive(context: Context?, intent: Intent?) {
        if (intent?.action == "SEND_NOTIFICATION") {
            showNotification(context)
        }
    }

    private fun showNotification(context: Context?) {
        val builder = NotificationCompat.Builder(context!!, CHANNEL_ID)
            .setSmallIcon(R.drawable.logo)
            .setContentTitle("Ежедневное уведомление")
            .setContentText("Проверьте новые обновления!")
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)

        with(NotificationManagerCompat.from(context)) {
            if (ActivityCompat.checkSelfPermission(
                    context,
                    Manifest.permission.POST_NOTIFICATIONS
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.
                return
            }
            notify(NOTIFICATION_ID, builder.build())
        }
    }
}
