package com.gregory.BMICalculator

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.android.material.slider.Slider
import java.util.Locale
import kotlin.math.pow
import androidx.core.content.ContextCompat

class MainActivity : AppCompatActivity() {

    private var height = 170
    private var weight = 70

    private lateinit var heightSlider: Slider
    private lateinit var heightTextView: TextView
    private lateinit var weightTextView: TextView
    private lateinit var resultTextView: TextView
    private lateinit var resultDescriptionTextView: TextView

    private lateinit var decreaseWeightButton: Button
    private lateinit var increaseWeightButton: Button
    private lateinit var calculateButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        connectViews()
        setupHeightSlider()
        setupWeightButtons()
        setupCalculateButton()
        updateHeightText()
        updateWeightText()
    }

    private fun connectViews() {
        heightSlider = findViewById(R.id.heightSlider)
        heightTextView = findViewById(R.id.heightTextView)
        weightTextView = findViewById(R.id.weightTextView)
        resultTextView = findViewById(R.id.resultTextView)
        resultDescriptionTextView = findViewById(R.id.resultDescriptionTextView)

        decreaseWeightButton = findViewById(R.id.decreaseWeightButton)
        increaseWeightButton = findViewById(R.id.increaseWeightButton)
        calculateButton = findViewById(R.id.calculateButton)
    }

    private fun setupHeightSlider() {
        heightSlider.addOnChangeListener { _, value, _ ->
            height = value.toInt()
            updateHeightText()
        }
    }

    private fun setupWeightButtons() {
        decreaseWeightButton.setOnClickListener {
            if (weight > 1) {
                weight--
                updateWeightText()
            }
        }

        increaseWeightButton.setOnClickListener {
            if (weight < 300) {
                weight++
                updateWeightText()
            }
        }
    }

    private fun setupCalculateButton() {
        calculateButton.setOnClickListener {
            calculateBmi()
        }
    }

    private fun updateHeightText() {
        heightTextView.text = getString(R.string.value_height, height)
    }

    private fun updateWeightText() {
        weightTextView.text = getString(R.string.value_weight, weight)
    }

    private fun calculateBmi() {
        val heightInMeters = height / 100f
        val bmi = weight / heightInMeters.pow(2)

        resultTextView.text = String.format(Locale.getDefault(), "%.2f", bmi)

        when {
            bmi < 18.5 -> {
                applyResult(
                    R.string.bmi_underweight,
                    R.color.bmi_underweight
                )
            }

            bmi < 25 -> {
                applyResult(
                    R.string.bmi_normal,
                    R.color.bmi_normal
                )
            }

            bmi < 30 -> {
                applyResult(
                    R.string.bmi_overweight,
                    R.color.bmi_overweight
                )
            }

            bmi < 35 -> {
                applyResult(
                    R.string.bmi_obesity,
                    R.color.bmi_obesity
                )
            }

            else -> {
                applyResult(
                    R.string.bmi_extreme_obesity,
                    R.color.bmi_extreme_obesity
                )
            }
        }
    }
    private fun applyResult(descriptionResId: Int, colorResId: Int) {
        val color = ContextCompat.getColor(this, colorResId)

        resultDescriptionTextView.text = getString(descriptionResId)
        resultDescriptionTextView.setTextColor(color)
        resultTextView.setTextColor(color)
    }
}