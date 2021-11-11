package kr.ac.deu.se.example

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.ListView
import android.widget.Toast

class AdapterViewDemoActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_adapter_view_demo)

        val listItems = mutableListOf("x", "y", "z")

        val adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, listItems)
        val listView = findViewById<ListView>(R.id.listView)
        listView.adapter = adapter

        val editText = findViewById<EditText>(R.id.editText)
        val button = findViewById<Button>(R.id.button)

        button.setOnClickListener {
            listItems.add(editText.text.toString())
            adapter.notifyDataSetChanged()
        }

        listView.setOnItemLongClickListener { parent, view, position, id ->
            listItems.removeAt(position)
            adapter.notifyDataSetChanged()
            return@setOnItemLongClickListener false
        }

        listView.setOnItemClickListener { parent, view, position, id ->
            Toast.makeText(applicationContext, listItems[position], Toast.LENGTH_SHORT).show()
        }
    }
}
