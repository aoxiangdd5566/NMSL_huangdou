package com.example.exercise03

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.Gson
import java.io.Serializable


class MainActivity : AppCompatActivity() {
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        var jsonString : String? = null
        val gson = Gson()
        var receipt : Array<json>? = null

        try {
            jsonString = assets.open("receipt.json").bufferedReader().use { it.readText() }
            receipt = gson.fromJson(jsonString,Array<json>::class.java)

        } catch(e: Exception) {
            Toast.makeText(this, "crash", Toast.LENGTH_SHORT).show()
        }

        val recyclerView=findViewById<RecyclerView>(R.id.RecyclerView)

        // 初始化 LayoutManager
        val layoutManager = LinearLayoutManager(this@MainActivity, LinearLayoutManager.VERTICAL, false)

        // 設定 LayoutManager
        recyclerView.layoutManager = layoutManager

        val adapter = TheAdapter(receipt!!,this)
        recyclerView.adapter = adapter

        val textView2=findViewById<TextView>(R.id.textView2)
        val textView7=findViewById<TextView>(R.id.textView7)

        var i=0//交易比數
        var y=0//總價
        for (x in receipt){
            y=x.Price+y
            i+=1
        }
        textView2.text="      共有${i}筆交易"
        textView7.text="$y"

        //剩總價與帳單筆數顯示

    }
}



data class json(
    val Date: String,
    val ID: String,
    val Store: String,
    val Price: Int,
    val Products: List<JSONdetails>
):Serializable
data class JSONdetails(
    val name:String,
    val price:Int
):Serializable

class TheHolder(v: View):RecyclerView.ViewHolder(v){
    var itemtextyear=v.findViewById<TextView>(R.id.textView3)
    var itemtextID=v.findViewById<TextView>(R.id.textView4)
    var itemtextName=v.findViewById<TextView>(R.id.textView5)
    var itemtextPrice=v.findViewById<TextView>(R.id.textView13)
}
class TheAdapter(val receipt: Array<json>,val context: Context) : RecyclerView.Adapter<TheHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TheHolder {
        return TheHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_cafe,parent,false)

        )
    }

    override fun getItemCount(): Int {
        return receipt.size
    }
    override fun onBindViewHolder(holder: TheHolder, position: Int) {
        val currentItem = receipt[position]
        holder.itemtextyear.text = currentItem.Date
        holder.itemtextID.text = currentItem.ID
        holder.itemtextName.text = currentItem.Store
        holder.itemtextPrice.text = currentItem.Price.toString()

        // 設定點擊監聽器，並傳遞資料
        holder.itemView.setOnClickListener {
            val intent = Intent(context, DeatailActivity::class.java)
            intent.putExtra("data", currentItem)
            //Toast.makeText(context, currentItem.toString(), Toast.LENGTH_SHORT).show()
            context.startActivity(intent)
        }
    }
}