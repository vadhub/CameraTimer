package com.abg.cameratimer

import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.widget.NumberPicker
import androidx.fragment.app.DialogFragment
import java.util.concurrent.TimeUnit

class ChooseTimeDialog : DialogFragment() {
    lateinit var accept: (time: Long) -> Unit

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {

        val view = layoutInflater.inflate(R.layout.choose_time_dialog, null)

        val arrayOfPeriod = initialArrayPeriod(view.context)
        var period = arrayOfPeriod[0].second

        val picker = view.findViewById<NumberPicker>(R.id.pickerPeriodTime)
        picker.minValue = 0
        picker.maxValue = 10
        picker.displayedValues = arrayOfPeriod.map { it.first }.toTypedArray()

        picker.setOnValueChangedListener { picker, oldVal, newVal ->
            Log.d("picker", " ${arrayOfPeriod[newVal]}")
            period = arrayOfPeriod[newVal].second
        }

        val builder = AlertDialog.Builder(view.context)

        builder.setView(view)
            .setPositiveButton(view.context.resources.getString(R.string.yes)) { _, _ ->
                accept.invoke(period)
            }.setNegativeButton(view.context.resources.getString(R.string.cancel)) { _, _ ->
                dialog?.cancel()
            }

        return builder.create()

    }

    private fun initialArrayPeriod(context: Context): List<Pair<String, Long>> {

        val sec = context.resources.getString(R.string.sec)
        val min = context.resources.getString(R.string.min)

        val arrayTime = mutableListOf<Pair<String, Long>>()

        for (i in 1..60) {
            arrayTime.add(Pair("$i $sec", TimeUnit.SECONDS.toMillis(i.toLong())))
        }

        for (i in 1..60) {
            arrayTime.add(Pair("$i $min", TimeUnit.MINUTES.toMillis(i.toLong())))
        }

        return arrayTime

    }

}