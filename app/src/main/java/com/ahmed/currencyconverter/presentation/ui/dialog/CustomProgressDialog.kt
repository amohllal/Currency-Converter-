package com.ahmed.currencyconverter.presentation.ui.dialog

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import com.ahmed.currencyconverter.R

class CustomProgressDialog(context: Context) {

    var dialog: Dialog = Dialog(context)


    fun showCustomDialog(isCancelable: Boolean) {
        dialog.setContentView(R.layout.custom_progress_dialog)
        dialog.window!!.setBackgroundDrawable(
            ColorDrawable(Color.TRANSPARENT)
        )
        dialog.setCancelable(isCancelable)
        dialog.show()
    }

    fun hideCustomDialog() {
        if (dialog.isShowing) {
            dialog.hide()
        }
    }

    fun dismissCustomDialog() {
        if (dialog.isShowing) {
            dialog.dismiss()
        }
        dialog.cancel()
        dialog.dismiss()
    }

}