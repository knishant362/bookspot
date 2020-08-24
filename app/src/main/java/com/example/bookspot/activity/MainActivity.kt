package com.example.bookspot.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.FrameLayout
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import com.example.bookspot.R
import com.example.bookspot.fragment.AboutAppFragment
import com.example.bookspot.fragment.DashboardFragment
import com.example.bookspot.fragment.FavouritesFragment
import com.example.bookspot.fragment.ProfileFragment
import com.google.android.material.navigation.NavigationView

class MainActivity : AppCompatActivity() {

    lateinit var drawerLayout: DrawerLayout
    lateinit var coordinatorLayout: CoordinatorLayout
    lateinit var toolbar: androidx.appcompat.widget.Toolbar
    lateinit var frameLayout: FrameLayout
    lateinit var navigationView: NavigationView

    var previousMenuItem : MenuItem ?= null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        drawerLayout = findViewById(R.id.drawerLayout)
        coordinatorLayout = findViewById(R.id.coordinatorLayout)
        toolbar = findViewById(R.id.toolbar)
        frameLayout = findViewById(R.id.frameLayout)
        navigationView = findViewById(R.id.navigationView)

        setUpToolbar()

        openDashboard()

        val actionBarDrawerToggle = ActionBarDrawerToggle(this@MainActivity , drawerLayout,
            R.string.open_drawer,
            R.string.close_drawer
        )

        drawerLayout.addDrawerListener(actionBarDrawerToggle)
        actionBarDrawerToggle.syncState()

        navigationView.setNavigationItemSelectedListener {

            if(previousMenuItem != null){
                previousMenuItem?.isChecked = false
            }
            it?.isCheckable = true
            it?.isChecked = true
            previousMenuItem = it

            when(it.itemId){
                R.id.dashboard -> {
                    openDashboard()
                    drawerLayout.closeDrawers()

                    Toast.makeText(this@MainActivity , "Click on Dashboard" , Toast.LENGTH_SHORT).show()
                }
                R.id.favourites -> {
                    supportFragmentManager.beginTransaction()
                        .replace(
                            R.id.frameLayout,
                            FavouritesFragment()
                        )
                        .commit()

                    supportActionBar?.title = "Favourites"
                    drawerLayout.closeDrawers()
                    Toast.makeText(this@MainActivity , "Click on Favourites" , Toast.LENGTH_SHORT).show()
                }
                R.id.profile -> {
                    supportFragmentManager.beginTransaction()
                        .replace(
                            R.id.frameLayout,
                            ProfileFragment()
                        )
                        .commit()

                    supportActionBar?.title = "Profile"
                    drawerLayout.closeDrawers()
                    Toast.makeText(this@MainActivity , "Click on Profile" , Toast.LENGTH_SHORT).show()
                }
                R.id.aboutApp -> {
                    supportFragmentManager.beginTransaction()
                        .replace(
                            R.id.frameLayout,
                            AboutAppFragment()
                        )
                        .commit()

                    supportActionBar?.title = "About App"
                    drawerLayout.closeDrawers()
                    Toast.makeText(this@MainActivity , "Click on About App" , Toast.LENGTH_SHORT).show()
                }
            }

            return@setNavigationItemSelectedListener true
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        val id = item.itemId

        if (id == android.R.id.home){
            drawerLayout.openDrawer(GravityCompat.START)
        }

        return super.onOptionsItemSelected(item)
    }

    fun setUpToolbar(){
        setSupportActionBar(toolbar)
        supportActionBar?.title = "Toolbar title"
        supportActionBar?.setHomeButtonEnabled(true)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

    }
    fun openDashboard(){
        val fragment = DashboardFragment()
        val transaction = supportFragmentManager.beginTransaction()

        transaction.replace(R.id.frameLayout, fragment).commit()
        supportActionBar?.title = "Dashboard"
        supportActionBar?.title = "Dashboard"
        navigationView.setCheckedItem(R.id.dashboard)
    }

    override fun onBackPressed() {
        val frag = supportFragmentManager.findFragmentById(R.id.frameLayout)

        when(frag) {

            !is DashboardFragment -> openDashboard()

            else -> super.onBackPressed()
        }
    }
}