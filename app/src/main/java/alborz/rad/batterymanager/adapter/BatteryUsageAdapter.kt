package alborz.rad.batterymanager.adapter

import alborz.rad.batterymanager.R
import alborz.rad.batterymanager.models.BatteryModel
import android.content.Context
import android.content.pm.ApplicationInfo
import android.content.pm.PackageManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import kotlin.math.roundToInt

class BatteryUsageAdapter(
    private var context: Context,
    private val battery: MutableList<BatteryModel>,
    val totalTime: Long) :
    RecyclerView.Adapter<BatteryUsageAdapter.ViewHolder>() {

    private var batteryFinalList: MutableList<BatteryModel> = ArrayList()

    init {
        batteryFinalList = calcBatteryUsage()
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): BatteryUsageAdapter.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val listItem = inflater.inflate(R.layout.item_usage_battery, parent, false)
        return ViewHolder(listItem)
    }

    override fun onBindViewHolder(holder: BatteryUsageAdapter.ViewHolder, position: Int) {
        holder.appNametext.text = "${batteryFinalList[position].packageName.toString()}"
        holder.timeUsageInPercent.text = "${batteryFinalList[position].tookenTimeInPercent}" + "%"
        holder.timeUsage.text = "${batteryFinalList[position].timeUsage}"
        holder.progressBar.progress = batteryFinalList[position].tookenTimeInPercent
    }

    override fun getItemCount(): Int {
        return batteryFinalList.size
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val appNametext: TextView = view.findViewById(R.id.appNameText)
        val timeUsageInPercent: TextView = view.findViewById(R.id.timeUsageInPercent)
        val timeUsage: TextView = view.findViewById(R.id.timeUsage)
        val progressBar: ProgressBar = view.findViewById(R.id.progressBar)
    }

    fun calcBatteryUsage(): MutableList<BatteryModel> {
        var appStatsListSorted = battery.groupBy { it.packageName }.mapValues { entry ->
            entry.value.sumBy { it.tookenTimeInPercent }
        }.toList().sortedWith(compareBy { it.second }).reversed()

        var finalList: MutableList<BatteryModel> = ArrayList()

        for (item in appStatsListSorted) {
            var bm = BatteryModel()
            val timePerApp = item.second.toFloat() / 100 * totalTime.toFloat() / 1000 / 60
            val hour = timePerApp / 60
            val min = timePerApp % 60
            bm.packageName = item.first
            bm.tookenTimeInPercent = item.second
            bm.timeUsage = "${hour.roundToInt()} Hour(s) : ${min.roundToInt()} Minute(s)"
            if (bm.tookenTimeInPercent != 0) {
                finalList += bm
            }
        }
        return finalList
    }

}