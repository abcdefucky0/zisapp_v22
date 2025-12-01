package solusiapk.com.zisapp_v2.datamodel

data class Campaign(
//    val Kode_Muzakki: String?,
//    val Tanggal: String?,
//    val Nama_Penerima: String?,
//    val Jenis_Bantuan: String?,
//    val Besar_Bantuan: Int?,
//    val Deskripsi_Singkat: String?,
//    val Status: String?,

    val id: Int,
    val slug: String,
    val title: String,
    val description: String,
    val donasiterkumpul: Int?,
    val start: String?,
    val end: String?,
    val target: Int?,
    val status: String?,
    val image: String?,
    val category_id: Int?,
    val user_id: Int?,
    val create_at: String?,
    val update_at: String?,
    val agency_id: Int?,
    val delete_at: String?
)
