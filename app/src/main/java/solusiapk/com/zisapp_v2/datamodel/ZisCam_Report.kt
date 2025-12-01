package solusiapk.com.zisapp_v2.datamodel

data class ZisCam_Report(
    val Nmr_Terima: String?,
    val Tanggal: String,
    val Jenis: String,
    val Muzakki: String,
    val Kode_Program: String?,
    val Keterangan: String,
    val Nominal: Double,
    val Mode_Pembayaran: String,
    val Status: String,
    val EDP: String,
    val Kode_Dana: String,
    val title: String
)
