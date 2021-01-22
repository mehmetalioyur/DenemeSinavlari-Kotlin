package com.mehmetalioyur.denemesnavlar

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.os.Handler
import android.view.View
import android.widget.RadioButton
import android.widget.Toast
import com.google.firebase.database.*
import com.mehmetalioyur.denemesnavlar.Model.DataDTO
import com.squareup.picasso.Picasso

import kotlinx.android.synthetic.*
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_questions.*
import kotlinx.android.synthetic.main.activity_questions.view.*
import java.util.*
import kotlin.collections.ArrayList

class QuestionsActivity : AppCompatActivity() {
    var secimArrayList = arrayListOf<Int?>()
    var constructordanCek = ArrayList<FirebasedanGelenVerileriAl>()
    var dogruCevabiCek = ArrayList<Int>()
   // diğer aktivitede göstermem gerekiyor
    var soruAdiCek = ArrayList<String>()
    var aSikkiCek = ArrayList<String>()
    var bSikkiCek = ArrayList<String>()
    var cSikkiCek = ArrayList<String>()
    var dSikkiCek = ArrayList<String>()
    var fotografCek = ArrayList<String>()


    private lateinit var database : DatabaseReference
    private var index = 0
    private var secimArrayIndex = 0
    private var secim: Int = 5

    var denemeAdi: String? = null

