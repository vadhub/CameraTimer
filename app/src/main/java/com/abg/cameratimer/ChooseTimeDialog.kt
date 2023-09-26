package com.abg.cameratimer

import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.DialogFragment
import com.abg.cameratimer.databinding.ChooseTimeDialogBinding
import java.util.concurrent.TimeUnit

class ChooseTimeDialog : DialogFragment() {

    lateinit var accept: (time: Long) -> Unit

    private lateinit var viewBinding: ChooseTimeDialogBinding

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {

        viewBinding = ChooseTimeDialogBinding.inflate(layoutInflater)

        val context = viewBinding.root.context

        val arrayOfPeriodInSec = initialArrayPeriodInSec(context)
        val arrayOfPeriodInMin = initialArrayPeriodInMin(context)

        var periodSec = arrayOfPeriodInSec[0].second
        var periodMin = 0L

        val picker = viewBinding.pickerPeriodTime
        val pickerMin = viewBinding.pickerPeriodTimeMin

        picker.minValue = 0
        picker.maxValue = 58
        picker.displayedValues = arrayOfPeriodInSec.map { it.first }.toTypedArray()

        pickerMin.minValue = 0
        pickerMin.maxValue = 58
        pickerMin.displayedValues = arrayOfPeriodInMin.map { it.first }.toTypedArray()

        picker.setOnValueChangedListener { picker, oldVal, newVal ->
            Log.d("picker", " ${arrayOfPeriodInSec[newVal]}")
            periodSec = arrayOfPeriodInSec[newVal].second
        }

        pickerMin.setOnValueChangedListener{ picker, oldVal, newVal ->
            periodMin = arrayOfPeriodInMin[newVal].second
        }

        val builder = AlertDialog.Builder(context)

        builder.setView(viewBinding.root)
            .setPositiveButton(context.resources.getString(R.string.yes)) { _, _ ->
                accept.invoke(periodSec + periodMin)
            }.setNegativeButton(context.resources.getString(R.string.cancel)) { _, _ ->
                dialog?.cancel()
            }

        return builder.create()

    }

    private fun initialArrayPeriodInSec(context: Context): List<Pair<String, Long>> {

        val sec = context.resources.getString(R.string.sec)

        val arrayTime = mutableListOf<Pair<String, Long>>()

        for (i in 0..59) {
            arrayTime.add(Pair("$i $sec", TimeUnit.SECONDS.toMillis(i.toLong())))
        }

        return arrayTime

    }

    private fun initialArrayPeriodInMin(context: Context): List<Pair<String, Long>> {

        val min = context.resources.getString(R.string.min)

        val arrayTime = mutableListOf<Pair<String, Long>>()

        for (i in 0..59) {
            arrayTime.add(Pair("$i $min", TimeUnit.MINUTES.toMillis(i.toLong())))
        }

        return arrayTime

    }

}