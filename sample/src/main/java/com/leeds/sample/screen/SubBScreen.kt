package com.leeds.sample.screen

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.leeds.lib.ui.viewstack.screen.BaseScreen
import com.leeds.sample.MainActivity
import com.leeds.sample.R

class SubBScreen : BaseScreen() {
    override fun createView(
        inflater: LayoutInflater?,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        if (inflater == null || container == null) {
            return null
        }

        return inflater.inflate(R.layout.screen_default, container, false)
    }

    override fun onCreateView(container: ViewGroup, view: View) {
        super.onCreateView(container, view)
        view.findViewById<TextView>(R.id.text)?.text = "SubB"
        view.findViewById<View>(R.id.back)?.setOnClickListener {
            pop()
        }
        view.findViewById<View>(R.id.next)?.setOnClickListener {
            next()
        }

    }

    private fun pop() {
        val activity = getActivity()
        if (activity is MainActivity) {
            activity.pop()
        }
    }

    private fun next() {
        val activity = getActivity()
        if (activity is MainActivity) {
            activity.popStart()
        }
    }
}