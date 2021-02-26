package com.hackerwall

import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.*
import com.hackerwall.base.ImageManager
import com.hackerwall.base.parseJson
import com.hackerwall.base.request
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import com.hackerwall.di.HackerWallApp
import com.hackerwall.di.ServiceLocator
import java.net.URL

class MainActivity : AppCompatActivity() {
    private lateinit var imageMgr: ImageManager
    private lateinit var serviceLocator: ServiceLocator

    lateinit var image: ImageView
    lateinit var button: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        serviceLocator = (applicationContext as HackerWallApp).serviceLocator
        imageMgr = serviceLocator.providesImageManager()

        initUi()

        setImage()
        checkDay()
    }

    private fun initUi() {
        image = findViewById(R.id.image)
        button = findViewById(R.id.button)


        button.setOnClickListener {
            Toast.makeText(this, "Setting new wallpaper...", Toast.LENGTH_SHORT).show()
            imageMgr.getWallpaper {
                serviceLocator.providesWallpaperManager().setWallpaper(it)
                setImage()
                Toast.makeText(this, "Done!", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun checkDay() {
        lifecycleScope.launch(Dispatchers.IO) {
            val today  = serviceLocator.providesEsbCalService().getToday()
            if (today == null) {
                Log.d("test1234","null")
            } else {
                Log.d("test1234","not null")
            }
        }
    }

    private fun setImage() {

        imageMgr.getWallpaper {
            image.setImageDrawable(it)
        }
    }
}