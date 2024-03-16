package com.example.android_korobkov.l.android_korobkov_5transport

import android.os.Bundle
import android.widget.AdapterView
import android.widget.SimpleAdapter
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.example.android_korobkov.l.android_korobkov_5transport.databinding.ActivitySimpleListViewBinding

class TransportAdapterActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySimpleListViewBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySimpleListViewBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupListViewSimple()
    }

    private fun setupListViewSimple() {
        val data = listOf(
            mapOf(
                KEY_TITLE to "Заголовок 1-го элемента",
                KEY_DESCRIPTION to "Содержимое для первого элемента"
            ),
            mapOf(
                KEY_TITLE to "Заголовок 2-го элемента",
                KEY_DESCRIPTION to "Содержимое для второго элемента"
            ),
            mapOf(
                KEY_TITLE to "Заголовок 3-го элемента",
                KEY_DESCRIPTION to "Содержимое для третьего элемента"
            )
        )

        val adapter = SimpleAdapter(
            this,
            data,
            R.layout.item_transport_custom,
            arrayOf(KEY_TITLE),
            intArrayOf(R.id.titleTransport)
        )

        binding.listView.adapter = adapter

        binding.listView.onItemClickListener =
            AdapterView.OnItemClickListener { parent, view, position, id ->
                val selectedItemTitle = data[position][KEY_TITLE]
                val selectedItemDescriptor = data[position][KEY_DESCRIPTION]

                val dialog = AlertDialog.Builder(this)
                    .setTitle(selectedItemTitle)
                    .setMessage(getString(R.string.item_selected_message, selectedItemDescriptor))
                    .setPositiveButton("ok") { dialog, whis -> }
                    .create()
                dialog.show()
            }
    }

    companion object {
        @JvmStatic
        val KEY_TITLE = "title"

        @JvmStatic
        val KEY_DESCRIPTION = "description"
    }
}