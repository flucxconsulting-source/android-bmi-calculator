package com.gregory.BMICalculator

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.slider.Slider
import java.util.Locale
import kotlin.math.pow

class MainActivity : AppCompatActivity() {

    private val defaultHeight = 170
    private val defaultWeight = 70

    private var height = 170
    private var weight = 70

    private lateinit var heightSlider: Slider
    private lateinit var heightTextView: TextView
    private lateinit var weightTextView: TextView
    private lateinit var resultTextView: TextView
    private lateinit var resultDescriptionTextView: TextView
    private lateinit var resultGuidanceTextView: TextView

    private lateinit var decreaseWeightButton: Button
    private lateinit var increaseWeightButton: Button
    private lateinit var calculateButton: Button
    private lateinit var resetButton: Button
    private lateinit var healthGuidanceButton: Button

    private var defaultResultTextColor = 0
    private var defaultDescriptionTextColor = 0

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
        defaultResultTextColor = resultTextView.currentTextColor
        defaultDescriptionTextColor = resultDescriptionTextView.currentTextColor
        setupHeightSlider()
        setupWeightButtons()
        setupCalculateButton()
        setupResetButton()
        setupHealthGuidanceButton()
        resetCalculator()
    }

    private fun connectViews() {
        heightSlider = findViewById(R.id.heightSlider)
        heightTextView = findViewById(R.id.heightTextView)
        weightTextView = findViewById(R.id.weightTextView)
        resultTextView = findViewById(R.id.resultTextView)
        resultDescriptionTextView = findViewById(R.id.resultDescriptionTextView)
        resultGuidanceTextView = findViewById(R.id.resultGuidanceTextView)

        decreaseWeightButton = findViewById(R.id.decreaseWeightButton)
        increaseWeightButton = findViewById(R.id.increaseWeightButton)
        calculateButton = findViewById(R.id.calculateButton)
        resetButton = findViewById(R.id.resetButton)
        healthGuidanceButton = findViewById(R.id.healthGuidanceButton)
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

    private fun setupResetButton() {
        resetButton.setOnClickListener {
            resetCalculator()
        }
    }

    private fun setupHealthGuidanceButton() {
        healthGuidanceButton.setOnClickListener {
            showHealthGuidanceDialog()
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
        val bmiCategory = getBmiCategory(bmi)

        resultTextView.text = String.format(Locale.getDefault(), "%.2f", bmi)
        applyResult(bmiCategory)
    }

    private fun getBmiCategory(bmi: Float): BmiCategory = when {
        bmi < 18.5 -> BmiCategory(
            R.string.bmi_underweight,
            R.string.bmi_underweight_guidance,
            R.color.bmi_underweight
        )

        bmi < 25 -> BmiCategory(
            R.string.bmi_normal,
            R.string.bmi_normal_guidance,
            R.color.bmi_normal
        )

        bmi < 30 -> BmiCategory(
            R.string.bmi_overweight,
            R.string.bmi_overweight_guidance,
            R.color.bmi_overweight
        )

        bmi < 35 -> BmiCategory(
            R.string.bmi_obesity,
            R.string.bmi_obesity_guidance,
            R.color.bmi_obesity
        )

        else -> BmiCategory(
            R.string.bmi_extreme_obesity,
            R.string.bmi_extreme_obesity_guidance,
            R.color.bmi_extreme_obesity
        )
    }

    private fun applyResult(bmiCategory: BmiCategory) {
        val color = ContextCompat.getColor(this, bmiCategory.colorResId)

        resultDescriptionTextView.text = getString(bmiCategory.descriptionResId)
        resultGuidanceTextView.text = getString(bmiCategory.guidanceResId)
        resultDescriptionTextView.setTextColor(color)
        resultTextView.setTextColor(color)
    }

    private fun resetCalculator() {
        height = defaultHeight
        weight = defaultWeight
        heightSlider.value = defaultHeight.toFloat()
        updateHeightText()
        updateWeightText()

        resultTextView.text = getString(R.string.placeholder_result)
        resultTextView.setTextColor(defaultResultTextColor)
        resultDescriptionTextView.text = getString(R.string.placeholder_description)
        resultDescriptionTextView.setTextColor(defaultDescriptionTextColor)
        resultGuidanceTextView.text = getString(R.string.placeholder_guidance)
    }

    private fun showHealthGuidanceDialog() {
        MaterialAlertDialogBuilder(this)
            .setTitle(R.string.health_guidance_title)
            .setMessage(R.string.health_guidance_message)
            .setPositiveButton(R.string.dialog_ok, null)
            .show()
    }

    private data class BmiCategory(
        val descriptionResId: Int,
        val guidanceResId: Int,
        val colorResId: Int
    )
}
