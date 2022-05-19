package alborz.rad.batterymanager.Activities

import alborz.rad.batterymanager.databinding.ActivityMainBinding
import android.content.Intent
import android.content.pm.ActivityInfo
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import java.util.*
import kotlin.concurrent.timerTask

class MainActivity : AppCompatActivity() {

    private lateinit var binding:ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        var intent: Intent = Intent(this@MainActivity,MainPageActivity::class.java)
        Timer().schedule(timerTask {
            kotlin.run {  startActivity(intent)
            finish()}
        },7000)
        this.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
    }
}