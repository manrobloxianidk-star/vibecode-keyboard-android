package com.vibecode.keyboard

import android.graphics.Color
import android.graphics.Typeface
import android.graphics.drawable.GradientDrawable
import android.inputmethodservice.InputMethodService
import android.os.Build
import android.view.Gravity
import android.view.View
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView

class VibeKeyboardService : InputMethodService() {
    private val backgroundColor = Color.rgb(17, 19, 26)
    private val keyColor = Color.rgb(37, 41, 54)
    private val accentColor = Color.rgb(118, 87, 232)

    override fun onCreateInputView(): View {
        val keyboard = LinearLayout(this).apply {
            orientation = LinearLayout.VERTICAL
            setPadding(dp(8), dp(7), dp(8), dp(9))
            setBackgroundColor(backgroundColor)
        }

        val heading = TextView(this).apply {
            text = "ВАЙБКОД  ·  БЫСТРЫЕ ФРАЗЫ"
            setTextColor(Color.rgb(171, 157, 255))
            textSize = 11f
            typeface = Typeface.create("sans-serif-medium", Typeface.NORMAL)
            gravity = Gravity.CENTER_VERTICAL
            setPadding(dp(4), 0, dp(4), dp(5))
        }
        keyboard.addView(heading, LinearLayout.LayoutParams(-1, dp(24)))

        addPhraseRow(keyboard, "Continue", "Давай быстрее пж")
        addPhraseRow(keyboard, "Че там", "я люблю клод код")
        addPhraseRow(keyboard, "я люблю кодекс", "самое главное мэйк но мистэйкс")
        addPhraseRow(keyboard, "Хуйня переделай", "Охуенно оставляй")

        val finalPhrase = key("сделай трекер привычек", accent = true) {
            commit("сделай трекер привычек")
        }
        keyboard.addView(finalPhrase, rowParams())

        val controls = LinearLayout(this).apply {
            orientation = LinearLayout.HORIZONTAL
            gravity = Gravity.CENTER
        }
        controls.addView(key("⌫  Удалить", textSize = 13f) { deleteOneCharacter() }, keyParams(1f))
        controls.addView(key("⇄  Клавиатура", textSize = 12f) {
            switchToNextInputMethod(false)
        }, keyParams(1.25f))
        controls.addView(key("Пробел ×3", textSize = 13f) { commit("   ") }, keyParams(1f))
        keyboard.addView(controls, rowParams(bottom = false))

        return keyboard
    }

    private fun addPhraseRow(parent: LinearLayout, left: String, right: String) {
        val row = LinearLayout(this).apply {
            orientation = LinearLayout.HORIZONTAL
            gravity = Gravity.CENTER
        }
        row.addView(key(left) { commit(left) }, keyParams(1f))
        row.addView(key(right, textSize = 12.5f) { commit(right) }, keyParams(1f))
        parent.addView(row, rowParams())
    }

    private fun key(label: String, accent: Boolean = false, textSize: Float = 14f, action: () -> Unit) =
        Button(this).apply {
            text = label
            isAllCaps = false
            maxLines = 2
            this.textSize = textSize
            typeface = Typeface.create("sans-serif-medium", Typeface.NORMAL)
            setTextColor(Color.WHITE)
            background = rounded(if (accent) accentColor else keyColor, 13)
            setPadding(dp(5), dp(4), dp(5), dp(4))
            minHeight = dp(48)
            setOnClickListener { action() }
        }

    private fun commit(text: String) {
        currentInputConnection?.commitText(text, 1)
    }

    private fun deleteOneCharacter() {
        val connection = currentInputConnection ?: return
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            connection.deleteSurroundingTextInCodePoints(1, 0)
        } else {
            connection.deleteSurroundingText(1, 0)
        }
    }

    private fun rounded(color: Int, radiusDp: Int) = GradientDrawable().apply {
        setColor(color)
        cornerRadius = dp(radiusDp).toFloat()
    }

    private fun keyParams(weight: Float) = LinearLayout.LayoutParams(0, dp(52), weight).apply {
        marginStart = dp(3)
        marginEnd = dp(3)
    }

    private fun rowParams(bottom: Boolean = true) = LinearLayout.LayoutParams(-1, dp(55)).apply {
        if (bottom) bottomMargin = dp(5)
    }

    private fun dp(value: Int): Int = (value * resources.displayMetrics.density).toInt()
}
