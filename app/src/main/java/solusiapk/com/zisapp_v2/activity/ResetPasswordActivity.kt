package solusiapk.com.zisapp_v2.activity

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ProgressBar
import android.widget.Toast
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.auth.FirebaseAuth
import solusiapk.com.zisapp_v2.R
import solusiapk.com.zisapp_v2.databinding.ActivityResetPasswordBinding

class ResetPasswordActivity : AppCompatActivity() {
    private lateinit var binding: ActivityResetPasswordBinding
    private lateinit var auth: FirebaseAuth
    private lateinit var etEmail: TextInputEditText
    private lateinit var btnResetPassword: Button
    private lateinit var progressBar: ProgressBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityResetPasswordBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Initialize Firebase Auth
        auth = FirebaseAuth.getInstance()

        // Initialize UI components
        etEmail = binding.etEmail
        btnResetPassword = binding.btnResetPassword
        progressBar = binding.progressBar

        btnResetPassword.setOnClickListener {
            resetPassword()
        }
    }

    private fun resetPassword() {
        val email = etEmail.text.toString().trim()

        if (email.isEmpty()) {
            etEmail.error = "Email diperlukan!"
            etEmail.requestFocus()
            return
        }

        // Show progress bar
        progressBar.visibility = View.VISIBLE
        btnResetPassword.isEnabled = false // Disable button to prevent multiple clicks

        auth.sendPasswordResetEmail(email)
            .addOnCompleteListener { task ->
                // Hide progress bar
                progressBar.visibility = View.GONE
                btnResetPassword.isEnabled = true // Re-enable button

                if (task.isSuccessful) {
                    Toast.makeText(
                        this,
                        "Tautan reset kata sandi telah dikirim ke email Anda. Silakan periksa kotak masuk Anda.",
                        Toast.LENGTH_LONG
                    ).show()
                    finish() // Close this activity and go back to the previous one (e.g., login)
                } else {
                    Toast.makeText(
                        this,
                        "Gagal mengirim tautan reset. Pastikan email Anda terdaftar. Error: ${task.exception?.message}",
                        Toast.LENGTH_LONG
                    ).show()
                }
            }
    }
//    override fun onSupportNavigateUp(): Boolean {
//        val navController = findNavController(R.id.nav_host_fragment_content_reset_password)
//        return navController.navigateUp(appBarConfiguration)
//                || super.onSupportNavigateUp()
//    }
}