package com.example.bdandsubd.presenter.spinerAdapter

import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import com.example.bdandsubd.entities.getter.GuestGet

class GuestSpinerAdapter(context: Context, resource: Int, private val data: List<GuestGet>):ArrayAdapter<GuestGet>(context,resource,data) {
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val textView = super.getView(position, convertView, parent) as TextView
        textView.text = data[position].name
        return textView

    }

    override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup): View {
        val textView = super.getDropDownView(position, convertView, parent) as TextView
        textView.text = data[position].name
        return textView
    }


}