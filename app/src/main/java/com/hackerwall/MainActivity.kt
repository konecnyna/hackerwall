package com.hackerwall

import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.snackbar.Snackbar
import com.hackerwall.base.ImageManager
import com.hackerwall.di.HackerWallApp
import com.hackerwall.di.ServiceLocator

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

        image = findViewById(R.id.image)
        button = findViewById(R.id.button)


        button.setOnClickListener {
            Toast.makeText(this,"Setting new wallpaper...", Toast.LENGTH_SHORT).show()
            imageMgr.getWallpaper {
                serviceLocator.providesWallpaperManager().setWallpaper(it)
                setImage()
                Toast.makeText(this,"Done!", Toast.LENGTH_SHORT).show()
            }
        }

        setImage()
        checkDay()
    }

    private fun checkDay() {
        
    }

    private fun setImage() {

        imageMgr.getWallpaper {
            image.setImageDrawable(it)
        }
    }
}