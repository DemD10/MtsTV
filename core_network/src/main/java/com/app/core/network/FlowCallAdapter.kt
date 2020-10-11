package com.app.core.network

import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import retrofit2.Call
import retrofit2.CallAdapter
import retrofit2.Callback
import retrofit2.Response
import java.lang.reflect.Type

/*
 1) I know that experimental api is bad, but why not for this case
 2) Old solution with suspendCancellableCoroutine is broke in 1.4 compiler
 */

class FlowCallAdapter<T>(
    private val responseType: Type
) : CallAdapter<T, Flow<Response<T>>> {
    override fun adapt(call: Call<T>): Flow<Response<T>> {
        return callbackFlow {
            call.enqueue(object : Callback<T> {
                override fun onFailure(call: Call<T>, t: Throwable) {
                    this@callbackFlow.close(t)
                }

                override fun onResponse(call: Call<T>, response: Response<T>) {
                    try {
                        offer(response.body()!!)
                    } catch (e: Exception) {
                        this@callbackFlow.close(e)
                    }
                }
            })
            this@callbackFlow.awaitClose { call.cancel() }
        }
    }

    override fun responseType() = responseType
}

class BodyCallAdapter<T>(private val responseType: Type) : CallAdapter<T, Flow<T>> {
    override fun adapt(call: Call<T>): Flow<T> {
        return callbackFlow {
            call.enqueue(object : Callback<T> {
                override fun onFailure(call: Call<T>, t: Throwable) {
                    this@callbackFlow.close(t)
                }

                override fun onResponse(call: Call<T>, response: Response<T>) {
                    try {
                        offer(response.body()!!)
                    } catch (e: Exception) {
                        this@callbackFlow.close(e)
                    }
                }
            })
            this@callbackFlow.awaitClose { call.cancel() }
        }
    }

    override fun responseType() = responseType
}