package solusiapk.com.zisapp_v2.datamodel

data class ZisMasuk(
    val Nmr_Terima: String?,
    val Tanggal: String,
    val Nmr_Jurnal: String,
    val Jenis: String,
    val Muzakki: String,
    val Kode_Program: String?,
    val Keterangan: String,
    val Nominal: Double?,
    val Mode_Pembayaran: String,
    val Status: String,
    val EDP: String,
    val Hash_Code: String,
    val Kode_Dana: String,
    val Kode_Campaign: String?
)
