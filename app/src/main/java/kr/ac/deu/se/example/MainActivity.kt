package kr.ac.deu.se.example

import android.content.ComponentName
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val recyclerView: RecyclerView = findViewById(R.id.recycler_view)
        recyclerView.adapter = DemoListAdapter(arrayOf("NaverMap", "AdapterViewDemo", "CustomListView"))
    }

    private class DemoListAdapter(private val dataSet: Array<String>) :
        RecyclerView.Adapter<DemoListAdapter.ViewHolder>() {

        class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
            val textView: TextView = itemView.findViewById(R.id.textView)

            init {
                textView.setOnClickListener {
                    val context = itemView.context
                    context.startActivity(Intent().apply {
                        component = ComponentName(context.packageName, "${context.packageName}.${textView.text}Activity")
                    })
                }
            }
        }

        override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
            val view = LayoutInflater.from(viewGroup.context)
                .inflate(R.layout.item, viewGroup, false)

            return ViewHolder(view)
        }

        override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
            viewHolder.textView.text = dataSet[position]
        }

        override fun getItemCount() = dataSet.size
    }
}
