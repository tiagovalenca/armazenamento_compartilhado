package com.cesar.school.armazenamentocompartilhado

import android.Manifest
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.AndroidViewModel
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {
    private lateinit var layoutManager: LinearLayoutManager
    private lateinit var viewModel: MainViewModel
    private lateinit var recyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setSupportActionBar(findViewById<Toolbar>(R.id.toolbar)).apply {
            title = "Armazenamento Compartilhado"
        }

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) ==
            PackageManager.PERMISSION_GRANTED) {
        } else {
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE), 0)
        }

        viewModel = MainViewModel(this.application)

        recyclerView = findViewById(R.id.recyclerView)

        initialiseAdapter()
        viewModel.loadImages()
        recyclerView.adapter?.notifyDataSetChanged()
    }

    private fun initialiseAdapter(){
        recyclerView.itemAnimator = DefaultItemAnimator()
        observeData()
    }

    fun observeData(){
        viewModel.images.observe(this, Observer {
            recyclerView.adapter = StoredImageAdapter(viewModel, it)
            recyclerView.adapter?.stateRestorationPolicy = RecyclerView.Adapter.StateRestorationPolicy.ALLOW
        })
    }
}