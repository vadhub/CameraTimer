package com.abg.cameratimer

import android.os.CountDownTimer

class Timer(private val time: Long) {
    private var handler: TimerHandler? = null
    private var timer: CountDownTimer? = null

    var timeStartFrom = time
        private set

    private val interval = 1000L

    var isStart = false
        private set

    fun setTimerHandler(handler: TimerHandler) {
        this.handler = handler
    }

    fun startTimer() {
        if (timeStartFrom > 1_000) {
            isStart = true
            timer = object : CountDownTimer(timeStartFrom, interval) {
                override fun onTick(millisUntilFinished: Long) {
                    timeStartFrom = millisUntilFinished
                    handler?.showTime(millisUntilFinished)
                }

                override fun onFinish() {
                    isStart = false
                    handler?.finishTime()
                    handler = null
                }
            }.start()
        }
    }

    fun cancelTimer() {
        handler = null
        isStart = false
        timer?.cancel()
    }

    fun timerDestroy() {
        handler = null
        cancelTimer()
        timer = null
    }
}

interface TimerHandler {
    fun showTime(time: Long)
    fun finishTime()
}