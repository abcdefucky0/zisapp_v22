package solusiapk.com.zisapp_v2.activity

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthUserCollisionException
import com.google.firebase.auth.FirebaseAuthWeakPasswordException
import solusiapk.com.zisapp_v2.databinding.ActivityAuthBinding
import kotlin.jvm.java

class AuthActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAuthBinding
    private lateinit var firebaseAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAuthBinding.inflate(layoutInflater)
        setContentView(binding.root)

        firebaseAuth = FirebaseAuth.getInstance()

        binding.loginButton.setOnClickListener {
            loginUser()
        }

        binding.registerButton.setOnClickListener {
            registerUser()
        }

        binding.textForgotPassword.setOnClickListener {
            resetpassword()
        }
    }

    fun resetpassword() {
        val intent = Intent(this, ResetPasswordActivity ::class.java)
        intent.flags =
            Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK // Hapus stack Activity
        startActivity(intent)
        finish() // Hapus AuthActivity dari stack
    }

    private fun loginUser() {
        val email = binding.emailEditText.text.toString().trim()
        val password = binding.passwordEditText.text.toString().trim()

        if (email.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, "Email and password cannot be empty.", Toast.LENGTH_SHORT).show()
            return
        }

        firebaseAuth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    Toast.makeText(this, "Login successful!", Toast.LENGTH_SHORT).show()
                    navigateToMainActivity()
                } else {
                    // Penanganan error login
                    val errorMessage = when (task.exception) {
                        is FirebaseAuthInvalidCredentialsException -> "Invalid credentials. Please check your email and password."
                        else -> "Login failed: ${task.exception?.message}"
                    }
                    Toast.makeText(this, errorMessage, Toast.LENGTH_LONG).show()
                }
            }
    }

    private fun registerUser() {
        val email = binding.emailEditText.text.toString().trim()
        val password = binding.passwordEditText.text.toString().trim()

        if (email.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, "Email and password cannot be empty.", Toast.LENGTH_SHORT).show()
            return
        }

        firebaseAuth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    Toast.makeText(this, "Registration successful!", Toast.LENGTH_SHORT).show()
                    // Setelah register, bisa langsung login atau biarkan user login manual
                    navigateToMainActivity()
                } else {
                    // Penanganan error register
                    val errorMessage = when (task.exception) {
                        is FirebaseAuthWeakPasswordException -> "Password is too weak. Please choose a stronger password."
                        is FirebaseAuthInvalidCredentialsException -> "Email is invalid or already in use."
                        is FirebaseAuthUserCollisionException -> "This email is already registered. Please login instead."
                        else -> "Registration failed: ${task.exception?.message}"
                    }
                    Toast.makeText(this, errorMessage, Toast.LENGTH_LONG).show()
                }
            }
    }

    private fun navigateToMainActivity() {
        val intent = Intent(this, MainActivity::class.java)
        intent.flags =
            Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK // Hapus stack Activity
        startActivity(intent)
        finish() // Hapus AuthActivity dari stack
    }
}