package solusiapk.com.zisapp_v2.fragment

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat.startActivity
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import solusiapk.com.zisapp_v2.R
import solusiapk.com.zisapp_v2.activity.AuthActivity
import solusiapk.com.zisapp_v2.activity.Campaign_Create_Activity
import solusiapk.com.zisapp_v2.activity.MainActivity
import solusiapk.com.zisapp_v2.activity.Muzakki_Activity
import solusiapk.com.zisapp_v2.activity.Upload_Bukti_Activity
import solusiapk.com.zisapp_v2.activity.Zis_Masuk_Activity
import solusiapk.com.zisapp_v2.adapter.CampaignAdapter
import solusiapk.com.zisapp_v2.adapter.PhotoSlideAdapter
import solusiapk.com.zisapp_v2.databinding.FragmentCampaignBinding
import solusiapk.com.zisapp_v2.viewmodel.CampaignViewModel
import solusiapk.com.zisapp_v2.viewmodel.MuzakkiViewModel
import kotlin.getValue
import kotlin.jvm.java

class Fragment_Campaign : Fragment() {
    private lateinit var firebaseAuth: FirebaseAuth
    private var _binding: FragmentCampaignBinding? = null
    private val binding get() = _binding!!

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: CampaignAdapter

    //private val viewModel: CampaignViewModel by viewModels() // Jika di Activity
    private val viewModel: CampaignViewModel by viewModels() // Jika di Fragment
    private val viewmodelmuzakki: MuzakkiViewModel by viewModels()

    //    private lateinit var progressBar: ProgressBar
//    private lateinit var textError: TextView
    private lateinit var fabAddPost: FloatingActionButton
    private lateinit var btnsalurkan: Button


    private lateinit var viewPagerPhoto: ViewPager2
    private lateinit var tabLayoutIndicator: TabLayout

//    private lateinit var bottomNavigationView: BottomNavigationView
//    private lateinit var toolbar: Toolbar
//    private lateinit var profileIcon: ImageView
//    private lateinit var toolbarTitle: TextView // Tambahkan ini untuk judul Toolbar

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        // return inflater.inflate(R.layout.fragment__campaign, container, false)
        _binding = FragmentCampaignBinding.inflate(inflater, container, false)
        return binding.root

    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        activity?.title = "Bakti Bersama Zisapp"
        firebaseAuth = FirebaseAuth.getInstance()

        super.onViewCreated(view, savedInstanceState)
        viewModel.fetchCampaignsRepo()

        fabAddPost = binding.fabAddPost

        recyclerView = binding.recyclerView
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        viewModel.campaings.observe(viewLifecycleOwner) { products ->
            adapter = CampaignAdapter(products)
            recyclerView.adapter = adapter
        }

        viewModel.errorMessage.observe(viewLifecycleOwner) { errorMessage ->
            Toast.makeText(requireContext(), errorMessage, Toast.LENGTH_SHORT).show()
        }

        binding.btnsalurkanzis.setOnClickListener {
            val currentUser = firebaseAuth.currentUser
            if (currentUser == null) {
                navigateToAuthActivity()
            } else {
                updateUI(currentUser)
            }

//            val intent = Intent(context, Zis_Masuk_Activity ::class.java)
//            startActivity(intent) // Memulai Activity kedua
        }

        viewPagerPhoto = binding.viewPagerPhoto
        tabLayoutIndicator = binding.tabLayoutIndicator


        // Contoh data gambar (gunakan gambar yang ada di res/drawable Anda)
        val photoList = listOf(
            R.drawable.gambar1, // Pastikan Anda memiliki gambar ini di res/drawable
            R.drawable.gambar2,
            R.drawable.gambar3,
            R.drawable.gambar4
        )

        val adapter = PhotoSlideAdapter(photoList)
        viewPagerPhoto.adapter = adapter

        // Menghubungkan TabLayout dengan ViewPager2 untuk indikator
        TabLayoutMediator(tabLayoutIndicator, viewPagerPhoto) { tab, position ->
            // Anda bisa mengosongkan ini jika hanya ingin indikator default
            // Atau atur teks/ikon jika diperlukan
        }.attach()

        fabAddPost.setOnClickListener {
            val currentUser = firebaseAuth.currentUser
            if (currentUser == null) {
                navigateToAuthActivity()
            } else {
                val userEmail: String?
                userEmail = currentUser.email
                if (!userEmail.isNullOrEmpty()) {
                    val newemail = userEmail.replace('@', 'g')
                    // Toast.makeText(this, newemail, Toast.LENGTH_SHORT).show()
                    viewmodelmuzakki.fetchMuzakkibyEmail(newemail)
                    viewmodelmuzakki.user.observe(viewLifecycleOwner) { user ->
                        if (user != null) {
                            val intent =
                                Intent(requireContext(), Campaign_Create_Activity::class.java)
                            intent.flags =
                                Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK // Hapus stack Activity
                            startActivity(intent)
                            activity?.finish()

//                            val intent =
//                                Intent(requireContext(), Upload_Bukti_Activity::class.java)
//                            intent.flags =
//                                Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK // Hapus stack Activity
//                            startActivity(intent)
//                            activity?.finish()
                        }
                    }
                }


                //updateUI(currentUser)
//                val intent = Intent(context, Campaign_Create_Activity::class.java)
//                intent.flags =
//                    Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK // Hapus stack Activity
//                startActivity(intent)
//                (this as? Activity)?.finish()
            }
        }
    }

    private fun updateUI(user: FirebaseUser?) {
        val currentUser = firebaseAuth.currentUser

        if (currentUser != null) {

            val userEmail: String?
            userEmail = currentUser.email
            if (!userEmail.isNullOrEmpty()) {
                val newemail = userEmail.replace('@', 'g')
                // Toast.makeText(this, newemail, Toast.LENGTH_SHORT).show()
                viewmodelmuzakki.fetchMuzakkibyEmail(newemail)

                viewmodelmuzakki.user.observeForever { user ->
                    if (user != null) {
                        var namanya = user.Nama_Muzakki
                        var kodemuz = user.Kode_Muzakki
                        var telepon = user.Telepon

                        val intent = Intent(context, Zis_Masuk_Activity::class.java)
                        intent.putExtra("namamuzakki", namanya)
                        intent.putExtra("Kodemuz", kodemuz)
                        intent.putExtra("telepon", telepon)
                        startActivity(intent) // Memulai Activity kedua
                    } else {
                        val intent = Intent(context, Muzakki_Activity::class.java)
                        startActivity(intent)
                    }
                }
            }

        }
    }

    private fun navigateToAuthActivity() {
        val intent = Intent(context, AuthActivity::class.java)
        intent.flags =
            Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK // Hapus stack Activity
        startActivity(intent)
        (this as? Activity)?.finish()
        // finish() // Hapus MainActivity dari stack
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

//    private val handler = Handler(Looper.getMainLooper())
//    private val runnable = object : Runnable {
//        override fun run() {
//            if (viewPagerPhoto.currentItem == photoList.size - 1) {
//                viewPagerPhoto.currentItem = 0
//            } else {
//                viewPagerPhoto.currentItem = viewPagerPhoto.currentItem + 1
//            }
//            handler.postDelayed(this, 3000) // Ganti 3000 dengan interval slide dalam ms
//        }
//    }

//    override fun onResume() {
//        super.onResume()
//        handler.postDelayed(runnable, 3000)
//    }
//
//    override fun onPause() {
//        super.onPause()
//        handler.removeCallbacks(runnable)
//    }
}