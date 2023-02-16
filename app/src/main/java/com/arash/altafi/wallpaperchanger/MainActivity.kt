package com.arash.altafi.wallpaperchanger

import android.app.WallpaperManager
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.os.Build
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.arash.altafi.wallpaperchanger.databinding.ActivityMainBinding
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import java.io.IOException

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setBackGround()
    }

    private fun setBackGround() = binding.apply {
        val target = object : CustomTarget<Bitmap>() {
            override fun onResourceReady(resource: Bitmap, transition: Transition<in Bitmap>?) {
                ivBackGround.setOnClickListener {
                    setBitmapAsWallpaper(resource, false)
                }
                ivBackGround.setOnLongClickListener {
                    setBitmapAsWallpaper(resource, true)
                    true
                }
            }

            override fun onLoadCleared(placeholder: Drawable?) {}
            override fun onLoadFailed(errorDrawable: Drawable?) {}
            override fun onLoadStarted(placeholder: Drawable?) {}
        }
        Glide.with(this@MainActivity)
            .asBitmap()
            .load("https://arashaltafi.ir/arash.jpg")
            .into(target)
    }

    private fun setBitmapAsWallpaper(bitmap: Bitmap, isLockScreen: Boolean) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            val wallPaper = if (isLockScreen) "LockScreen" else "BackGround"
            val wallpaperManager = WallpaperManager.getInstance(this)
            try {
                wallpaperManager.setBitmap(
                    bitmap,
                    null,
                    true,
                    if (isLockScreen)
                        WallpaperManager.FLAG_LOCK
                    else
                        WallpaperManager.FLAG_SYSTEM
                )
                Toast.makeText(this , "$wallPaper Successfully Changed" , Toast.LENGTH_SHORT).show()
            } catch (e: IOException) {
                Toast.makeText(this , "$wallPaper Not Changed" , Toast.LENGTH_SHORT).show()
                e.printStackTrace()
            }
        }
    }

}