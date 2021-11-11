package kr.ac.deu.se.example

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.Toast

class AdapterViewDemoActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_adapter_view_demo)

        val listItems = arrayOf("x", "y", "z")

        val adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, listItems)
        val listView = findViewById<ListView>(R.id.list_view)
        listView.adapter = adapter

        listView.onItemClickListener =
            AdapterView.OnItemClickListener { parent, view, position, id ->
                Toast.makeText(applicationContext, listItems[position], Toast.LENGTH_SHORT).show()
            }
    }
}
