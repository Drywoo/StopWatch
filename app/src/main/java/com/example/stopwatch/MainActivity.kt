package com.example.stopwatch

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import com.example.stopwatch.databinding.ActivityMainBinding
import java.util.Timer
import kotlin.concurrent.timer

class MainActivity : AppCompatActivity() {

    private val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }
    private var time = 0
    private var timerTask: Timer? = null
    private var isRunning = false
    private var lap = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val view = binding.root
        setContentView(view)

        binding.flap.setOnClickListener {
            isRunning = !isRunning

            if (isRunning) {
                start()
            } else {
                pause()
            }
        }
        binding.lapBtn.setOnClickListener {
            recordLapTime()
        }

    }

    private fun start() {
        binding.flap.setImageResource(R.drawable.baseline_pause_24)
        timerTask = timer(period = 10) {
            time++

            val sec = time / 100
            val milli = time % 100

            runOnUiThread {
                binding.secTextView.text = "$sec"
                binding.milliTextView.text = "$milli"
            }

        }
    }

    private fun pause() {
        binding.flap.setImageResource(R.drawable.baseline_play_arrow_24)
        timerTask?.cancel()
    }

    private fun recordLapTime() {
        val lapTime = this.time
        val textView = TextView(this)
        textView.text = "$lap Lap : ${lapTime / 100}.${lapTime%100}"

        binding.lapLayout.addView(textView,0)
        lap++
    }
    private fun reset() {
        timerTask?.cancel()
        time = 0
        isRunning = false
        binding.flap.setImageResource(R.drawable.baseline_play_arrow_24)
        binding.secTextView.text = "0"
        binding.milliTextView.text = "00"

        binding.lapLayout.removeAllViews()
        lap = 1
    }
}