package solusiapk.com.zisapp_v2.datamodel

data class ZisMasuk_Respon(
    var status: String,
    var message: String,
    var data: ZisMasuk? // Anda bisa ganti User dengan model data yang lebih spesifik
)
