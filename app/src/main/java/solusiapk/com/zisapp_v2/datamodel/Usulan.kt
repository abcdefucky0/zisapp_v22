package solusiapk.com.zisapp_v2.datamodel

data class Usulan(
    val Kode_Muzakki: String?,
    val Tanggal: String?,
    val Nama_Penerima: String?,
    val Jenis_Bantuan: String?,
    val Besar_Bantuan: Int?,
    val Deskripsi_Singkat: String?,
    val Status: String?,
    val Alamat: String,
    val Foto1: String,
    val Foto2: String,
    val Foto3: String
)
