package vlad.makarenko.swapiaggregator.utils

import android.content.Context
import android.view.View
import android.widget.Adapter
import android.widget.ListView
import android.widget.FrameLayout




fun ListView.setWrapContent() {
    layoutParams.height = getWidestView(context, adapter)
}

private fun getWidestView(context: Context, adapter: Adapter): Int {
    var maxWidth = 0
    var view: View? = null
    val fakeParent = FrameLayout(context)
    var i = 0
    val count: Int = adapter.count
    while (i < count) {
        view = adapter.getView(i, view, fakeParent)
        view.measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED)
        val width: Int = view.measuredWidth
        if (width > maxWidth) {
            maxWidth = width
        }
        i++
    }
    return maxWidth
}