package com.alexpletnyov.coroutines_example

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.core.view.isVisible
import com.alexpletnyov.coroutines_example.databinding.ActivityMainBinding
import kotlin.concurrent.thread

class MainActivity : AppCompatActivity() {

	private val binding by lazy {
		ActivityMainBinding.inflate(layoutInflater)
	}

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContentView(binding.root)
		binding.buttonLoad.setOnClickListener {
			loadData()
		}
	}

	private fun loadData() {
		binding.progress.isVisible = true
		binding.buttonLoad.isEnabled = false
		loadCity {
			binding.tvLocation.text = it
			loadTemperature(it) {
				binding.tvTemperature.text = it.toString()
				binding.progress.isVisible = false
				binding.buttonLoad.isEnabled = true
			}
		}
	}

	private fun loadTemperature(city: String, callback: (Int) -> Unit) {
		thread {
			Toast.makeText(
				this,
				"Load temperature for city: $city",
				Toast.LENGTH_SHORT
			).show()
			Thread.sleep(5000)
			callback.invoke(17)
		}
	}

	private fun loadCity(callback: (String) -> Unit) {
		thread {
			Thread.sleep(5000)
			callback.invoke("Blagoveshchensk")
		}
	}
}