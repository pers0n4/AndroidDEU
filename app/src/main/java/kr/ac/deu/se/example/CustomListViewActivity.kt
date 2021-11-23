package kr.ac.deu.se.example

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ListView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class CustomListViewActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_custom_list_view)

        val listItems = mutableListOf<CustomListViewItem>()

        listItems.add(
            CustomListViewItem(
                "TypeScript",
                "TypeScript is a strongly typed programming language that builds on JavaScript, giving you better tooling at any scale."
            )
        )
        listItems.add(
            CustomListViewItem(
                "Python",
                "Python is a programming language that lets you work more quickly and integrate your systems more effectively."
            )
        )
        listItems.add(
            CustomListViewItem(
                "Rust",
                "A language empowering everyone to build reliable and efficient software."
            )
        )
        listItems.add(
            CustomListViewItem(
                "Go",
                "Go is an open source programming language that makes it easy to build simple, reliable, and efficient software."
            )
        )
        listItems.add(
            CustomListViewItem(
                "Kotlin",
                "A modern programming language that makes developers happier."
            )
        )

        val adapter = CustomListViewAdapter(listItems)

        val listView = findViewById<ListView>(R.id.custom_list_view)
        listView.adapter = adapter
    }

    inner class CustomListViewAdapter(private val listItems: MutableList<CustomListViewItem>) :
        BaseAdapter() {
        override fun getCount(): Int = listItems.size

        override fun getItem(position: Int): CustomListViewItem = listItems[position]

        override fun getItemId(position: Int): Long = position.toLong()

        override fun getView(position: Int, view: View?, parent: ViewGroup?): View? {
            var convertView = view
            if (convertView == null) {
                convertView =
                    LayoutInflater.from(parent?.context)
                        .inflate(R.layout.custom_list_item, parent, false)
            }

            val textTitle = convertView?.findViewById<TextView>(R.id.text_custom_list_title)
            val textSubtitle = convertView?.findViewById<TextView>(R.id.text_custom_list_subtitle)

            val item = listItems[position]
            textTitle?.text = item.title
            textSubtitle?.text = item.subtitle

            return convertView
        }
    }

    data class CustomListViewItem(val title: String, val subtitle: String)
}
