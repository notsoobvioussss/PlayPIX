package playpix.playpixaposta.plataformaplaypix.baixarplaypix


import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import java.time.LocalDate
import java.time.Period
import java.time.format.DateTimeFormatter

class Calc : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_calc)
        val calculateButton = findViewById<Button>(R.id.calculateButton)
        val birthDateEditText = findViewById<EditText>(R.id.birthDateEditText)
        val ageTextView = findViewById<TextView>(R.id.ageTextView)

        calculateButton.setOnClickListener {
            val birthDateInput = birthDateEditText.text.toString()

            if (birthDateInput.isNotEmpty()) {
                val dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy")
                val currentDate = LocalDate.now()
                val birthDate = LocalDate.parse(birthDateInput, dateFormatter)
                val age = calculateAge(birthDate, currentDate)

                ageTextView.text = "Age: $age year"
            } else {
                ageTextView.text = "Error. Incorrect date of birthday"
            }
        }
    }

    private fun calculateAge(birthDate: LocalDate, currentDate: LocalDate): Int {
        return Period.between(birthDate, currentDate).years
    }
}
