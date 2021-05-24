package com.example.ui

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.blankj.utilcode.util.Utils
import com.example.ui.gallery.GalleryActivity
import kotlinx.android.synthetic.main.activity_main.*

/**
 * Created by yujunyao@xinrenlei.net on 5/24/21.
 */
class MainActivity: AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        Utils.init(application)

        gallery.setOnClickListener {
            startActivity(Intent(this, GalleryActivity::class.java))
        }
    }


}