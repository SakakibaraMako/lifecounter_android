package edu.uw.ischool.lifecounter

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.core.widget.doAfterTextChanged

class StartActivity : AppCompatActivity() {

    lateinit var btnEnter : Button
    lateinit var input : EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_start)

        btnEnter = findViewById(R.id.btnEnter)
        input = findViewById(R.id.input)

        input.setOnClickListener {
            if(input.text.toString() == getString(R.string.default_input_text)) input.setText("")
        }

        input.doAfterTextChanged { btnEnter.isEnabled = input.text.toString() != "" }

        btnEnter.setOnClickListener {
            var intent = Intent(this, MainActivity::class.java).apply {
                putExtra("number", input.text.toString().toInt())
            }
            startActivity(intent)
        }
    }
}