package alborz.rad.batterymanager.Activities

import alborz.rad.batterymanager.adapter.BatteryUsageAdapter
import alborz.rad.batterymanager.databinding.ActivityUsageBatteryBinding
import alborz.rad.batterymanager.models.BatteryModel
import alborz.rad.batterymanager.utilies.BatteryUsage
import android.content.Intent
import android.content.pm.ActivityInfo
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Gravity
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager


class UsageBatteryActivity : AppCompatActivity() {

    lateinit var binding: ActivityUsageBatteryBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUsageBatteryBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val batteryUsage = BatteryUsage(this)
        var appStatsList: MutableList<BatteryModel> = ArrayList()

        if(batteryUsage.getUsageStatsList().isEmpty()) {

        }
        for (item in batteryUsage.getUsageStatsList()) {

            if (item.totalTimeInForeground > 1000) {
                var bm = BatteryModel()
                bm.packageName = item.packageName
                bm.tookenTimeInPercent =
                    (item.totalTimeInForeground.toFloat() / batteryUsage.getTotalTime()
                        .toFloat() * 100).toInt()
                appStatsList += bm
            }
        }

        this.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        val adapter = BatteryUsageAdapter(this,appStatsList,batteryUsage.getTotalTime())
        binding.recyclerview.setHasFixedSize(true)
        binding.recyclerview.layoutManager = LinearLayoutManager(this)
        binding.recyclerview.adapter = adapter
    }

}