     override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_questions)

        denemeAdi = intent.getStringExtra("denemeAdi")

        verileriAl()

        // Beklemeden soruları göstermeye çalıştığımda uygulama çöküyor. 4 Saniye uygun. Fotoğrafları da onCreatede çekebilirim. Araştır !!
        val handler = Handler()
        val runnable = Runnable { }
        object : CountDownTimer(4000, 1000) {
            override fun onTick(p0: Long) {
                soruYukleniyor.text ="Sorular Yükleniyor. Lütfen ${p0/1000} Saniye Bekleyiniz"
                scrollView.visibility = View.GONE
                linearLayout.visibility = View.GONE
                tekrar_dene_button.visibility = View.GONE
            }
            override fun onFinish() {
                //Sorular yüklenmediyse hata mesajı göster ve görünümleri yükleme
                //Soruların tamamını alamama durumu olabilir.!!
                if (constructordanCek.size == 0){
                    soruYukleniyor.text = "Sorular yüklenemedi. Lütfen internet bağlantınızı kontrol ediniz."
                    tekrar_dene_button.visibility = View.VISIBLE
                }
                else{
                    tekrar_dene_button.visibility = View.GONE
                    soruYukleniyor.visibility = View.GONE
                    scrollView.visibility = View.VISIBLE
                    linearLayout.visibility = View.VISIBLE

                // Cevap indexlerinin tamamına 5(boş bırak) değerini atıyorum.
                while (secimArrayIndex < constructordanCek.size) {

                    secimArrayList.add(secimArrayIndex, 5)

                    secimArrayIndex++
                }

                    sorulariGoster()

                handler.removeCallbacks(runnable)
            }}
        }.start()
    }
    private fun verileriAl() {
        database = FirebaseDatabase.getInstance().reference.child(denemeAdi!! )  // Denemeler listesindeki değer ile aynı addaki firebase koleksiyonunu çağırıyorum

        database.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {

                    // bütün verileri alıyorum. DataDTO'ya ekliyorum ??
                dataSnapshot.children.forEach {
                    val post = it.getValue(DataDTO::class.java)
                    if (post != null) {
         // verileri değişkene atayıp constructor şeklinde arrayliste ekliyorum.
                        val soruAdiniAl =  post.soruAdi  //.toString()
                        val aSikkiniAl = post.a
                        val bSikkiniAl = post.b
                        val cSikkiniAl = post.c
                        val dSikkiniAl = post.d
                        val dogruCevabiAl = post.dc
                        val fotoyuAl = post.foto
                        // Json(html) ile Android Studio(utf-8) karakter kodlarını replace etmem gerekiyor. Eksiklerim var!!
                        val soruAdiniDuzenle =soruAdiniAl.replace("\\n", "\n").replace("<u>","<u>").replace("</u>","</u>")
                        val verileriisle = FirebasedanGelenVerileriAl(
                                soruAdiniDuzenle,
                                aSikkiniAl,
                                bSikkiniAl,
                                cSikkiniAl,
                                dSikkiniAl,
                                dogruCevabiAl,
                                fotoyuAl

                        )
                        constructordanCek.add(verileriisle)
                        dogruCevabiCek.add(verileriisle.dogruCevap)  /// Doğru cevapları ayrıca alıyorum.
                        // sınav bittikten sonra soruları gösterebilmek için alıyorum.
                        soruAdiCek.add(verileriisle.soruAdi)
                        aSikkiCek.add(verileriisle.aSikki)
                        bSikkiCek.add(verileriisle.bSikki)
                        cSikkiCek.add(verileriisle.cSikki)
                        dSikkiCek.add(verileriisle.dSikki)
                        fotografCek.add(verileriisle.fotoyuAl)

                    }

                    }

        }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(this@QuestionsActivity,"İnternete Bağlı Değilsiniz",Toast.LENGTH_LONG).show()
            }
        })
    }

    fun sorulariGoster() {
        soru_numarasi.text = ( "${index+1}/ ${constructordanCek.size}")
        // visibility gone--> layoutta kullandığı alanı da siliyor.

        if (constructordanCek[index].fotoyuAl.isNotEmpty()){  // fotoğraf var ise göster. yok ise görünmez yap
            Picasso.get().load(constructordanCek[index].fotoyuAl).into(soru_fotografi)
            soru_fotografi.visibility = View.VISIBLE
            println("fotograf var")
        }
        else{
                Picasso.get().load(R.drawable.previous_selected).into(soru_fotografi)  //rastgele fotoğraf
              soru_fotografi.visibility = View.GONE
            println("fotograf yok")
            }

        soru_adi.text = constructordanCek[index].soruAdi
        a_sikki.text = constructordanCek[index].aSikki
        b_sikki.text = constructordanCek[index].bSikki
        c_sikki.text = constructordanCek[index].cSikki
        d_sikki.text = constructordanCek[index].dSikki


        // ileri geri visibility
        if (index+1 < constructordanCek.size){  // son soruda ileri buton görünmez.
            ileri_button.visibility =View.VISIBLE
        }
        else{
            ileri_button.visibility = View.INVISIBLE
        }

        if(index < 1){                       // ilk soruda geri buton görünmez.
            geri_button.visibility = View.INVISIBLE
        }else
        {
           geri_button.visibility = View.VISIBLE
        }

    }



    fun secimRadioButton(view: View) {

        if (view is RadioButton) {
            // Is the button now checked?
            val checked = view.isChecked

            // RadioButtonlar a tıklandığında seçim değişkenine, o şıkkın değerini veriyorum.
            when (view.getId()) {

                R.id.a_sikki ->
                    if (checked){
                        secim = 1
                    }
                R.id.b_sikki ->
                    if (checked){
                        secim = 2
                    }
                R.id.c_sikki ->
                    if (checked){
                        secim = 3
                    }
                R.id.d_sikki ->
                    if (checked){
                        secim = 4
                    }

            }
        }

    }
    fun ileriButton (view: View) {

        secimArrayList[index] = secim   // seçim değişkenini soru cevap array listesinde değiştir.
        RadioGrup.clearCheck()     /// radiobutton işaretlemelerini kaldır.

        index++                         // indexi arttır.

        // yeni indexte önceden seçim yapılmış ise o değerin bulunduğu radiobuttonu işaretle. Ve seçimi de işaretlenen radiobuttona göre değiştir.
        if (secimArrayList[index] == 1){
            a_sikki.isChecked = true
            secim = 1
        }
        else if (secimArrayList[index] == 2){
            b_sikki.isChecked = true
            secim = 2
        }
        else if (secimArrayList[index] == 3){
            c_sikki.isChecked = true
            secim = 3
        }
        else if (secimArrayList[index] == 4){
            d_sikki.isChecked = true
            secim = 4
        }
        else {secim = 5
        }
        sorulariGoster()  // yeni indexteki soruları göster.

        //  if(!a_sikki.isChecked and !b_sikki.isChecked and !c_sikki.isChecked and !d_sikki.isChecked )

    }
    fun geriButton(view: View)  {  // ileriButton ile aynı işlemler

        secimArrayList[index] = secim
        RadioGrup.clearCheck()

        index--
        if (secimArrayList[index] == 1){
            a_sikki.isChecked = true
            secim = 1
        }
        else if (secimArrayList[index] == 2){
            b_sikki.isChecked = true
            secim = 2
        }
        else if (secimArrayList[index] == 3){
            c_sikki.isChecked = true
            secim = 3
        }
        else if (secimArrayList[index] == 4){
            d_sikki.isChecked = true
            secim = 4}
        else {secim = 5}
        sorulariGoster()
    }
    fun bitirButton (view: View) {  // bitir'e basılan indexteki cevabı kaydet. Kullanıcı cevapları ve doğru cevaplar arraylistini sonuclar aktiviteye gönder.
        secimArrayList[index] = secim

        val intentsonuclarActivity = Intent(this, SonuclarActivity::class.java)
        intentsonuclarActivity.putIntegerArrayListExtra("yollananKullaniciCevabi", secimArrayList)
        intentsonuclarActivity.putIntegerArrayListExtra ("dogruCevaplar", dogruCevabiCek)
        intentsonuclarActivity.putExtra("denemeAdi",denemeAdi)

        intentsonuclarActivity.putStringArrayListExtra("soruAdi",soruAdiCek)
        intentsonuclarActivity.putStringArrayListExtra("aSikki",aSikkiCek)
        intentsonuclarActivity.putStringArrayListExtra("bSikki",bSikkiCek)
        intentsonuclarActivity.putStringArrayListExtra("cSikki",cSikkiCek)
        intentsonuclarActivity.putStringArrayListExtra("dSikki",dSikkiCek)
        intentsonuclarActivity.putStringArrayListExtra("fotograf",fotografCek)

        startActivity(intentsonuclarActivity)
        finish()
    }

    fun tekrarDeneButton(view: View){  /// sorular yüklenmediyse visible.
        recreate()

    }



}



