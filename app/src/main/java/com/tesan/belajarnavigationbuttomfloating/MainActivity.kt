package com.tesan.belajarnavigationbuttomfloating

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.ui.NavigationUI.setupWithNavController
import androidx.navigation.fragment.NavHostFragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.tesan.belajarnavigationbuttomfloating.databinding.ActivityMainBinding
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ReplaceFragment(HomeFragment())

        binding.bottomNavView.setOnItemSelectedListener {
            when(it.itemId){
                R.id.homeFragment -> ReplaceFragment(HomeFragment())
                R.id.addFragment -> ReplaceFragment(ProfileDetailFragment())
                R.id.profileFragment -> ReplaceFragment(ProfileFragment())
            }
            true
        }
    }

    fun ReplaceFragment(fragment: Fragment){
        val fragmentManager = supportFragmentManager
        val fragmentTransition = fragmentManager.beginTransaction()
        fragmentTransition.replace(R.id.mainFrag, fragment)
        fragmentTransition.commit()
    }
}