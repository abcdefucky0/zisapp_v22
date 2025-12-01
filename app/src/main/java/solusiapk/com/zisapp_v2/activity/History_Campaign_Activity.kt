package solusiapk.com.zisapp_v2.activity

import android.os.Bundle
import android.view.View
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import solusiapk.com.zisapp_v2.R
import solusiapk.com.zisapp_v2.databinding.ActivityHistoryCampaignBinding
import solusiapk.com.zisapp_v2.viewmodel.ZisViewModel
import androidx.activity.viewModels
import androidx.recyclerview.widget.RecyclerView
import solusiapk.com.zisapp_v2.adapter.HistoryCampaignAdapter
import solusiapk.com.zisapp_v2.adapter.ZisAdapter
import solusiapk.com.zisapp_v2.databinding.ActivityMainBinding
import solusiapk.com.zisapp_v2.databinding.FragmentRiwayatDonasiBinding

class History_Campaign_Activity : AppCompatActivity() {
    private lateinit var binding: ActivityHistoryCampaignBinding

    // private val binding get() = _binding

    private val viewModel: ZisViewModel by viewModels()
    private lateinit var recyclerView: RecyclerView
    private lateinit var progressBar: ProgressBar
    private lateinit var textError: TextView
    private lateinit var adapter: HistoryCampaignAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHistoryCampaignBinding.inflate(layoutInflater)
        setContentView(binding.root)

        recyclerView = binding.recyclerViewhistory
        recyclerView.layoutManager = LinearLayoutManager(this)

       var judul = intent.getStringExtra("NAMA_EXTRA")
        judul?. let{
            binding.judulhis.text = "Progress " + judul
        }
        var kodenya = intent.getStringExtra("Kodepilih")

        viewModel.fetchistorycampaign(kodenya.toString())
        viewModel.hiscam.observe(this) { products ->
            adapter = HistoryCampaignAdapter(products)
            recyclerView.adapter = adapter
        }

        viewModel.errorMessage.observe(this) { errorMessage ->
            Toast.makeText(this, errorMessage, Toast.LENGTH_SHORT).show()
        }
    }
}
