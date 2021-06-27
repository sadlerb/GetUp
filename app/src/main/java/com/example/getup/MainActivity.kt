    package com.example.getup

    import android.app.NotificationChannel
    import android.app.NotificationManager
    import android.content.Context
    import android.os.Build
    import android.os.Bundle
    import android.os.Handler
    import android.os.Looper
    import android.view.View
    import android.widget.TextView
    import androidx.appcompat.app.AppCompatActivity
    import androidx.core.app.NotificationCompat
    import androidx.core.app.NotificationManagerCompat
    import com.google.android.material.floatingactionbutton.FloatingActionButton
    import java.util.*

    class MainActivity : AppCompatActivity(),CustomDialogFragment.OnInputListener{
        private val dialogFragment = CustomDialogFragment()
        private val CHANNEL_ID = "channel_id_example"
        private val notificationId = 0

        override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
            setContentView(R.layout.activity_main)
            val createViewButton: FloatingActionButton = findViewById(R.id.createReminder)
            createViewButton.setOnClickListener {
                if (dialogFragment.isAdded){
                    return@setOnClickListener
                }
                dialogFragment.show(supportFragmentManager,"Custom_Dialog")
            }
        }

        override fun sendInput(input: Array<String>) {
            setText(input)
        }

        private fun setText(input: Array<String>) {
            val repeat: TextView =  findViewById(R.id.repeat)
            val desc: TextView = findViewById(R.id.description)
            desc.text = input[0]
            repeat.text = input[1]
            startTimer(repeat.text.toString())
            desc.visibility = View.VISIBLE
            repeat.visibility = View.VISIBLE
        }

        private fun startTimer(input: String) {
            var time = input.toLong()
            time *= 60000
            Handler(Looper.getMainLooper()).postDelayed({
                sendNotification()
            },time)
        }

        private fun sendNotification() {
            createNotificationChannel()
            val builder = NotificationCompat.Builder(this,CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_launcher_foreground)
                .setContentText("My Notification")
                .setContentText("Lorem ipsum dolor sit amet, consectetur adipiscing elit. Praesent nec facilisis nulla, eu consequat neque. Duis ultricies iaculis pharetra. Maecenas ornare ac lectus consectetur varius")
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            with(NotificationManagerCompat.from(this)){
                notify(notificationId,builder.build())
            }
        }

        private fun createNotificationChannel() {
            // Create the NotificationChannel, but only on API 26+ because
            // the NotificationChannel class is new and not in the support library
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                val name = "Notification Name"
                val descriptionText = "Notification Description"
                val importance = NotificationManager.IMPORTANCE_DEFAULT
                val channel = NotificationChannel(CHANNEL_ID, name, importance).apply {
                    description = descriptionText
                }
                // Register the channel with the system
                val notificationManager: NotificationManager =
                    getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
                notificationManager.createNotificationChannel(channel)
            }
        }
    }

