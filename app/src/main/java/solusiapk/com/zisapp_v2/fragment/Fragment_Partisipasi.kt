package solusiapk.com.zisapp_v2.fragment

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import solusiapk.com.zisapp_v2.R
import solusiapk.com.zisapp_v2.activity.AuthActivity
import solusiapk.com.zisapp_v2.activity.Muzakki_Activity
import solusiapk.com.zisapp_v2.adapter.ZiscamAdapter
import solusiapk.com.zisapp_v2.databinding.FragmentPartisipasiBinding
import solusiapk.com.zisapp_v2.viewmodel.MuzakkiViewModel
import solusiapk.com.zisapp_v2.viewmodel.ZisViewModel
import kotlin.getValue


class Fragment_Partisipasi : Fragment() {
    private var _binding: FragmentPartisipasiBinding? = null
    private val binding get() = _binding!!

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: ZiscamAdapter

    //private val viewModel: CampaignViewModel by viewModels() // Jika di Activity
    private val viewModel: ZisViewModel by viewModels() // Jika di Fragment
    private val viewModelmuzakki: MuzakkiViewModel by viewModels()

    private lateinit var firebaseAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        firebaseAuth = FirebaseAuth.getInstance()
        checkCurrentUser()
        activity?.title = "Riwayat Partisipasi Pada Campaign"
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentPartisipasiBinding.inflate(inflater, container, false)
        return binding.root
    }

    companion object {
        private const val TAG = "Fragment_Campaign"
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

                        viewModel.fetcdonasisaya(kodemuz)

                        recyclerView = binding.recyclerViewrdonasizm
                        recyclerView.layoutManager = LinearLayoutManager(requireContext())

                        viewModel.ziscam.observe(viewLifecycleOwner) { products ->
                            adapter = ZiscamAdapter(products)
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


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}