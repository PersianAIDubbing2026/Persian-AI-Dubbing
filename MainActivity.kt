package com.persian.aidubbing

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast

class MainActivity : Activity() {
    private var selectedAudio: Uri? = null
    private lateinit var status: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val layout = LinearLayout(this).apply {
            orientation = LinearLayout.VERTICAL
            setPadding(48, 64, 48, 48)
        }

        val title = TextView(this).apply {
            text = "Persian AI Dubbing"
            textSize = 26f
        }
        val intro = TextView(this).apply {
            text = "دوبله هوشمند و ترجمه ویدئو به فارسی"
            textSize = 18f
            setPadding(0, 24, 0, 24)
        }
        status = TextView(this).apply {
            text = "برای شروع، یک فایل صوتی یا ویدئویی انتخاب کنید."
            textSize = 16f
            setPadding(0, 16, 0, 24)
        }
        val choose = Button(this).apply {
            text = "انتخاب فایل"
            setOnClickListener { openFilePicker() }
        }
        val process = Button(this).apply {
            text = "شروع دوبله"
            setOnClickListener {
                if (selectedAudio == null) {
                    Toast.makeText(this@MainActivity, "اول یک فایل انتخاب کنید", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(
                        this@MainActivity,
                        "فایل انتخاب شد. برای فعال شدن دوبله واقعی، سرویس هوش مصنوعی باید متصل شود.",
                        Toast.LENGTH_LONG
                    ).show()
                }
            }
        }

        layout.addView(title)
        layout.addView(intro)
        layout.addView(choose)
        layout.addView(status)
        layout.addView(process)
        setContentView(layout)
    }

    private fun openFilePicker() {
        val intent = Intent(Intent.ACTION_OPEN_DOCUMENT).apply {
            addCategory(Intent.CATEGORY_OPENABLE)
            type = "*/*"
            putExtra(Intent.EXTRA_MIME_TYPES, arrayOf("audio/*", "video/*"))
        }
        startActivityForResult(intent, 100)
    }

    @Deprecated("Deprecated in Android, retained for broad device compatibility")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 100 && resultCode == RESULT_OK) {
            selectedAudio = data?.data
            status.text = if (selectedAudio != null) {
                "فایل انتخاب شد: ${selectedAudio!!.lastPathSegment ?: "فایل آماده است"}"
            } else {
                "فایلی انتخاب نشد."
            }
        }
    }
}
