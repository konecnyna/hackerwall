package com.hackerwall.ui

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.davemorrissey.labs.subscaleview.ImageSource
import com.davemorrissey.labs.subscaleview.SubsamplingScaleImageView
import com.google.android.material.snackbar.BaseTransientBottomBar
import com.google.android.material.snackbar.Snackbar
import com.hackerwall.R
import com.hackerwall.base.Event
import com.hackerwall.base.ImageManager
import com.hackerwall.base.fadeIn
import com.hackerwall.base.fadeOut
import com.hackerwall.di.HackerWallApp
import com.hackerwall.di.ServiceLocator
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe

class MainActivity : AppCompatActivity() {
    private lateinit var imageMgr: ImageManager
    private lateinit var serviceLocator: ServiceLocator

    lateinit var image: SubsamplingScaleImageView
    lateinit var button: Button
    lateinit var calTextView: TextView
    lateinit var timestampTextView: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        serviceLocator = (applicationContext as HackerWallApp).serviceLocator
        imageMgr = serviceLocator.providesImageManager()

        initUi()
        work()
        EventBus.getDefault().register(this)

        logBtn.setOnClickListener {
            val modalbottomSheetFragment = ModalBottomSheet()
            modalbottomSheetFragment.show(supportFragmentManager, modalbottomSheetFragment.tag)
        }

        logBtn.setOnLongClickListener {
            serviceLocator.provideLogger().clearLogs()
            Snackbar.make(content, "Logs cleared", BaseTransientBottomBar.LENGTH_SHORT).show()
            true
        }
    }

    override fun onResume() {
        super.onResume()
        work()
    }

    override fun onStop() {
        super.onStop()
        EventBus.getDefault().unregister(this)
    }

    @Subscribe
    fun handleEvent(event: Event.WallpaperJobFire) {
        setWallpaperLastRun()
    }

    private fun work() {
        setImage()
        setWallpaperLastRun()
        checkDay()
    }

    private fun setWallpaperLastRun() {
        val date = serviceLocator.providesStorageManager().wallpaperJobLastRun
        val log = serviceLocator.providesStorageManager().errorLog

        var msg = ""
        if (date.isNotEmpty()) {
            msg = "Last run: $date"
        }

        if (log.isNotEmpty()) {
            msg += "\nError: $log"
        }

        if (msg.isNotEmpty()) {
            timestampTextView.fadeOut()
            timestampTextView.text = msg
            timestampTextView.fadeIn()
        }
    }


    private fun initUi() {
        image = findViewById(R.id.image)
        button = findViewById(R.id.button)
        calTextView = findViewById(R.id.cal)
        timestampTextView = findViewById(R.id.timestamp)


        button.setOnClickListener {
            Toast.makeText(this, "Setting new wallpaper...", Toast.LENGTH_SHORT).show()
            imageMgr.getWallpaper(true) {
                serviceLocator.providesWallpaperManager().setWallpaper(it)
                setImage()
                Toast.makeText(this, "Done!", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun checkDay() {
        lifecycleScope.launch(Dispatchers.IO) {
            val today = serviceLocator.providesEsbCalService().getToday()
            withContext(Dispatchers.Main) {
                today?.let {
                    calTextView.text = it["content"]
                    calTextView.fadeIn(400)
                }
            }
        }
    }

    private fun setImage() {
        imageMgr.getWallpaper {
            image.setImage(ImageSource.bitmap(it))
        }
    }
}