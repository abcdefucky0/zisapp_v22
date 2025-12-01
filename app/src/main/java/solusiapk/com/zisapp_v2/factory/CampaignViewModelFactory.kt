package solusiapk.com.zisapp_v2.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import solusiapk.com.zisapp_v2.repository.CampaignRepository2
import solusiapk.com.zisapp_v2.viewmodel.CampaignViewModel2
import kotlin.jvm.java

class CampaignViewModelFactory(private val repository: CampaignRepository2) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(CampaignViewModel2::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return CampaignViewModel2(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}