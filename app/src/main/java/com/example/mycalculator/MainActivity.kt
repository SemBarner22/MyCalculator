package com.example.mycalculator

import android.annotation.SuppressLint
import android.graphics.Color
import android.os.Bundle
import android.text.method.ScrollingMovementMethod
import android.view.View
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.core.text.isDigitsOnly
import kotlinx.android.synthetic.main.activity_main.*
import net.objecthunter.exp4j.ExpressionBuilder
import java.lang.Exception

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        answer.movementMethod = ScrollingMovementMethod();
        text.movementMethod = ScrollingMovementMethod();
    }

    var color = "grey"
    var string : String = "0"
    var ans : String = "0"

    fun eval(view: View) {
        var appending: String = (view as Button).tag as String
        if (string == "0" && appending.matches("\\d+".toRegex())) {
            string = appending;
        } else {
            string += appending
        }
        text.text = string
    }

    @SuppressLint("SetTextI18n")
    fun res(view: View?) {
        try {
            answer.text = ExpressionBuilder(string).build().evaluate().toBigDecimal().toString()
            answer.setTextColor(Color.GRAY)
            ans = answer.text.toString()
        }  catch (e : Exception) {
            answer.setTextColor(Color.RED)
        }
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        color = savedInstanceState.getString("color").toString()
        string = savedInstanceState.getString("string").toString()
        ans = savedInstanceState.getString("answer").toString()
        text.text = string
        res(null)
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putString("string", text.text as String?)
        outState.putString("color", color)
        outState.putString("answer", answer.text as String?)
    }

    fun clear(view: View) {
        string = "0"
        text.text = string
    }

    fun backspace(view: View) {
        if (string.isNotEmpty()) {
            string = string.substring(0, string.length - 1)
        } else {
            string = "0"
        }
        text.text = string
    }


}