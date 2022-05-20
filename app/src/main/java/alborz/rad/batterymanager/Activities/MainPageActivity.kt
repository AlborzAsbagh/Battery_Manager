package alborz.rad.batterymanager.Activities

import alborz.rad.batterymanager.R
import alborz.rad.batterymanager.databinding.ActivityMainPageBinding
import alborz.rad.batterymanager.models.BatteryModel
import alborz.rad.batterymanager.utilies.BatteryUsage
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.content.pm.ActivityInfo
import android.graphics.Color
import android.os.BatteryManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.widget.ImageView
import android.widget.Toast
import java.util.*
import java.util.zip.Inflater
import kotlin.concurrent.timerTask
import kotlin.math.roundToInt

class MainPageActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainPageBinding

    var batteryInfo: BroadcastReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {

            val batteryLevel = intent?.getIntExtra(BatteryManager.EXTRA_LEVEL, 0)
            binding.voltageValue.text = (intent?.getIntExtra(BatteryManager.EXTRA_VOLTAGE, 0)
                ?.div(1000)).toString() + " volt"
            binding.techValue.text = intent?.getStringExtra(BatteryManager.EXTRA_TECHNOLOGY)
            if (intent?.getIntExtra(BatteryManager.EXTRA_PLUGGED, 0) != 0) {
                binding.plugValue.setText(R.string.plugged_in)
            } else {
                binding.plugValue.setText(R.string.plugged_out)
            }
            binding.tempValue.text = (intent?.getIntExtra(BatteryManager.EXTRA_TEMPERATURE, 0)
                ?.div(10)).toString() + " °C"

            binding.circularProgressBar.progressMax = 100f
            batteryLevel?.toFloat()
                ?.let { binding.circularProgressBar.setProgressWithAnimation(it) }
            binding.batteryLevelValue.text = batteryLevel.toString() + "%"

            var health = intent?.getIntExtra(BatteryManager.EXTRA_HEALTH, 0)
            when (health) {
                BatteryManager.BATTERY_HEALTH_COLD -> {
                    binding.healthCalc.text = "Your battery is cold , it's ok"
                    binding.healthCalc.setTextColor(Color.GRAY)
                    binding.imgHealth.setImageResource(R.drawable.cold)
                }
                BatteryManager.BATTERY_HEALTH_DEAD -> {
                    binding.healthCalc.text = "Your battery is dead , please change it"
                    binding.healthCalc.setTextColor(Color.parseColor("#000000"))
                    binding.imgHealth.setImageResource(R.drawable.dead)
                }
                BatteryManager.BATTERY_HEALTH_GOOD -> {
                    binding.healthCalc.text = "Your battery is healthy"
                    binding.healthCalc.setTextColor(Color.GREEN)
                    binding.imgHealth.setImageResource(R.drawable.healthy)
                }
                BatteryManager.BATTERY_HEALTH_OVERHEAT -> {
                    binding.healthCalc.text = "Your battery is overheat"
                    binding.healthCalc.setTextColor(Color.RED)
                    binding.imgHealth.setImageResource(R.drawable.overheat)
                }
                BatteryManager.BATTERY_HEALTH_OVER_VOLTAGE -> {
                    binding.healthCalc.text = "Your battery is over voltage"
                    binding.healthCalc.setTextColor(Color.YELLOW)
                    binding.imgHealth.setImageResource(R.drawable.overvoltage)
                }
                else -> {
                    binding.healthCalc.text = "Your battery is dead , please change it"
                    binding.healthCalc.setTextColor(Color.parseColor("#000000"))
                    binding.imgHealth.setImageResource(R.drawable.dead)
                }
            }
        }

    }

    // binding.circularProgressBar.progressBarColor = Color.RED
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainPageBinding.inflate(layoutInflater)
        setContentView(binding.root)
        registerReceiver(batteryInfo, IntentFilter(Intent.ACTION_BATTERY_CHANGED))
        this.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        binding.menuButton.setOnClickListener {
            binding.drawer.openDrawer(Gravity.RIGHT)
        }
        val batteryUsage = BatteryUsage(this)
        var app_usage_symbol: ImageView = findViewById(R.id.app_usage_symbole)
        //startActivity(Intent(this@MainPageActivity,UsageBatteryActivity::class.java))
        app_usage_symbol.setOnClickListener {

            Timer().schedule(timerTask {
                runOnUiThread {
                    var msg = Toast(applicationContext)
                    msg.duration = Toast.LENGTH_SHORT
                    msg.setGravity(Gravity.CENTER, 0, 0)
                    msg.setText("Loading Data , Please Wait")
                    msg.show()
                }
            }, 0)
            startActivity(Intent(this@MainPageActivity, UsageBatteryActivity::class.java))
        }
    }
}



