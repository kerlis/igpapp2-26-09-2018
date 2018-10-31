package sistemasfireg.igp.org.sismosperu
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.media.RingtoneManager
import android.net.Uri
import android.os.Build
import android.support.v4.app.NotificationCompat
import android.util.Log
import com.google.firebase.messaging.FirebaseMessaging
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import sistemasfireg.igp.org.sismosperu.R
import sistemasfireg.igp.org.sismosperu.Ultimosismo3
import java.util.*

class Messagingservicef : FirebaseMessagingService() {

    companion object {
        private val TAG = Messagingservicef::class.java.simpleName
    }



    override fun onMessageReceived(remoteMessage: RemoteMessage?) {
        sendNotification()
    }



    private fun sendNotification() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val intent = Intent(this, Ultimosismo3::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
          //  intent.putExtra("NOTIFICACIONDATA", remoteMessage)
            val pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_ONE_SHOT)
            val channelId = getString(R.string.default_notification_channel_id_lahar)
            val channelName = getString(R.string.default_notification_channel_name_lahar)
            val notificationBuilder = NotificationCompat.Builder(this, channelId)
                    .setSmallIcon(R.mipmap.ic_launcher)
                    .setContentTitle("Alerta de Lahar")
                    .setContentText(" fdhgfdsgfdh")
                    .setAutoCancel(true)
                    .setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION))
                    .setContentIntent(pendingIntent)
            val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                val channel = NotificationChannel(channelId, channelName, NotificationManager.IMPORTANCE_HIGH)
                notificationManager.createNotificationChannel(channel)
            }
            val random = Random()
            notificationManager.notify(random.nextInt(100), notificationBuilder.build())
        }
        else {
            val intent = Intent(this, Ultimosismo3::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            val pendingIntent = PendingIntent.getActivity(this, 0 /* Request code */, intent,
                    PendingIntent.FLAG_ONE_SHOT)

            val icon = BitmapFactory.decodeResource(this.resources, R.drawable.logo)

            val defaultSoundUri = Uri.parse("android.resource://" + packageName + "/" + R.raw.beep2)

            val notificationBuilder = NotificationCompat.Builder(this)
                    .setSmallIcon(R.mipmap.ic_launcher)
                    .setContentTitle("sismo")
                    .setContentText("sismo")
                    .setAutoCancel(true)
                    .setSound(defaultSoundUri)
                    .setContentIntent(pendingIntent)
            val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

            notificationManager.notify(0 /* ID of notification */, notificationBuilder.build())


        }
    }


}



