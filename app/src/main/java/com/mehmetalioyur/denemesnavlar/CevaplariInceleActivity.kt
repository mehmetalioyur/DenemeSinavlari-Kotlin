package com.mehmetalioyur.denemesnavlar


import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_cevaplari_incele.*


class CevaplariInceleActivity : AppCompatActivity() {

    private lateinit var soruAdi : ArrayList<String>
    private lateinit var aSikki : ArrayList<String>
    private lateinit var bSikki : ArrayList<String>
    private lateinit var cSikki : ArrayList<String>
    private lateinit var dSikki : ArrayList<String>
    private lateinit var fotograf : ArrayList<String>
    private lateinit var kullaniciCevabi : ArrayList<Int>
    private lateinit var dogruCevap : ArrayList<Int>

    private var index = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cevaplari_incele)
            // soru ve cevapları question activityden getirdim.
        soruAdi = intent.getStringArrayListExtra("soruAdi")!!
        aSikki = intent.getStringArrayListExtra("aSikki")!!
        bSikki = intent.getStringArrayListExtra("bSikki")!!
        cSikki = intent.getStringArrayListExtra("cSikki")!!
        dSikki = intent.getStringArrayListExtra("dSikki")!!
        fotograf = intent.getStringArrayListExtra("fotograf")!!
        kullaniciCevabi = intent.getIntegerArrayListExtra("kullaniciCevabi")!!
        dogruCevap = intent.getIntegerArrayListExtra("dogruCevap")!!

    cevaplariGoster()
    }


    @SuppressLint("SetTextI18n")
    fun cevaplariGoster (){
            /// arraylist indexlerini sırayla gösteriyorum.
        cevaplari_incele_soru_numarasi.text = "${index+1} / ${soruAdi.size}"
        cevaplari_incele_soru_adi.text = soruAdi[index]
        cevaplari_incele_a_sikki.text = aSikki[index]
        cevaplari_incele_b_sikki.text = bSikki[index]
        cevaplari_incele_c_sikki.text = cSikki[index]
        cevaplari_incele_d_sikki.text = dSikki[index]

        if (fotograf[index].isNotEmpty()){  // fotoğraf var ise göster. yok ise görünmez yap
            Picasso.get().load(fotograf[index]).into(cevaplari_incele_soru_fotografi)
            cevaplari_incele_soru_fotografi.visibility = View.VISIBLE

        }
        else{
            Picasso.get().load(R.drawable.previous_selected).into(cevaplari_incele_soru_fotografi)  //rastgele fotoğraf
            cevaplari_incele_soru_fotografi.visibility = View.GONE
        }

        // ileri geri visibility
        if (index+1 < soruAdi.size   ){  // son soruda ileri buton görünmez.
            cevaplari_incele_ileri_button.visibility =View.VISIBLE
        }else{
            cevaplari_incele_ileri_button.visibility = View.INVISIBLE
        }

        if(index < 1){                       // ilk soruda geri buton görünmez.
            cevaplari_incele_geri_button.visibility = View.INVISIBLE
        }else{
            cevaplari_incele_geri_button.visibility = View.VISIBLE
        }// her soru için önce siyah rengi atıyorum.
        cevaplari_incele_a_sikki.setBackgroundResource(R.drawable.radio_unchecked)
        cevaplari_incele_b_sikki.setBackgroundResource(R.drawable.radio_unchecked)
        cevaplari_incele_c_sikki.setBackgroundResource(R.drawable.radio_unchecked)
        cevaplari_incele_d_sikki.setBackgroundResource(R.drawable.radio_unchecked)
        textViewRenk()
    }

    fun cevaplariInceleIleriButton(view:View){


        index++
        cevaplariGoster()
    }
    fun cevaplariInceleGeriButton (view: View){


        index--
        cevaplariGoster()
    }


    private fun dogruCevap(){
        if (dogruCevap[index] ==1){
            cevaplari_incele_a_sikki.setBackgroundResource(R.drawable.radio_checked)
        }
        if (dogruCevap[index] ==2){
            cevaplari_incele_b_sikki.setBackgroundResource(R.drawable.radio_checked)
        }
        if (dogruCevap[index] ==3){
            cevaplari_incele_c_sikki.setBackgroundResource(R.drawable.radio_checked)
        }
        if (dogruCevap[index] ==4){
            cevaplari_incele_d_sikki.setBackgroundResource(R.drawable.radio_checked)
        }


    }
    private fun yanlisCevap() {
        if (kullaniciCevabi[index] == 1) {
            cevaplari_incele_a_sikki.setBackgroundResource(R.drawable.radio_wrong_answer)
        }
        if (kullaniciCevabi[index] == 2) {
            cevaplari_incele_b_sikki.setBackgroundResource(R.drawable.radio_wrong_answer)
        }
        if (kullaniciCevabi[index] == 3) {
            cevaplari_incele_c_sikki.setBackgroundResource(R.drawable.radio_wrong_answer)
        }
        if (kullaniciCevabi[index] == 4) {
            cevaplari_incele_d_sikki.setBackgroundResource(R.drawable.radio_wrong_answer)
        }
    }
        private fun bosCevap(){
            if (dogruCevap[index] ==1){
                cevaplari_incele_a_sikki.setBackgroundResource(R.drawable.radio_empty_answer)
            }
            if (dogruCevap[index] ==2){
                cevaplari_incele_b_sikki.setBackgroundResource(R.drawable.radio_empty_answer)
            }
            if (dogruCevap[index] ==3){
                cevaplari_incele_c_sikki.setBackgroundResource(R.drawable.radio_empty_answer)
            }
            if (dogruCevap[index] ==4){
                cevaplari_incele_d_sikki.setBackgroundResource(R.drawable.radio_empty_answer)
            }
    }
    private fun textViewRenk(){// işaretleme yapıldığında doğru cevap yeşil. Yapılmadığında sarı. Yanlış cevap var ise kırmızı.
        if (kullaniciCevabi[index] == dogruCevap[index]){
            dogruCevap()

        }
        else if(kullaniciCevabi[index] == 5){
            bosCevap()
        }

        else{
            dogruCevap()
            yanlisCevap()
        }
    }
    fun cevaplariInceleBitirButton (view: View){ // sonuclar aktivitye geri döndüm.
        this.finish()

    }
} 