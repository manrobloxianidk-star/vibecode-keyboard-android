package com.vibecode.keyboard

import android.app.Activity
import android.content.Intent
import android.graphics.Color
import android.graphics.Typeface
import android.graphics.drawable.GradientDrawable
import android.os.Bundle
import android.provider.Settings
import android.view.Gravity
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView

class MainActivity : Activity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.statusBarColor = Color.rgb(17, 19, 26)
        window.navigationBarColor = Color.rgb(17, 19, 26)

        val root = LinearLayout(this).apply {
            orientation = LinearLayout.VERTICAL
            gravity = Gravity.CENTER
            setPadding(dp(24), dp(28), dp(24), dp(28))
            setBackgroundColor(Color.rgb(17, 19, 26))
        }

        val title = TextView(this).apply {
            text = "Вайбкод\nКлавиатура"
            setTextColor(Color.WHITE)
            textSize = 34f
            typeface = Typeface.create("sans-serif", Typeface.BOLD)
            gravity = Gravity.CENTER
        }
        root.addView(title, matchWrap())

        val subtitle = TextView(this).apply {
            text = "Одна кнопка — одна легендарная фраза."
            setTextColor(Color.rgb(178, 181, 194))
            textSize = 16f
            gravity = Gravity.CENTER
            setPadding(0, dp(14), 0, dp(28))
        }
        root.addView(subtitle, matchWrap())

        root.addView(actionButton("1. Включить клавиатуру") {
            startActivity(Intent(Settings.ACTION_INPUT_METHOD_SETTINGS))
        }, matchWidth())

        root.addView(actionButton("2. Выбрать клавиатуру", accent = true) {
            val manager = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
            manager.showInputMethodPicker()
        }, matchWidth().apply { topMargin = dp(12) })

        val note = TextView(this).apply {
            text = "После включения выбери её в списке клавиатур.\nПриложение не запрашивает доступ к интернету."
            setTextColor(Color.rgb(145, 149, 164))
            textSize = 13f
            gravity = Gravity.CENTER
            setPadding(0, dp(24), 0, 0)
        }
        root.addView(note, matchWrap())

        setContentView(root)
    }

    private fun actionButton(label: String, accent: Boolean = false, action: () -> Unit): Button =
        Button(this).apply {
            text = label
            isAllCaps = false
            textSize = 16f
            setTextColor(Color.WHITE)
            background = rounded(if (accent) 0xFF7657E8.toInt() else 0xFF252936.toInt(), 18)
            setPadding(dp(12), dp(8), dp(12), dp(8))
            setOnClickListener { action() }
        }

    private fun rounded(color: Int, radiusDp: Int) = GradientDrawable().apply {
        setColor(color)
        cornerRadius = dp(radiusDp).toFloat()
    }

    private fun matchWrap() = LinearLayout.LayoutParams(
        LinearLayout.LayoutParams.MATCH_PARENT,
        LinearLayout.LayoutParams.WRAP_CONTENT
    )

    private fun matchWidth() = matchWrap().apply { height = dp(58) }

    private fun dp(value: Int): Int = (value * resources.displayMetrics.density).toInt()
}
