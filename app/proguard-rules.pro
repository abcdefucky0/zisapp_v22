# Ini adalah file konfigurasi ProGuard.
# Anda bisa menambahkan aturan kustom di sini.

# Menggunakan aturan optimasi default dari Android SDK
#-include proguard-android-optimize.txt

# Aturan umum untuk Kotlin
-keep class kotlin.** { *; }
-keep class kotlin.Metadata { *; }
-keepattributes Signature
-keepattributes InnerClasses
-keepattributes SourceFile
-keepattributes LineNumberTable


# Aturan untuk menjaga kelas yang diakses melalui refleksi atau injeksi dependensi
# Ganti 'com.yourcompany.yourapp.di.modules' dengan paket yang sesuai
-keep class solusiapk.com.zisapp_v2.** { *; }

# --- ATURAN KHUSUS UNTUK DEPENDENSI PROYEK KAMU ---
# Keep semua kelas di package yang berisi kode custom Anda, termasuk data model, repository, viewmodel, dll.
-keep class solusiapk.com.zisapp_v2.** { *; }
# Keep semua Activity, Fragment
-keep class solusiapk.com.zisapp_v2.activity.** { *; }
-keep class solusiapk.com.zisapp_v2.fragment.** { *; }

# Keep semua data model (penting untuk Gson/Retrofit/Firebase)
-keep class solusiapk.com.zisapp_v2.datamodel.** { *; }

# Keep API interface Retrofit
-keep interface solusiapk.com.zisapp_v2.api.** { *; }

# Keep Repository & ViewModel
-keep class solusiapk.com.zisapp_v2.repository.** { *; }
-keep class solusiapk.com.zisapp_v2.viewmodel.** { *; }

# Keep Factory & Adapter (RecyclerView, ViewPager, dsb.)
-keep class solusiapk.com.zisapp_v2.factory.** { *; }
-keep class solusiapk.com.zisapp_v2.adapter.** { *; }
-keep class solusiapk.com.zisapp_v2.viewholder.** { *; }

# Keep semua annotation (SerializedName, @Keep, dll.)
-keepattributes *Annotation*

## ==== KEEP ALL PROJECT CLASSES ====
#-keep class solusiapk.com.zisapp_v2.activity.** { *; }
#-keep class solusiapk.com.zisapp_v2.adapter.** { *; }
#-keep class solusiapk.com.zisapp_v2.api.** { *; }
#-keep class solusiapk.com.zisapp_v2.datamodel.** { *; }
#-keep class solusiapk.com.zisapp_v2.factory.** { *; }
#-keep class solusiapk.com.zisapp_v2.fragment.** { *; }
#-keep class solusiapk.com.zisapp_v2.repository.** { *; }
#-keep class solusiapk.com.zisapp_v2.until.** { *; }
#-keep class solusiapk.com.zisapp_v2.viewholder.** { *; }
#-keep class solusiapk.com.zisapp_v2.viewmodel.** { *; }


##############################################################
# ==== ANDROIDX CORE, APPCOMPAT, MATERIAL, CONSTRAINT LAYOUT ====
##############################################################
-keep class androidx.core.** { *; }
-dontwarn androidx.core.**
-keep class androidx.appcompat.** { *; }
-dontwarn androidx.appcompat.**
-keep class com.google.android.material.** { *; }
-dontwarn com.google.android.material.**
-keep class androidx.constraintlayout.** { *; }
-dontwarn androidx.constraintlayout.**

##############################################################
# ==== VIEW / UI COMPONENTS ====
##############################################################
-keep class androidx.coordinatorlayout.** { *; }
-dontwarn androidx.coordinatorlayout.**
-keep class androidx.recyclerview.** { *; }
-dontwarn androidx.recyclerview.**
-keep class androidx.cardview.** { *; }
-dontwarn androidx.cardview.**
-keep class androidx.viewpager2.** { *; }
-dontwarn androidx.viewpager2.**
-keep class com.google.android.flexbox.** { *; }
-dontwarn com.google.android.flexbox.**

##############################################################
# ==== NAVIGATION & FRAGMENT ====
##############################################################
-keep class androidx.navigation.** { *; }
-dontwarn androidx.navigation.**
-keep class androidx.fragment.** { *; }
-dontwarn androidx.fragment.**

##############################################################
# ==== LIFECYCLE (ViewModel & LiveData) ====
##############################################################
-keep class androidx.lifecycle.** { *; }
-dontwarn androidx.lifecycle.**
-keep class androidx.activity.** { *; }
-dontwarn androidx.activity.**

##############################################################
# ==== RETROFIT & OKHTTP ====
##############################################################
#-keep class retrofit2.** { *; }
#-dontwarn retrofit2.**
#-keepattributes Signature
#-keepattributes *Annotation*
#
#-keep class okhttp3.** { *; }
#-dontwarn okhttp3.**
#-keep interface okhttp3.** { *; }
# Keep retrofit interface
-keep interface * {
    @retrofit2.http.* <methods>;
}

# Keep Gson annotations
-keepclassmembers class * {
    @com.google.gson.annotations.SerializedName <fields>;
}

##############################################################
# ==== COROUTINES ====
##############################################################
-keep class kotlinx.coroutines.** { *; }
-dontwarn kotlinx.coroutines.**

##############################################################
# ==== GLIDE ====
##############################################################
#-keep class com.bumptech.glide.** { *; }
#-dontwarn com.bumptech.glide.**
#-keep class com.bumptech.glide.annotation.GlideModule
#-keep public class * extends com.bumptech.glide.module.AppGlideModule
#-keep public enum com.bumptech.glide.load.ImageHeaderParser$** { **[] $VALUES; public *; }
-keep public class * implements com.bumptech.glide.module.GlideModule
#-keep public class * extends com.bumptech.glide.AppGlideModule
-keep public enum com.bumptech.glide.load.ImageHeaderParser$** { *; }

##############################################################
# ==== FIREBASE & GOOGLE AUTH ====
##############################################################
#-keep class com.google.firebase.** { *; }
#-dontwarn com.google.firebase.**
#-keep class com.google.android.gms.** { *; }
#-dontwarn com.google.android.gms.**
#-keep class androidx.credentials.** { *; }
#-dontwarn androidx.credentials.**

-keep class com.google.firebase.** { *; }
-keep class com.google.android.gms.** { *; }

##############################################################
# ==== JSON PARSING (GSON) ====
##############################################################
-keep class com.google.gson.** { *; }
-dontwarn com.google.gson.**
-keepclassmembers class * {
    @com.google.gson.annotations.SerializedName <fields>;
}
-keepattributes Signature
-keepattributes *Annotation*

##############################################################
# ==== JUNIT & TESTING ====
##############################################################
-dontwarn org.junit.**
-dontwarn androidx.test.**

##############################################################
# ==== KEEP ALL MODEL CLASSES ====
##############################################################

