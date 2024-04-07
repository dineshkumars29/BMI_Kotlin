package com.example.bmi

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import android.widget.Toast.*
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }


        val weightText = findViewById<EditText>(R.id.weighteditTextNumber)
        val heightText = findViewById<EditText>(R.id.heighteditTextNumber)
        val buttonFunction = findViewById<Button>(R.id.buttonfunc)


        Log.i("mytag", "outside func")
//        val totalText = findViewById<TextView>(R.id.outputtextview) //not working this format
        buttonFunction.setOnClickListener {
            Log.i("mytag", "inside func")
//            if((weightText.text != null && weightText.text.toString() != "") && (heightText.text != null && heightText.text.toString() != "")) {
            val weight = weightText.text.toString()
            val height = heightText.text.toString()
            if(validateInput(weight, height)){
                val bmi =
                    weight.toFloat() / ((height.toFloat() / 100) * (height.toFloat() / 100))
                val twodigi = String.format("%.2f", bmi).toFloat()
                displayBmi(twodigi)
            } else{
                makeText(this@MainActivity,"Please Enter Your details correctly", LENGTH_SHORT).show()
            }
        }
    }

    private fun validateInput(weight:String?, height: String?): Boolean{
        return when {
            weight.isNullOrEmpty() -> {
                Toast.makeText(this,"weight is Empty",Toast.LENGTH_LONG).show()
                return false
            }
            height.isNullOrEmpty() -> {
                Toast.makeText(this,"height is Empty",Toast.LENGTH_LONG).show()
                return false
            }
            else -> {
                return true
            }
        }
    }

    private fun displayBmi(bmi:Float) {
        val output = findViewById<TextView>(R.id.outputtextview)
        val contentView = findViewById<TextView>(R.id.constentvaluetext)
        var result = ""
        var color = 0
        output.text = bmi.toString()
        when {
            bmi <18.50 -> {
                result = "UnderWeight"
                color = R.color.ContentTextOrangeColor
                }
            bmi in 18.50..24.50 -> { result = "normal"
                color = R.color.ContentTextGreenColor}
            bmi in 24.5..29.9 -> { result = "Overweight"
                color = R.color.ContentTextYellowColor}
            bmi > 30 -> {result = "Obese"
                color = R.color.ContentTextRedColor}
        }
        contentView.setTextColor(ContextCompat.getColor(this,color))
        contentView.text = result
    }
}