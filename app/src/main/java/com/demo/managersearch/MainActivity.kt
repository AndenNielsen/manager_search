package com.demo.managersearch

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.demo.managersearch.ui.ManagerSearchFragment
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview

@FlowPreview
@ExperimentalCoroutinesApi
class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, ManagerSearchFragment.newInstance())
                .commitNow()
        }
    }
}