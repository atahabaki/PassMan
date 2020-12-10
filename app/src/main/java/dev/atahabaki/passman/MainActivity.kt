package dev.atahabaki.passman

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

    }

    fun modulo3(plain: String, key: String): String? {
        var ciphertext: String = "";
        if (plain.length == key.length) {
            var i=0
            while (i < plain.length) {
                if (i % 123 == 0)
                    ciphertext += plain[i];
                if (i % 5 == 0)
                    ciphertext += key[i];
                if (i % 3 == 0)
                    ciphertext += key[i].toByte().toInt().toString();
                if (i % 2 == 0)
                    ciphertext += plain[i].toByte().toInt().toString();
                else
                    ciphertext += plain[i];
                i++
            }
        }
        return ciphertext;
    }
}