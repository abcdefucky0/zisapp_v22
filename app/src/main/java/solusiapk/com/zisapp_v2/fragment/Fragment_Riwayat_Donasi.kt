package solusiapk.com.zisapp_v2.fragment

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat.startActivity
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import solusiapk.com.zisapp_v2.activity.AuthActivity
import solusiapk.com.zisapp_v2.activity.Muzakki_Activity
import solusiapk.com.zisapp_v2.activity.Profile_Activity
import solusiapk.com.zisapp_v2.activity.Zis_Masuk_Activity
import solusiapk.com.zisapp_v2.adapter.ZisAdapter
import solusiapk.com.zisapp_v2.databinding.FragmentRiwayatDonasiBinding
import solusiapk.com.zisapp_v2.viewmodel.MuzakkiViewModel
import solusiapk.com.zisapp_v2.viewmodel.ZisViewModel
import kotlin.getValue

class Fragment_Riwayat_Donasi : Fragment() {
    private lateinit var firebaseAuth: FirebaseAuth

    private var _binding: FragmentRiwayatDonasiBinding? = null
    private val binding get() = _binding!!

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: ZisAdapter

    //private val viewModel: CampaignViewModel by viewModels() // Jika di Activity
    private val viewModel: ZisViewModel by viewModels() // Jika di Fragment
    private val viewModelmuzakki: MuzakkiViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
              _binding = FragmentRiwayatDonasiBinding.inflate(inflater, container, false)
        return binding.root


    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        // Inisialisasi Firebase Auth
        firebaseAuth = FirebaseAuth.getInstance()
        activity?.title = "Riwayat Setoran ZIS Anda "
        // Cek apakah pengguna sudah login (non-null) dan update UI yang sesuai.
//        val currentUser = auth.currentUser
//        if (currentUser != null) {
//            reloadActivity(currentUser)
//        } else {
//            val intent = Intent(context, Zis_Masuk_Activity ::class.java)
//            startActivity(intent) // Memulai Activity kedua
//        }


        // Periksa status login saat Activity dibuat
        checkCurrentUser()

//        binding.signOutButton.setOnClickListener {
//            signOut()
//        }

        super.onViewCreated(view, savedInstanceState)
//        viewModel.fetchZisRepo()
//
//        recyclerView = binding.recyclerViewrdonasi
//        recyclerView.layoutManager = LinearLayoutManager(requireContext())
//
//        viewModel.zis.observe(viewLifecycleOwner) { products ->
//            adapter = ZisAdapter(products)
//            recyclerView.adapter = adapter
//        }
//
//        viewModel.errorMessage.observe(viewLifecycleOwner) { errorMessage ->
//            Toast.makeText(requireContext(), errorMessage, Toast.LENGTH_SHORT).show()
//        }
    }

    private fun checkCurrentUser() {
        val currentUser = firebaseAuth.currentUser
        if (currentUser == null) {
            // Jika belum login, arahkan ke AuthActivity
            navigateToAuthActivity()
        } else {
            val userEmail: String?
            userEmail = currentUser.email
            if (!userEmail.isNullOrEmpty()) {
                val newemail = userEmail.replace('@', 'g')
                // Toast.makeText(this, newemail, Toast.LENGTH_SHORT).show()
                viewModelmuzakki.fetchMuzakkibyEmail(newemail)

                viewModelmuzakki.user.observeForever { user ->
                    if (user != null) {

                        var kodemuz = user.Kode_Muzakki

                        viewModel.fetczissaya(kodemuz)

                        recyclerView = binding.recyclerViewrdonasi
                        recyclerView.layoutManager = LinearLayoutManager(requireContext())

                        viewModel.zis.observe(viewLifecycleOwner) { products ->
                            adapter = ZisAdapter(products)
                            recyclerView.adapter = adapter
                        }

                        viewModel.errorMessage.observe(viewLifecycleOwner) { errorMessage ->
                            Toast.makeText(requireContext(), errorMessage, Toast.LENGTH_SHORT).show()
                        }

                    } else {
                        val intent = Intent(context, Muzakki_Activity::class.java)
                        startActivity(intent)
                    }
                }

            }

            // Jika sudah login, tampilkan informasi user
           // updateUI(currentUser)
        }
    }


    private fun signOut() {
        firebaseAuth.signOut()
        Toast.makeText(context, "Logged out successfully.", Toast.LENGTH_SHORT).show()
        // Setelah logout, arahkan kembali ke AuthActivity
        navigateToAuthActivity()
    }

    private fun navigateToAuthActivity() {
        val intent = Intent(context, AuthActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK // Hapus stack Activity
        startActivity(intent)
        (context as? Activity)?.finish()
       // finish() // Hapus MainActivity dari stack
    }


    companion object {
        private const val TAG = "Fragment_Campaign"
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}