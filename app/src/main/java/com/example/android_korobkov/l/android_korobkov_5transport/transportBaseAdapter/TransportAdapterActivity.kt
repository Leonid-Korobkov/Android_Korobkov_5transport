package com.example.android_korobkov.l.android_korobkov_5transport.transportBaseAdapter

import android.content.DialogInterface
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.example.android_korobkov.l.android_korobkov_5transport.databinding.ActivityListTransportBinding
import com.example.android_korobkov.l.android_korobkov_5transport.databinding.DialogAddTransportBinding

class TransportAdapterActivity : AppCompatActivity() {
    private lateinit var adapter: TransportAdapter
    private lateinit var binding: ActivityListTransportBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Связываем макет с активити
        binding = ActivityListTransportBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Настройка списка и прослушивателей кнопок
        setupList()
        binding.addButton.setOnClickListener { onAddPressed() }
        binding.infoButton.setOnClickListener { onInfoPressed() }
    }

    // Инициализация данных для списка транспорта
    private val dataTransports = mutableListOf(
        TransportModel("Toyota", "Автомобиль", "500 кг", 2),
        TransportModel("Honda", "Мотоцикл", "300 кг", 0),
        TransportModel("Ford", "Автомобиль", "800 кг", 3),
        TransportModel("Harley Davidson", "Мотоцикл", "400 кг", 0),
        TransportModel("Volvo", "Автомобиль", "1000 кг", 4)
    )

    private fun setupList() {
        // Создание адаптера для списка
        adapter = TransportAdapter(this, dataTransports) {
            deleteTransport(it)
        }

        // Установка адаптера для списка
        binding.listView.adapter = adapter

        // Установка прослушивателя выбора элемента списка
        binding.listView.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                // Отображение информации о выбранном элементе списка
                val transport = dataTransports[position]
                val message =
                    "Название: ${transport.name}\nТип: ${transport.type}\nГрузоподъемность: ${transport.capacity}\nКоличество осей: ${transport.axleCount}"
                binding.tranportViewInfo.text = message
                infoButtonClicked = false
                binding.tranportViewInfo.setBackgroundColor(getColor(android.R.color.transparent))
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                // Ничего не делаем в случае, если ничего не выбрано
            }
        }
    }

    // Обработка нажатия на кнопку "Добавить"
    private fun onAddPressed() {
        val dialogBinding = DialogAddTransportBinding.inflate(layoutInflater)
        val dialog = AlertDialog.Builder(this)
            .setTitle("Создать транспорт")
            .setView(dialogBinding.root)
            .setPositiveButton("Добавить") { _, _ ->
                // Получение данных о новом транспорте из диалогового окна
                val name = dialogBinding.editTextName.text.toString().takeIf { it.isNotBlank() }
                    ?: "<Без названия>"
                val type = if (dialogBinding.radioButtonCar.isChecked) "Автомобиль" else "Мотоцикл"
                val capacity =
                    dialogBinding.editTextCapacity.text.toString().takeIf { it.isNotBlank() }
                        ?: "1000 кг"
                val axleCount = dialogBinding.editTextAxleCount.text.toString().toIntOrNull() ?: 0

                // Создание нового транспорта
                createTransport(name, type, capacity, axleCount)
            }
            .create()
        dialog.show()
    }

    // Создание нового транспорта
    private fun createTransport(name: String, type: String, capacity: String, axleCount: Int) {
        // Создание транспорта с передачей данных в конструктор
        val transport = TransportModel(
            name = name,
            type = type,
            capacity = capacity,
            axleCount = axleCount,
            isShowRemoveIcon = true
        )

        // Добавление транспорта в список и обновление адаптера
        dataTransports.add(transport)
        adapter.notifyDataSetChanged()
    }

    // Удаление выбранного транспорта
    private fun deleteTransport(transport: TransportModel) {
        val listener = DialogInterface.OnClickListener { dialog, which ->
            if (which == DialogInterface.BUTTON_POSITIVE) {
                val sizeDataTransports = dataTransports.size

                if (sizeDataTransports <= 1) {
                    Toast.makeText(this, "Нельзя удалить последний транспорт", Toast.LENGTH_SHORT)
                        .show()
                    return@OnClickListener
                }
                // Удаление транспорта из списка и обновление адаптера
                dataTransports.remove(transport)
                adapter.notifyDataSetChanged()
            }
        }
        val dialog = AlertDialog.Builder(this)
            .setTitle("Удалить транспорт")
            .setMessage("Вы уверены, что хотите удалить транспорт ${transport}?")
            .setPositiveButton("Удалить", listener)
            .setNegativeButton("Отмена", listener)
            .create()
        dialog.show()
    }

    // Объявляем переменную, которая будет отслеживать, была ли уже нажата кнопка
    private var infoButtonClicked = false
    private fun onInfoPressed() {

        // Генерируем случайный цвет для фона
        val randomColor = generateRandomColor()
        // Устанавливаем случайный цвет фона для текстового поля
        binding.tranportViewInfo.setBackgroundColor(randomColor)

        // Проверяем, была ли уже нажата кнопка
        if (!infoButtonClicked) {
            // Определяем позицию текущего выбранного элемента в списке
            val selectedItemPosition = binding.listView.selectedItemPosition + 1
            val totalItemCount = dataTransports.size

            // Формируем текст с информацией о позиции элемента
            val positionInfoText = "\n\nПозиция $selectedItemPosition из $totalItemCount"

            // Отображаем информацию о позиции в текстовом поле, добавляя к существующему тексту
            binding.tranportViewInfo.append(positionInfoText)

            // Устанавливаем флаг, что кнопка была нажата
            infoButtonClicked = true
        }
    }

    private fun generateRandomColor(): Int {
        // Генерируем случайные значения для красного, зеленого и синего цветов
        val red = (0..255).random()
        val green = (0..255).random()
        val blue = (0..255).random()
        // Собираем цвет из сгенерированных значений
        return android.graphics.Color.rgb(red, green, blue)
    }

}