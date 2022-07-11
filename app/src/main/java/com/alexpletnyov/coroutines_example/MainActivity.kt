package com.alexpletnyov.coroutines_example

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import com.alexpletnyov.coroutines_example.databinding.ActivityMainBinding
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

	private val binding by lazy {
		ActivityMainBinding.inflate(layoutInflater)
	}

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContentView(binding.root)
		binding.buttonLoad.setOnClickListener {
			binding.progress.isVisible = true
			binding.buttonLoad.isEnabled = false
			val deferredCity: Deferred<String> = lifecycleScope.async {
				loadCity()
			}
			val deferredTemp: Deferred<Int> = lifecycleScope.async {
				loadTemperature()
			}
			lifecycleScope.launch {
				val city = deferredCity.await()
				binding.tvLocation.text = city
				val temp = deferredTemp.await()
				binding.tvTemperature.text = temp.toString()
				Toast.makeText(
					this@MainActivity,
					"City: $city, Temp: $temp",
					Toast.LENGTH_SHORT
				).show()
				binding.progress.isVisible = false
				binding.buttonLoad.isEnabled = true
			}
		}
	}

	private suspend fun loadData() {
		Log.d("MainActivity", "Load started: $this")
		binding.progress.isVisible = true
		binding.buttonLoad.isEnabled = false
		val city = loadCity()

		binding.tvLocation.text = city
		val temp = loadTemperature()

		binding.tvTemperature.text = temp.toString()
		binding.progress.isVisible = false
		binding.buttonLoad.isEnabled = true
		Log.d("MainActivity", "Load finished   : $this")
	}

	private suspend fun loadTemperature(): Int {
		delay(5000)
		return 17
	}

	private suspend fun loadCity(): String {
		delay(2000)
		return "Blagoveshchensk"
	}
}