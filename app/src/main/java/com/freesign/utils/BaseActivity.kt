package com.freesign.utils

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import cn.pedant.SweetAlert.SweetAlertDialog


abstract class BaseActivity: AppCompatActivity() {
    private var pDialog: SweetAlertDialog? = null

    open fun showProgress() : SweetAlertDialog{
        pDialog = SweetAlertDialog(this, SweetAlertDialog.PROGRESS_TYPE)
        pDialog!!.progressHelper.barColor = Color.parseColor("#A5DC86")
        pDialog!!.titleText = "Memuat"
        pDialog!!.setCancelable(false)
        pDialog!!.show()
        return pDialog!!
    }

    open fun dismissProgress() {
        if (pDialog != null) pDialog!!.dismiss()
    }

    open fun showSuccess(message: String?) : SweetAlertDialog {
        val swal = SweetAlertDialog(this, SweetAlertDialog.SUCCESS_TYPE)
        swal.titleText = "Sukses"
        swal.contentText = message
        swal.show()
        return swal
    }

    open fun showError(text: String) : SweetAlertDialog {
        val swal = SweetAlertDialog(this, SweetAlertDialog.ERROR_TYPE)
        swal.titleText = "Gagal"
        swal.contentText = text
        swal.show()
        return swal
    }
}