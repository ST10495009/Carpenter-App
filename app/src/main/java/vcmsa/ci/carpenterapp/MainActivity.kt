package vcmsa.ci.carpenterapp

import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    // using mapOf to assign a price to each material
    private val materialCosts = mapOf(
        R.id.cbWood to 500,
        R.id.cbHardboard to 200,
        R.id.cbNails to 20,
        R.id.cbHinges to 30
    )

    // storing the selected task type
    private var selectedTask: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // referencing UI elements
        val radioGroupTask = findViewById<RadioGroup>(R.id.radioGroupTask)

        val cbWood = findViewById<CheckBox>(R.id.cbWood)
        val cbHardboard = findViewById<CheckBox>(R.id.cbHardboard)
        val cbNails = findViewById<CheckBox>(R.id.cbNails)
        val cbHinges = findViewById<CheckBox>(R.id.cbHinges)

        val etHours = findViewById<EditText>(R.id.etHours)
        val btnCalculate = findViewById<Button>(R.id.btnCalculate)
        val tvEstimate = findViewById<TextView>(R.id.tvEstimate)

        // tracking which task the user selects
        radioGroupTask.setOnCheckedChangeListener { _, checkedId ->
            selectedTask = when (checkedId) {
                R.id.radInstallDoor -> "Install Door"
                R.id.radBuildShelf -> "Build Shelf"
                R.id.radAssembleFurniture -> "Assemble Furniture"
                else -> ""
            }
        }

        btnCalculate.setOnClickListener {

            // Validating whether or not a task is selected
            if (selectedTask.isEmpty()) {
                Toast.makeText(this, "Please select a task.", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // Fetching and validating the number of hours entered
            val hoursText = etHours.text.toString()
            if (hoursText.isEmpty()) {
                Toast.makeText(this, "Please enter the number of hours.", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val hours = hoursText.toIntOrNull()
            if (hours == null || hours <= 0) {
                Toast.makeText(this, "Enter a valid positive number of hours.", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // Calculating total material costs based on selected checkboxes
            val checkboxes = listOf(cbWood ,cbHardboard, cbNails, cbHinges)
            var atLeastOneMaterialSelected = false
            var materialTotal = 0

            for (checkbox in checkboxes) {
                if (checkbox.isChecked) {
                    atLeastOneMaterialSelected = true
                    materialTotal += materialCosts[checkbox.id] ?: 0
                }
            }

            // Validating that the user selected at least one material
            if (!atLeastOneMaterialSelected) {
                Toast.makeText(this, "Please select at least one material.", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // Calculating the final cost (material cost × number of hours)
            val totalCost = materialTotal * hours

            // Displaying the result
            tvEstimate.text = "Task: $selectedTask\nEstimated Cost: R$totalCost"
        }
    }
}
