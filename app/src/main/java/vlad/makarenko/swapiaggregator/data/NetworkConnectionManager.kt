package vlad.makarenko.swapiaggregator.data

import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import android.net.NetworkRequest
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.callbackFlow
import javax.inject.Inject

class NetworkConnectionManager @Inject constructor(@ApplicationContext private val context: Context) {
    @ExperimentalCoroutinesApi
    val isConnected = callbackFlow {
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

        val callback = NetworkCallback { connectionState -> trySend(connectionState) }

        val networkRequest = NetworkRequest.Builder()
            .addCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
            .build()

        connectivityManager.registerNetworkCallback(networkRequest, callback)

        val currentState = getCurrentConnectivityState(connectivityManager)
        trySend(currentState)

        awaitClose {
            connectivityManager.unregisterNetworkCallback(callback)
        }
    }

    private fun getCurrentConnectivityState(
        connectivityManager: ConnectivityManager
    ) = connectivityManager.allNetworks.any { network ->
        connectivityManager.getNetworkCapabilities(network)
            ?.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
            ?: false
    }
}

private class NetworkCallback(private val callback: (Boolean) -> Unit) :
    ConnectivityManager.NetworkCallback() {
    override fun onAvailable(network: Network) {
        callback(true)
    }

    override fun onLost(network: Network) {
        callback(false)
    }
}
