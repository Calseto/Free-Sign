package com.freesign.utils

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import cn.pedant.SweetAlert.SweetAlertDialog


abstract class BaseFragment: Fragment() {
    private var pDialog: SweetAlertDialog? = null

    open fun showProgress() : SweetAlertDialog{
        pDialog = SweetAlertDialog(context, SweetAlertDialog.PROGRESS_TYPE)
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
        val swal = SweetAlertDialog(context, SweetAlertDialog.SUCCESS_TYPE)
        swal.titleText = "Sukses"
        swal.contentText = message
        swal.show()
        return swal
    }

    open fun showError(text: String) : SweetAlertDialog {
        val swal = SweetAlertDialog(context, SweetAlertDialog.WARNING_TYPE)
        swal.titleText = "Gagal"
        swal.contentText = text
        swal.show()
        return swal
    }
}