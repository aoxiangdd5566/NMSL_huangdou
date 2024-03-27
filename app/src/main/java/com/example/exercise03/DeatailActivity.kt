package com.example.exercise03

import android.os.Bundle
import android.provider.ContactsContract.Contacts.Data
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity


class DeatailActivity : AppCompatActivity() {

    private lateinit var receivedJson: json // 在類別中宣告 receivedJson 變數

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_deatail)

        try {
            val intent = getIntent()
            receivedJson = intent.getSerializableExtra("data") as json
            //Toast.makeText(this, receivedJson.toString(), Toast.LENGTH_SHORT).show()
        }catch (e:Exception){
            Toast.makeText(this, "ERROR", Toast.LENGTH_SHORT).show()
        }

        val textView17=findViewById<TextView>(R.id.textView17)
        val textView19=findViewById<TextView>(R.id.textView19)
        val textView8=findViewById<TextView>(R.id.textView8)
        val textView11=findViewById<TextView>(R.id.textView11)
        val textView12=findViewById<TextView>(R.id.textView12)

        //後續要解析
        if (receivedJson != null) {
            val products = receivedJson.Products
            textView17.text = products.map { it.name }.joinToString("\n")
        } else {
            Toast.makeText(this, "ERROR", Toast.LENGTH_SHORT).show()
        }

        if (receivedJson != null) {
            val products = receivedJson.Products
            textView19.text = products.map { it.price }.joinToString("\n")
        } else {
            Toast.makeText(this, "ERROR", Toast.LENGTH_SHORT).show()
        }

        val Datalist=(receivedJson.Date).split("-")
        val intDatalist=Datalist.map { it.toInt() }
        val intDatalistyear=intDatalist[0]-1911
        val strDatalistyear="${intDatalistyear}"

        var strDatalistmonth="error"
        var x=1
        var y=2
        while (y<13){
            try {
                if (intDatalist[1]==x || intDatalist[1]==y){
                    strDatalistmonth= "${x}-${y}"
                }
            }catch (e:Exception){
                Toast.makeText(this, "ERROR", Toast.LENGTH_SHORT).show()
            }

            x+=2
            y+=2
        }

        textView8.text=receivedJson.Store
        textView11.text="${strDatalistyear}年${strDatalistmonth}月"
        textView12.text=receivedJson.ID
    }
}
