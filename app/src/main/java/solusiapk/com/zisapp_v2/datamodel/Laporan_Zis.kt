package solusiapk.com.zisapp_v2.datamodel

import java.util.Date

data class Laporan_Zis(
    val Tanggal: String,
    val Referensi: String,
    val Jenis: String,
    val Jenis_Dana: String,
    val Masuk: Int,
    val Keluar: Int,
    val Keterangan: String
)
