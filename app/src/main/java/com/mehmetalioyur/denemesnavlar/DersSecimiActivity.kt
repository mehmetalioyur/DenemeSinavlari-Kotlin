package com.mehmetalioyur.denemesnavlar

import android.content.Intent
import android.os.Bundle
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_ders_secimi.*

class DersSecimiActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ders_secimi)

        val dersListesi = ArrayList<String>()   // ders isimlerini oluştur.
        dersListesi.add("Türkçe")
        dersListesi.add("Matematik")
        dersListesi.add("Fen Bilgisi")
        dersListesi.add("Sosyal Bilgiler")

        val adapter = ArrayAdapter(this,R.layout.list_view_row,R.id.dersSecimiButton,dersListesi)

        ders_listesi_list.adapter = adapter
        // DenemelerListesi Aktiviteye Geç. dersAdi nı indexten al. !Aktiveyi kapatmadım. Geri tuşuna basıldığında buraya gelinsin.
        ders_listesi_list.onItemClickListener = AdapterView.OnItemClickListener { parent , view, position, id ->
            val intent = Intent(applicationContext,DenemelerListesiActivity::class.java)
            intent.putExtra("dersAdi",dersListesi[position])
            startActivity(intent)
        }
    }

}