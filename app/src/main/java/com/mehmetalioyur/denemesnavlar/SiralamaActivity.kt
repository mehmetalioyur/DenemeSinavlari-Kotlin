package com.mehmetalioyur.denemesnavlar

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.mehmetalioyur.denemesnavlar.Adapter.SiralamaRecyclerAdapter
import com.mehmetalioyur.denemesnavlar.Model.Siralama
import kotlinx.android.synthetic.main.activity_siralama.*


class SiralamaActivity : AppCompatActivity() {
    private lateinit var database : FirebaseFirestore
    private lateinit var recyclerViewAdapter : SiralamaRecyclerAdapter
    private var siralamaListesi = ArrayList<Siralama>()
    private var denemeAdi :String =""


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_siralama)

        denemeAdi = intent.getStringExtra("denemeAdi").toString()

        database = FirebaseFirestore.getInstance()

    netleriGetir()

        //recyclerView Ayarları
        val layoutManager = LinearLayoutManager(this)
        recyclerView.layoutManager = layoutManager
        recyclerViewAdapter = SiralamaRecyclerAdapter(siralamaListesi)
        recyclerView.adapter  = recyclerViewAdapter

    }

    private fun  netleriGetir (){
// firebaseden aldığım verileri net sayısına göre düşen bir şekilde sırala.
        database.collection("${denemeAdi}SinavSonuclari").orderBy(
            "netSayisi",
            Query.Direction.DESCENDING
        ).addSnapshotListener { value, error ->
            if (error != null){
                Toast.makeText(
                    applicationContext,
                    error.localizedMessage.toString(),
                    Toast.LENGTH_LONG
                ).show()

            }else{

                if (value != null) {
                    val documents = value.documents

                    siralamaListesi.clear()
                    var siraNo = 0

                    documents.forEach { document ->
                        siraNo++
                        val netSayisi = document.get("netSayisi") as Double
                        val userEmail = document.get("isim") as String

                        val indirilenSiralama = Siralama(siraNo, userEmail, netSayisi)
                        siralamaListesi.add(indirilenSiralama)
                    }
                    recyclerViewAdapter.notifyDataSetChanged()
                }
            }

        }

    }

    fun sonucuGorButton(view: View) {
        finish()
    }
    fun yeniTestCozButton(view: View) {
        val intent = Intent(applicationContext, DersSecimiActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        startActivity(intent)
        finish()
            }

}