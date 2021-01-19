package com.mehmetalioyur.denemesnavlar

import android.content.Intent
import android.os.Bundle
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_denemeler_listesi.*

class DenemelerListesiActivity : AppCompatActivity() {

    private var denemeListesi =ArrayList<String>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_denemeler_listesi)


        val username = intent.getStringExtra("username")
        val dersAdi = intent.getStringExtra("dersAdi")

        // ders adına göre buradaki list viewda gösterilecek öğeler. Bunu firebasedan otomatik ekleyebirim. Araştır!!

        if (dersAdi =="Matematik")
        {
            denemeListesi.add("Matematik Deneme 1")
            denemeListesi.add("Matematik Deneme 2")

        }
        if (dersAdi == "Türkçe")
        {
            denemeListesi.add("Türkçe Deneme 1")
            denemeListesi.add("Türkçe Deneme 2")

        }

        if (dersAdi == "Fen Bilgisi")
        {
            denemeListesi.add("Fen Bilgisi Deneme 1")
            denemeListesi.add("Fen Bilgisi Deneme 2")

        }
        if (dersAdi == "Sosyal Bilgiler") {
            denemeListesi.add("Sosyal Bilgiler Deneme 1")
            denemeListesi.add("Sosyal Bilgiler Deneme 2")
        }
        val adapter = ArrayAdapter(this,R.layout.list_view_row,R.id.dersSecimiButton,denemeListesi)

        deneme_secimi_listView.adapter = adapter
        //QuestionActivitye git. denemeAdi al.
        deneme_secimi_listView.onItemClickListener = AdapterView.OnItemClickListener { parent, view, position, id ->
            val intent = Intent(applicationContext,QuestionsActivity::class.java)
            intent.putExtra("denemeAdi",denemeListesi[position])
            intent.putExtra("username",username)
            startActivity(intent)
            finish()
        }
    }



    }