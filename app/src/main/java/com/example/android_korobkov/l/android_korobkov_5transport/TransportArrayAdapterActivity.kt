package com.example.android_korobkov.l.android_korobkov_5transport

import android.content.DialogInterface
import android.os.Bundle
import android.widget.ArrayAdapter
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.example.android_korobkov.l.android_korobkov_5transport.databinding.ActivityListTransportBinding
import com.example.android_korobkov.l.android_korobkov_5transport.databinding.DialogAddTransportBinding
import com.example.android_korobkov.l.android_korobkov_5transport.transportBaseAdapter.TransportModel

class TransportArrayAdapterActivity : AppCompatActivity() {
    private lateinit var adapter: ArrayAdapter<TransportModel>
    private lateinit var binding: ActivityListTransportBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityListTransportBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupListWithArrayAdapter()
        binding.addButton.setOnClickListener { onAddPressed() }
    }

    private fun setupListWithArrayAdapter() {
        val data = mutableListOf(
            TransportModel("Toyota", "Автомобиль", "500 кг", 2),
            TransportModel("Honda", "Мотоцикл", "300 кг", 0),
            TransportModel("Ford", "Автомобиль", "800 кг", 3),
            TransportModel("Harley Davidson", "Мотоцикл", "400 кг", 0),
            TransportModel("Volvo", "Автомобиль", "1000 кг", 4)
        )


        adapter = ArrayAdapter(
            this,
            R.layout.item_transport_custom,
            R.id.editTextName,
            data
        )

        binding.listView.adapter = adapter

        binding.listView.setOnItemClickListener { parent, view, position, id ->
            adapter.getItem(position)?.let {
                deleteTransport(it)
            }
        }
    }

    private fun onAddPressed() {
        val dialogBinding = DialogAddTransportBinding.inflate(layoutInflater)
        val dialog = AlertDialog.Builder(this)
            .setTitle("Create charcter")
            .setView(dialogBinding.root)
            .setPositiveButton("Add") { d, which ->
                val name = dialogBinding.editTextName.text.toString()
                if (name.isNotBlank()) {
                    createTransport(name)
                }
            }
            .create()
        dialog.show()
    }

    private fun createTransport(name: String) {
        val transport = TransportModel(
            name,
            "Автомобиль",
            "1000 кг",
            4
        )

        adapter.add(transport)
    }

    private fun deleteTransport(transport: TransportModel) {
        val listener = DialogInterface.OnClickListener { dialog, which ->
            if (which == DialogInterface.BUTTON_POSITIVE) {
                adapter.remove(transport)
            }
        }
        val dialog = AlertDialog.Builder(this)
            .setTitle("Delete transport")
            .setMessage("Are you sure you want to delete the transport ${transport}")
            .setPositiveButton("Delete", listener)
            .setNegativeButton("Cancel", listener)
            .create()
        dialog.show()
    }
}