package com.example.githubuser.ui.theme

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.CompoundButton
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatDelegate
import com.example.githubuser.ViewModelFactory
import com.example.githubuser.databinding.ActivityThemeBinding


class ThemeActivity : AppCompatActivity() {

    private lateinit var themeBinding: ActivityThemeBinding
    private val themeViewModel by viewModels<ThemeViewModel> {
        ViewModelFactory.getInstance(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        themeBinding= ActivityThemeBinding.inflate(layoutInflater)
        setContentView(themeBinding.root)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        themeViewModel.getThemeSettings().observe(this) { isDarkModeActive: Boolean ->
            if (isDarkModeActive) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                themeBinding.switchTheme.isChecked = true
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                themeBinding.switchTheme.isChecked = false
            }

        }
        themeBinding.switchTheme.setOnCheckedChangeListener { _: CompoundButton?, isChecked: Boolean ->
           themeViewModel.saveThemeSetting(isChecked)
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId){
            android.R.id.home -> {
                finish()
            }
        }
        return super.onOptionsItemSelected(item)
    }
}