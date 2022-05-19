package alborz.rad.batterymanager.utilies

import android.app.usage.UsageStats
import android.app.usage.UsageStatsManager
import android.content.Context
import android.content.Intent
import android.provider.Settings
import java.util.*

class BatteryUsage(context:Context) {

    private var context:Context = context
    init {
        if(getUsageStatsList().isEmpty()) {
            var intent = Intent(Settings.ACTION_USAGE_ACCESS_SETTINGS)
            context.startActivity(intent)
        }
    }

    fun getTotalTime(): Long {
        var totalTime:Long = 0
        for (item in getUsageStatsList()) {
            totalTime+= item.totalTimeInForeground.toLong()
        }
        return totalTime
    }
    fun getUsageStatsList():List<UsageStats> {

        var usm = context.getSystemService("usagestats") as UsageStatsManager
        var endTime = System.currentTimeMillis()
        var startTime = System.currentTimeMillis() - 24 * 3600 * 1000
        return usm.queryUsageStats(UsageStatsManager.INTERVAL_DAILY,startTime,endTime)
    }

}