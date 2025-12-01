package solusiapk.com.zisapp_v2.activity

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import solusiapk.com.zisapp_v2.R
import solusiapk.com.zisapp_v2.databinding.ActivityMainBinding
import solusiapk.com.zisapp_v2.fragment.Fragment_Campaign
import solusiapk.com.zisapp_v2.fragment.Fragment_Laporan
import solusiapk.com.zisapp_v2.fragment.Fragment_Partisipasi
import solusiapk.com.zisapp_v2.fragment.Fragment_Riwayat_Donasi
import solusiapk.com.zisapp_v2.viewmodel.MuzakkiViewModel
import kotlin.jvm.java

class MainActivity : AppCompatActivity() {
    private val viewModel: MuzakkiViewModel by viewModels()
    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        firebaseAuth = FirebaseAuth.getInstance()
        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar) // Mengatur Toolbar kustom sebagai ActionBar

        // Muat fragment awal (HomeFragment) saat aplikasi dimulai
        if (savedInstanceState == null) {
            replaceFragment(Fragment_Campaign())
        }

        // Atur listener untuk BottomNavigationView
        binding.bottomNavigation.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.nav_home -> {
                    replaceFragment(Fragment_Campaign())
                    true
                }
                R.id.nav_partisipasi -> {
                    replaceFragment(Fragment_Partisipasi())
                    true
                }
                R.id.nav_histori -> {
                    replaceFragment(Fragment_Riwayat_Donasi())
                    true
                }

                R.id.nav_laporan -> {
                    replaceFragment(Fragment_Laporan())
                    true
                }

                else -> false
            }
        }




//        setSupportActionBar(binding.toolbar)
//
//        val navController = findNavController(R.id.nav_host_fragment_content_main)
//        appBarConfiguration = AppBarConfiguration(navController.graph)
//        setupActionBarWithNavController(navController, appBarConfiguration)
//
//        binding.fab.setOnClickListener { view ->
//            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                .setAction("Action", null)
//                .setAnchorView(R.id.fab).show()
//        }
    }

    private fun replaceFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, fragment)
            .commit()
    }

    // Metode ini dipanggil untuk membuat menu di ActionBar/Toolbar
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu) // Inflasi menu_main.xml
        return true // Mengembalikan true berarti menu akan ditampilkan
    }

    // Metode ini dipanggil ketika item menu dipilih
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_profile -> {
                // Ketika menu profil diklik
                val currentUser = firebaseAuth.currentUser
                if (currentUser == null) {
                    navigateToAuthActivity()
                } else {
                    updateUI(currentUser)
                }


                //Toast.makeText(this, "Profil diklik!", Toast.LENGTH_SHORT).show()
                // Contoh: Navigasi ke ProfileActivity
                // val intent = Intent(this, ProfileActivity::class.java)
                // startActivity(intent)
                true
            }

            R.id.logout -> {
                // Ketika menu pengaturan diklik
                // Toast.makeText(this, "Pengaturan diklik!", Toast.LENGTH_SHORT).show()
                signOut()
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
                true
            }

            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun signOut() {
        firebaseAuth.signOut()
        Toast.makeText(this, "Logged out successfully.", Toast.LENGTH_SHORT).show()
        // Setelah logout, arahkan kembali ke AuthActivity
        //navigateToAuthActivity()
    }

    private fun updateUI(user: FirebaseUser?) {
        val currentUser = firebaseAuth.currentUser

        if (currentUser != null) {

            val userEmail: String?
            userEmail = currentUser.email
            if (!userEmail.isNullOrEmpty()) {
                val newemail = userEmail.replace('@', 'g')
                // Toast.makeText(this, newemail, Toast.LENGTH_SHORT).show()
                viewModel.fetchMuzakkibyEmail(newemail)
                observeViewModel()
            }

        }
    }

    private fun observeViewModel() {
        viewModel.user.observe(this) { user ->
            if (user != null) {
                var namanya = user.Nama_Muzakki
                var kodemuz = user.Kode_Muzakki
                var jenismuzak = user.Jenis_Muzakki
                val jk = user.JK
                val telepon = user.Telepon
                val alamat = user.Alamat

                // Intent untuk membuka Activity baru
                val intent = Intent(this, Profile_Activity::class.java)

                // Mengirim data ke Activity baru (opsional)
                intent.putExtra("namamuzakki", namanya)
                intent.putExtra("Kodemuz", kodemuz)
                intent.putExtra("jenismuz", jenismuzak)
                intent.putExtra("jk", jk)
                intent.putExtra("telepon", telepon)
                intent.putExtra("alamat", alamat)

                startActivity(intent)
            } else {
                val intent = Intent(this, Muzakki_Activity::class.java)
                startActivity(intent)
            }
        }

//        viewModel.errorMessage.observe(this) { errorMessage ->
//            if (errorMessage.isNotEmpty()) {
//                errorTextView.text = errorMessage
//                errorTextView.visibility = View.VISIBLE
//                Toast.makeText(this, errorMessage, Toast.LENGTH_LONG).show()
//            } else {
//                errorTextView.visibility = View.GONE
//            }
//        }
//
//        viewModel.isLoading.observe(this) { isLoading ->
//            progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
//            fetchUserButton.isEnabled = !isLoading
//        }
    }

    private fun navigateToAuthActivity() {
        val intent = Intent(this, AuthActivity::class.java)
        intent.flags =
            Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK // Hapus stack Activity
        startActivity(intent)
        (this as? Activity)?.finish()
        // finish() // Hapus MainActivity dari stack
    }
//    override fun onCreateOptionsMenu(menu: Menu): Boolean {
//        // Inflate the menu; this adds items to the action bar if it is present.
//        menuInflater.inflate(R.menu.menu_main, menu)
//        return true
//    }
//
//    override fun onOptionsItemSelected(item: MenuItem): Boolean {
//        // Handle action bar item clicks here. The action bar will
//        // automatically handle clicks on the Home/Up button, so long
//        // as you specify a parent activity in AndroidManifest.xml.
//        return when (item.itemId) {
//            R.id.action_settings -> true
//            else -> super.onOptionsItemSelected(item)
//        }
//    }
//
//    override fun onSupportNavigateUp(): Boolean {
//        val navController = findNavController(R.id.nav_host_fragment_content_main)
//        return navController.navigateUp(appBarConfiguration)
//                || super.onSupportNavigateUp()
//    }
}