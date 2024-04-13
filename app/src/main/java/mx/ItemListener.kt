package mx

import android.view.View

interface ItemListener {
    fun onClick(v: View?, positio: Int)
    fun onEdit(v: View?, position: Int)
    fun onDel(v: View?, position: Int)
}