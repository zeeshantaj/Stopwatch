package com.example.first_kotlin_app_using_mvvm.StopWatch

import android.app.Dialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.SystemClock
import android.widget.Button
import android.widget.Chronometer
import android.widget.NumberPicker
import com.example.first_kotlin_app_using_mvvm.R
import com.example.first_kotlin_app_using_mvvm.databinding.ActivityStopWatchBinding

class StopWatchActivity : AppCompatActivity() {

    private var isRunning= false
    private var minute:String ?= "00:00:00"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityStopWatchBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.clockTime.text=minute

        binding.setCountDownTxt.setOnClickListener{
            val dialog = Dialog(this)
            dialog.setContentView(R.layout.set_countdown_layout)
            val numberPicker = dialog.findViewById<NumberPicker>(R.id.numberPicker)
            numberPicker.minValue=0
            numberPicker.maxValue=30
            dialog.findViewById<Button>(R.id.countSetBtn).setOnClickListener{

                minute = numberPicker.value.toString()
                binding.clockTime.text = dialog.findViewById<NumberPicker>(R.id.numberPicker).value.toString()+ " minute"
                dialog.dismiss()
            }
            dialog.show()
        }
        binding.runBtn.setOnClickListener{
            if (!isRunning){
                isRunning = false
                if (!minute.equals("00:00:00")){
                    var total = minute!!.toInt()*60*1000L
                    var countDown = 1000L
                    binding.chronometer.base = SystemClock.elapsedRealtime() + total
                    binding.chronometer.format = "%$ %$"
                    binding.chronometer.onChronometerTickListener = Chronometer.OnChronometerTickListener {
                        val elapsedTime = SystemClock.elapsedRealtime() - binding.chronometer.base
                        if (elapsedTime >= total){
                            binding.chronometer.stop()
                            isRunning = false
                            binding.runBtn.text = "Run"
                        }

                    }
                }

            else{
                isRunning = true
                binding.chronometer.base = SystemClock.elapsedRealtime()
                binding.runBtn.text = "Stop"
                    binding.chronometer.start()
            }}
            else{
                binding.chronometer.stop()
                isRunning = false
               // binding.chronometer.base = SystemClock.elapsedRealtime()
                binding.runBtn.text = "Run"
            }


        }
    }
}
