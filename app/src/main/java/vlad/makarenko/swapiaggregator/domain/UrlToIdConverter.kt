package vlad.makarenko.swapiaggregator.domain

import android.net.Uri
import timber.log.Timber

object UrlToIdConverter {

    class UrlToIdConvertException : Exception("Url does not have id")

    fun getIdFromUrl(url: String) =
        Uri.parse(url).lastPathSegment?.toInt() ?: throw UrlToIdConvertException()

    fun getIdFromPageOrNull(url: String?) = url?.let {
        Uri.parse(it).getQueryParameter("page")?.toInt() ?: throw UrlToIdConvertException()
    }
}