package com.tdtd.presentation.ui.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.FrameLayout
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.tdtd.presentation.R
import com.tdtd.presentation.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailAdminActivity : AppCompatActivity() {

    private lateinit var toolbar: Toolbar
    private lateinit var actionBar: ActionBar
    private lateinit var view: View
    private lateinit var frameLayout: FrameLayout
    private lateinit var inflater: LayoutInflater

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_admin)

        inflater = getSystemService(LAYOUT_INFLATER_SERVICE) as LayoutInflater
        frameLayout = findViewById<View>(R.id.detailAdminFrameLayout) as FrameLayout

        view = inflater.inflate(R.layout.layout_detail_admin, frameLayout, false)
        frameLayout.addView(view)

        toolbar = findViewById(R.id.app_toolbar)
        setSupportActionBar(toolbar)
        actionBar = supportActionBar!!
        actionBar.setDisplayShowCustomEnabled(true)
    }
}