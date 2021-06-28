    package com.example.getup

    import android.app.NotificationChannel
    import android.app.NotificationManager
    import android.content.Context
    import android.os.Build
    import android.os.Bundle
    import android.view.View
    import android.widget.Button
    import android.widget.TextView
    import androidx.appcompat.app.AppCompatActivity
    import androidx.core.app.NotificationCompat
    import androidx.core.app.NotificationManagerCompat
    import com.google.android.material.floatingactionbutton.FloatingActionButton
    import java.util.*
    import kotlin.concurrent.schedule

    class MainActivity : AppCompatActivity(),CustomDialogFragment.OnInputListener{
        private val dialogFragment = CustomDialogFragment()
        private val CHANNEL_ID = "channel_id_example"
        private val notificationId = 0
        private lateinit var repeat: TextView
        private lateinit var desc: TextView
        private lateinit var notifaction_builder: NotificationCompat.Builder
        private lateinit var timer: TimerTask
        private lateinit var stop: Button

        override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
            setContentView(R.layout.activity_main)
            repeat = findViewById(R.id.repeat)
            desc = findViewById(R.id.description)
            stop = findViewById(R.id.stop_timer)
            val createViewButton: FloatingActionButton = findViewById(R.id.createReminder)
            createViewButton.setOnClickListener {
                if (dialogFragment.isAdded){
                    return@setOnClickListener
                }
                dialogFragment.show(supportFragmentManager,"Custom_Dialog")
            }
            stop.setOnClickListener {
                stopTimer()
                desc.text = ""
                repeat.text = ""
                desc.visibility = View.INVISIBLE
                repeat.visibility = View.INVISIBLE

            }
        }

        override fun sendInput(input: Array<String>) {
            setText(input)
        }

        private fun setText(input: Array<String>) {
            desc.text = input[0]
            repeat.text = input[1]
            startTimer(repeat.text.toString())
            desc.visibility = View.VISIBLE
            repeat.visibility = View.VISIBLE
        }

        private fun startTimer(input: String) {
            createNotificationChannel()
            createNotification()
            var time = input.toLong()
            time *= 60000

            timer = Timer().schedule(time,time){
                sendNotification()
            }
        }

        private fun stopTimer(){
            timer.cancel()

        }

        private fun sendNotification() {
            with(NotificationManagerCompat.from(this)){
                notify(notificationId,notifaction_builder.build())
            }
        }

        private fun createNotification(){
            notifaction_builder = NotificationCompat.Builder(this,CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_launcher_foreground)
                .setContentText("My Notification")
                .setContentText(desc.text)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
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

