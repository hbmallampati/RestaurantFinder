package edu.utexas.cs.zhitingz.fcsql

import android.content.Context
import android.content.Intent
import android.database.Cursor
import android.net.Uri
import android.text.Html
import android.text.method.LinkMovementMethod
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CursorAdapter
import android.widget.TextView

class RestaurantItemAdapter(context: Context, c: Cursor, autoRequery: Boolean) :
//https://developer.android.com/reference/kotlin/android/widget/CursorAdapter
        CursorAdapter(context, c, autoRequery) {

    override fun newView(context: Context, cursor: Cursor, viewGroup: ViewGroup): View {
        return LayoutInflater.from(context).inflate(R.layout.restaurant_item, viewGroup, false)
    }

    override fun bindView(view: View, context: Context, cursor: Cursor) {
        val restaurantName = view.findViewById<View>(R.id.restaurant_name) as TextView
        val restaurantPhone = view.findViewById<View>(R.id.restaurant_phone) as TextView
        val restaurantAddress = view.findViewById<View>(R.id.restaurant_address) as TextView
        val restaurantUrl = view.findViewById<View>(R.id.restaurant_url) as TextView
        val restaurantPrice = view.findViewById<View>(R.id.price) as TextView

        // XXX WRITE ME: Fill the TextView using data in cursor.
        // price will be in the form of "$", e.g. 0 will be "", 1 will be "$" and 2 will be "$$".
        val name = cursor.getString(cursor.getColumnIndex("name"))
        val address = cursor.getString(cursor.getColumnIndex("full_address"))

        val phone : String
        if(cursor.getString(cursor.getColumnIndex("phone")) != null)
            phone = cursor.getString(cursor.getColumnIndex("phone"))
        else
            phone = " "

        val url = cursor.getString(cursor.getColumnIndex("url"))

        var price : String
        when (cursor.getInt(cursor.getColumnIndex("price"))) {
            1-> price = "$"
            2-> price = "$$"
            3-> price = "$$$"
            4 -> price = "$$$$"
            else -> price = " "
        }

        restaurantName.text = name
        restaurantPhone.text = phone
        restaurantPrice.text = price
        restaurantPhone.setOnClickListener { v ->
            val intent = Intent(Intent.ACTION_DIAL, Uri.fromParts("tel", phone, null))
            v.context.startActivity(intent)
        }
        restaurantAddress.text = address
        restaurantUrl.text = Html.fromHtml("<a href=\"$url\">website</a>", Html.FROM_HTML_MODE_LEGACY)
        restaurantUrl.movementMethod = LinkMovementMethod.getInstance()

    }
}
