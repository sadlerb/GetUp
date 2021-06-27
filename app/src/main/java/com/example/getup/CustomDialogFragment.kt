package com.example.getup

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.fragment.app.DialogFragment


class CustomDialogFragment : DialogFragment() {

    interface OnInputListener{
        fun sendInput(input:Array<String>)
    }
    var listener :OnInputListener? = null

    private lateinit var desc: EditText
    private lateinit var repeat: EditText
    private lateinit var confirm: Button
    private lateinit var cancel: Button

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view =  inflater.inflate(R.layout.custom_dialog_fragment, container, false)
        desc = view.findViewById(R.id.editTextTextDesc)
        repeat = view.findViewById(R.id.editTextNumber)
        confirm = view.findViewById(R.id.dialogSubmit)
        cancel = view.findViewById(R.id.cancelButton)
        cancel.setOnClickListener{
            dialog?.dismiss()
        }
        confirm.setOnClickListener{
            val stringInput = desc.text.toString()
            val repeatInput = repeat.text.toString()
            listener?.sendInput(arrayOf<String>(stringInput,repeatInput))
            dialog?.dismiss()
        }
        return view
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        try {
            listener = activity as OnInputListener
        }catch (e: ClassCastException ){

        }
    }

    companion object {
        private const val TAG = "MyCustomDialog"
    }
}

