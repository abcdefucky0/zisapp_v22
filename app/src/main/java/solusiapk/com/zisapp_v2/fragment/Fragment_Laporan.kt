package solusiapk.com.zisapp_v2.fragment

import android.icu.util.Calendar
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import solusiapk.com.zisapp_v2.R
import solusiapk.com.zisapp_v2.adapter.LaporanZisAdapter
import solusiapk.com.zisapp_v2.adapter.ZiscamAdapter
import solusiapk.com.zisapp_v2.databinding.FragmentLaporanBinding
import solusiapk.com.zisapp_v2.datamodel.Laporan_Zis
import solusiapk.com.zisapp_v2.viewmodel.ZisViewModel
import java.text.NumberFormat
import java.util.Locale
import kotlin.getValue

class Fragment_Laporan : Fragment() {
    private var _binding: FragmentLaporanBinding? = null
    private val binding get() = _binding!!
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: LaporanZisAdapter
    private val viewModel: ZisViewModel by viewModels() // Jika di Fragment

    private lateinit var progressBar: ProgressBar
    private lateinit var textError: TextView


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentLaporanBinding.inflate(inflater, container, false)
        return binding.root


    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
//    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        activity?.title = "Laporan Dana ZIS "
        loadsaldo()
        loadlaporanawal()
        loadperiode()
    }

    fun loadsaldo() {
        viewModel.fetchsaldozis()
        viewModel.saldo.observeForever { user ->
            if (user != null) {
                var amountIDR = user.Masuk

                val indonesiaLocale = Locale("id", "ID")
                val currencyFormatterIDR = NumberFormat.getCurrencyInstance(indonesiaLocale)

                binding.tvCurrentBalance.text = "${currencyFormatterIDR.format(amountIDR)}"
            } else {
                binding.tvCurrentBalance.text = "Kosong"
            }

        }
    }

    fun loadperiode() {
        val calendar =
            Calendar.getInstance() // Mendapatkan instance Calendar untuk waktu saat ini

        val dari = calendar.get(Calendar.MONTH) + 1
        val hingga = calendar.get(Calendar.YEAR)

        viewModel.fetchakumulasizisperiode(dari.toString(), hingga.toString())
        viewModel.saldoper.observeForever { user2 ->
            if (user2 != null) {
                var amountIDRmasuk = user2.Masuk
                val amountIDRkeluar = user2.Keluar
                val indonesiaLocale = Locale("id", "ID")
                val currencyFormatterIDR = NumberFormat.getCurrencyInstance(indonesiaLocale)
                binding.tvIncome.text = "${currencyFormatterIDR.format(amountIDRmasuk)}"
                binding.tvExpense.text = "${currencyFormatterIDR.format(amountIDRkeluar)}"
            } else {
                //binding.tvCurrentBalance.text = "Kosong"
            }

        }
    }

    fun loadlaporanawal() {

        progressBar = binding.progressBar
        textError = binding.textError


        // var kodemuz = user.Kode_Muzakki
        val calendar =
            Calendar.getInstance() // Mendapatkan instance Calendar untuk waktu saat ini

        val dari = calendar.get(Calendar.MONTH) + 1
        val hingga = calendar.get(Calendar.YEAR)
        viewModel.fetchlaporanzis(dari.toString(), hingga.toString())

        recyclerView = binding.recyclerViewlaporan
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        viewModel.lapzis.observe(viewLifecycleOwner) { products ->
            adapter = LaporanZisAdapter(products)
            recyclerView.adapter = adapter
        }

//        viewModel.errorMessage.observe(viewLifecycleOwner) { errorMessage ->
//            Toast.makeText(requireContext(), errorMessage, Toast.LENGTH_SHORT).show()


        viewModel.isLoading.observe(viewLifecycleOwner) { isLoading ->
            progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
        }

        viewModel.error.observe(viewLifecycleOwner) { error ->
            if (error != null) {
                textError.text = error
                textError.visibility = View.VISIBLE
            } else {
                textError.visibility = View.GONE
            }

        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}