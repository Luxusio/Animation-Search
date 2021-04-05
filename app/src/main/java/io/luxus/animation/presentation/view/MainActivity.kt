package io.luxus.animation.presentation.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import io.luxus.animation.R

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        // https://medium.com/@anoopg87/set-start-destination-for-navhostfragment-dynamically-b072a29bfe49
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.navigation_graph) as NavHostFragment?
        val navInflater = navHostFragment!!.navController.navInflater

        val navGraph = navInflater.inflate(R.navigation.navigation_graph)
        val navController = navHostFragment.navController

        navController.graph = navGraph

    }
}