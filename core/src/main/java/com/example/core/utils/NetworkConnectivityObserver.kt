package com.example.core.utils

//class NetworkConnectivityObserver(
//    private val context: Context
//) : ConnectivityObserver {
//
//    private val connectivityManager =
//        context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
//
//    override fun observe(): Flow<Boolean> {
//        return callbackFlow {
//            val callback = object : ConnectivityManager.NetworkCallback() {
//                override fun onAvailable(network: Network) {
//                    super.onAvailable(network)
//                    launch { send(true) }
//                }
//
//                override fun onLosing(network: Network, maxMsToLive: Int) {
//                    super.onLosing(network, maxMsToLive)
//                    launch { send(true) }
//                }
//
//                override fun onLost(network: Network) {
//                    super.onLost(network)
//                    launch { send(false) }
//                }
//
//                override fun onUnavailable() {
//                    super.onUnavailable()
//                    launch { send(false) }
//                }
//            }
//
//            connectivityManager.registerDefaultNetworkCallback(callback)
//            awaitClose {
//                connectivityManager.unregisterNetworkCallback(callback)
//            }
//        }.distinctUntilChanged()
//    }
//}