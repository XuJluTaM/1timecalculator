package com.example.timecalculator

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import java.text.SimpleDateFormat
import java.util.*

class MainActivity : AppCompatActivity() {

    private lateinit var inputTime1: EditText
    private lateinit var inputTime2: EditText
    private lateinit var addBtn: Button
    private lateinit var subtractBtn: Button
    private lateinit var resultTextView: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.apply {
            setDisplayShowHomeEnabled(true)
            setLogo(R.mipmap.ic_launcher) // Убедитесь, что ic_launcher существует в res/mipmap
            setDisplayUseLogoEnabled(true)
            title = "Time Calculator"
            subtitle = "Add and Subtract Times"
        }

        inputTime1 = findViewById(R.id.inputTime1)
        inputTime2 = findViewById(R.id.inputTime2)
        addBtn = findViewById(R.id.addBtn)
        subtractBtn = findViewById(R.id.subtractBtn)
        resultTextView = findViewById(R.id.resultTextView)

        addBtn.setOnClickListener { calculateTime(true) }
        subtractBtn.setOnClickListener { calculateTime(false) }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_clear -> {
                clearFields()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun calculateTime(isAddition: Boolean) {
        val timeFormat = SimpleDateFormat("HH:mm:ss", Locale.getDefault())
        try {
            val time1 = timeFormat.parse(inputTime1.text.toString())
            val time2 = timeFormat.parse(inputTime2.text.toString())

            val calendar = Calendar.getInstance()
            calendar.time = time1!!

            val seconds = time2!!.hours * 3600 + time2.minutes * 60 + time2.seconds
            if (isAddition) {
                calendar.add(Calendar.SECOND, seconds)
            } else {
                calendar.add(Calendar.SECOND, -seconds)
            }

            val resultTime = timeFormat.format(calendar.time)
            resultTextView.text = resultTime
            resultTextView.setTextColor(getColor(R.color.resultColor))
            Toast.makeText(this, "Результат: $resultTime", Toast.LENGTH_LONG).show()
        } catch (e: Exception) {
            e.printStackTrace()
            Toast.makeText(this, "Неверный формат времени", Toast.LENGTH_LONG).show()
        }
    }

    private fun clearFields() {
        inputTime1.text.clear()
        inputTime2.text.clear()
        resultTextView.text = "Result will be shown here"
        resultTextView.setTextColor(getColor(android.R.color.black))
        Toast.makeText(this, "Данные очищены", Toast.LENGTH_LONG).show()
    }

    override fun onDestroy() {
        super.onDestroy()
        Toast.makeText(this, "Приложение закрыто", Toast.LENGTH_LONG).show()
    }
}