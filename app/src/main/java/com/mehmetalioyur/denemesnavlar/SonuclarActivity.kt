package com.mehmetalioyur.denemesnavlar

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_sonuclar.*
import kotlin.math.ceil
import kotlin.math.floor

class SonuclarActivity : AppCompatActivity() {
    private val db = Firebase.firestore

    private var netSayisi: Double = 0.0
    private var index = 0
    private var dogruCevapSayisi = 0
    private var bosCevapSayisi = 0
    private var yanlisCevapSayisi = 0
    private var denemeAdi :String = ""

    private var kullaniciCevabi =ArrayList<Int>()
    private var dogruCevap = ArrayList<Int>()
    private lateinit var isimText : String

    private lateinit var soruAdi : ArrayList<String>
    private lateinit var aSikki : ArrayList<String>
    private lateinit var bSikki : ArrayList<String>
    private lateinit var cSikki : ArrayList<String>
    private lateinit var dSikki : ArrayList<String>
    private lateinit var fotograf : ArrayList<String>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sonuclar)

        // Cevapları incele activitye göndereceğim.
        soruAdi = intent.getStringArrayListExtra("soruAdi") as ArrayList<String>
        aSikki = intent.getStringArrayListExtra("aSikki") as ArrayList<String>
        bSikki = intent.getStringArrayListExtra("bSikki") as ArrayList<String>
        cSikki = intent.getStringArrayListExtra("cSikki") as ArrayList<String>
        dSikki = intent.getStringArrayListExtra("dSikki") as ArrayList<String>
        fotograf = intent.getStringArrayListExtra("fotograf") as ArrayList<String>

        val sharedPreferences = this.getSharedPreferences("com.mehmetalioyur.denemesnavlar", Context.MODE_PRIVATE)
        isimText = sharedPreferences.getString("username", "").toString()

    //     isimText = intent.getStringExtra("username")!!
        kullaniciCevabi = intent.getIntegerArrayListExtra("yollananKullaniciCevabi")!!
        this.dogruCevap = intent.getIntegerArrayListExtra("dogruCevaplar")!!
        denemeAdi = intent.getStringExtra("denemeAdi").toString()

//Doğru cevap boyutuna gelene kadar doğru-yanlış-boş int değerlerini duruma arttır.
        if (dogruCevap != null) {
            while (index < dogruCevap.size) {
                when {
                    kullaniciCevabi[index] == dogruCevap[index] -> {

                        dogruCevapSayisi++
                    }
                    kullaniciCevabi[index] == 5 -> {
                        bosCevapSayisi++
                    }
                    kullaniciCevabi[index] != dogruCevap[index] -> {
                        yanlisCevapSayisi++

                    }
                }
                index++
            }
        }
        // Değerleri yazdır.
        soru_sayisi.text = ("Toplam Soru Sayısı :${dogruCevap.size}")
        dogru_cevap.text = ("Doğru Cevap Sayısı :${dogruCevapSayisi}")
        yanlis_cevao.text = ("Yanlış Cevap Sayısı :${yanlisCevapSayisi}")
        bos_cevap.text = ("Boş Cevap Sayısı :${bosCevapSayisi}")

// net sayısı virgülden sonra sonsuza gittiği için floor ve ceil fonksiyonunu kullandım.
// floor ve ceil çalışma mantığı-> virgülden sonrasını almıyor. Virgüldeken sonraki değere ceil 1 ekliyor. floor 1 çıkarıyor.
        netSayisi = dogruCevapSayisi.toDouble() - yanlisCevapSayisi.toDouble()/3
        netSayisi = if (netSayisi >= 0) {
            floor(netSayisi * 100) / 100
        } else {
            ceil(netSayisi * 100) / 100

        }
        net_sayisi.text = "Net Sayısı $netSayisi"

    /// firestore'a net gönder
        val postMap = hashMapOf<String,Any>()
        postMap["netSayisi"] = netSayisi
        postMap["isim"] = isimText
        db.collection("${denemeAdi}SinavSonuclari").add(postMap).addOnCompleteListener {

            if(it.isSuccessful && it.isComplete)  {
               // finish()// geri dön. yanlış olabilir.

            }

        }.addOnFailureListener {
            Toast.makeText(this,it.localizedMessage.toString(),Toast.LENGTH_LONG).show()
        }




    }

    fun siralamayaGitButton(view: View){    // SiralamaActicity

        val intentSiralamaActivity = Intent(this, SiralamaActivity::class.java)
        intentSiralamaActivity.putExtra("denemeAdi",denemeAdi)
        startActivity(intentSiralamaActivity)
    }
    fun cevaplariInceleActivityGit(view: View){   // CevaplarıInceleActivity

        val intentCevaplariInceleActivity = Intent(this, CevaplariInceleActivity::class.java)
        intentCevaplariInceleActivity.putStringArrayListExtra("soruAdi",soruAdi)
        intentCevaplariInceleActivity.putStringArrayListExtra("aSikki",aSikki)
        intentCevaplariInceleActivity.putStringArrayListExtra("bSikki",bSikki)
        intentCevaplariInceleActivity.putStringArrayListExtra("cSikki",cSikki)
        intentCevaplariInceleActivity.putStringArrayListExtra("dSikki",dSikki)
        intentCevaplariInceleActivity.putStringArrayListExtra("fotograf",fotograf)
        intentCevaplariInceleActivity.putIntegerArrayListExtra("kullaniciCevabi",kullaniciCevabi)
        intentCevaplariInceleActivity.putIntegerArrayListExtra("dogruCevap",dogruCevap)
        startActivity(intentCevaplariInceleActivity)

    }
    fun yeniTestCoz(view: View){ // bu aktiviteyi destroy etmem gerekiyor. yeni cevaplar için.
        val intentYeniTestCoz = Intent(this,DersSecimiActivity::class.java)
        startActivity(intentYeniTestCoz)
        finish()


    }


}