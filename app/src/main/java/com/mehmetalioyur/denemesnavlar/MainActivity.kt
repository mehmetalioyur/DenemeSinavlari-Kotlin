package com.mehmetalioyur.denemesnavlar

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {
private lateinit var isimText:String
private lateinit var username : String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //sharedPreferences'ta kullanıcı adı varsa DersSecimiActivitye git. username i al. Bu aktiviteyi bitir.
        val sharedPreferences = this.getSharedPreferences("com.mehmetalioyur.denemesnavlar", Context.MODE_PRIVATE)
        username = sharedPreferences.getString("username", "").toString()
        println(username)

        if (username != "") {
            val intent = Intent(this, DersSecimiActivity::class.java)

         //   intent.putExtra("username",username)

            startActivity(intent)
            finish()

        }

    }
    fun basla(view: View){

        //isimTexte girilen veriyi sharedPreferences a koy. Ve geri çağır.
        isimText = isim_text.text.toString()
        val sharedPreferences = this.getSharedPreferences("com.mehmetalioyur.denemesnavlar", Context.MODE_PRIVATE)
        sharedPreferences.edit().putString("username",isimText).apply()
        username = sharedPreferences.getString("username", "").toString()

        //Boş ise toast mesajı yazdır.
        if (username == ""){
            Toast.makeText(this,"Lütfen İsminizi Giriniz.",Toast.LENGTH_LONG).show()
        // dolu ise DersSecimiActivitye git. username i al. Bu aktiviteyi bitir.
        }else{
           // val intent = Intent(this, DersSecimiActivity::class.java)
            intent.putExtra("username",username)

            startActivity(intent)

            finish()
        }
    }
}