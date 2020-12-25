package com.freesign

import android.graphics.Rect
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ItemDecoration
import cn.pedant.SweetAlert.SweetAlertDialog
import com.freesign.adapter.DesignerAdapter
import com.freesign.firebase.firestore.collection.UserCollection
import com.freesign.model.User
import com.freesign.utils.Authenticated
import com.freesign.utils.BaseFragment
import kotlinx.android.synthetic.main.search_designer.*


class SearchDesigner : BaseFragment() {

    var dialog: SweetAlertDialog? = null

    var designers = ArrayList<User>()
    var page = 1
    var changePage = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.search_designer, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if(Authenticated.getSpecialization() == "Web Designer")
            button_web.visibility = View.VISIBLE
        else if(Authenticated.getSpecialization() == "Interior Designer")
            button_interior.visibility = View.VISIBLE
        else
            button_architecture.visibility = View.VISIBLE

        UserCollection.getDesignerBySpecialization(Authenticated.getSpecialization()!!, this::checkData)

        if(dialog!=null)
            dialog!!.dismiss()

        button_next.setOnClickListener {
            page++
            changePage = true
            UserCollection.getDesignerBySpecialization(Authenticated.getSpecialization()!!, this::checkData)
        }

        button_back.setOnClickListener {
            page--
            changePage = true
            UserCollection.getDesignerBySpecialization(Authenticated.getSpecialization()!!, this::checkData)
        }
    }

    fun checkData(newDesigners: ArrayList<User>) {
        if(!designers.equals(newDesigners) || changePage) {
            changePage = false
            designers = newDesigners
            setRecycleData()
        }
    }

    private fun setRecycleData(){
        var items = ArrayList<User>()

        var index = 0

        while(true) {
            val start = (page - 1) * 4
            val end = start + 4

            if ((index in start until end) && (index < designers.size))
                items.add(designers[index])

            if (index > end)
                break

            index++
        }

        val designerAdapter = context?.let { DesignerAdapter(items, it) }

        rv_designer.apply {
            layoutManager = GridLayoutManager(context, 2)
            adapter = designerAdapter
        }

        rv_designer.addItemDecoration(object : ItemDecoration() {
            override fun getItemOffsets(
                outRect: Rect,
                view: View,
                parent: RecyclerView,
                state: RecyclerView.State
            ) {
                val position = parent.getChildAdapterPosition(view!!) // item position
                val spanCount = 2
                val spacing = 10 //spacing between views in grid
                if (position >= 0) {
                    val column = position % spanCount // item column
                    outRect.left =
                        spacing - column * spacing / spanCount // spacing - column * ((1f / spanCount) * spacing)
                    outRect.right =
                        (column + 1) * spacing / spanCount // (column + 1) * ((1f / spanCount) * spacing)
                    if (position < spanCount) { // top edge
                        outRect.top = spacing
                    }
                    outRect.bottom = spacing // item bottom
                } else {
                    outRect.left = 0
                    outRect.right = 0
                    outRect.top = 0
                    outRect.bottom = 0
                }
            }
        })
    }
}