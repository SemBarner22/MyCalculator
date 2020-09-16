package com.example.mycalculator

import android.annotation.SuppressLint
import android.content.ClipData
import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.content.ClipboardManager
import android.text.method.ScrollingMovementMethod
import android.view.View
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
import net.objecthunter.exp4j.ExpressionBuilder

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        answer.movementMethod = ScrollingMovementMethod();
        text.movementMethod = ScrollingMovementMethod();
    }

    @SuppressLint("SetTextI18n")
    fun eval(view: View) {
        var appending: String = (view as Button).tag as String
        text.text = text.text.toString() + appending
    }

    fun copy(view: View) {
        setClipboard(this, answer.text.toString())
    }


    private fun setClipboard(context: Context, text: String) {
        val clipboard =
            context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
        val clip = ClipData.newPlainText("Copied Text", text)
        clipboard.setPrimaryClip(clip)
    }

    @SuppressLint("SetTextI18n")
    fun res(view: View?) {
        try {
            if (text.text.isEmpty()) {
                answer.text = "0"
            } else {
                answer.text =
                    ExpressionBuilder(text.text.toString()).build().evaluate().toBigDecimal()
                        .toString()
            }
            answer.setTextColor(Color.GRAY)
        } catch (e: Exception) {
            answer.setTextColor(Color.RED)
        }
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        answer.setTextColor(savedInstanceState.getInt("color"))
        text.text = savedInstanceState.getString("string").toString()
        answer.text = savedInstanceState.getString("answer").toString()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putString("string", text.text.toString())
        outState.putInt("color", answer.currentTextColor)
        outState.putString("answer", answer.text.toString())
    }

    fun clear(view: View) {
        text.text = ""
    }

    fun backspace(view: View) {
        if (text.text.isNotEmpty()) {
            text.text = text.text.dropLast(1)
        } else {
            text.text = ""
        }
    }


}