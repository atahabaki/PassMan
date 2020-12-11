package dev.atahabaki.passman.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.add
import androidx.fragment.app.commit
import dev.atahabaki.passman.R
import dev.atahabaki.passman.ui.fragments.Modulo3Fragment

class MainActivity : AppCompatActivity(R.layout.activity_main) {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //For now, modulo3Fragment is the default one. :D
        if (savedInstanceState == null) {
            supportFragmentManager.commit {
                setReorderingAllowed(true)
                add<Modulo3Fragment>(R.id.fragment_container)
            }
        }
    }
}