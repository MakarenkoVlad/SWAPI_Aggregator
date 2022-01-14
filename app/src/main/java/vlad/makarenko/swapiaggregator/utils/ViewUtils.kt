package vlad.makarenko.swapiaggregator.utils

import android.view.View
import androidx.annotation.StringRes
import com.google.android.material.snackbar.Snackbar

fun View.showSnackBar(text: String, length: Int = Snackbar.LENGTH_LONG) = Snackbar.make(this, text, length).show()

fun View.showSnackBar(@StringRes text: Int, length: Int = Snackbar.LENGTH_LONG) = Snackbar.make(this, text, length).show()