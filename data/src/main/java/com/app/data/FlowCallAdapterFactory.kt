package com.app.data

import kotlinx.coroutines.flow.Flow
import retrofit2.CallAdapter
import retrofit2.Response
import retrofit2.Retrofit
import java.lang.reflect.ParameterizedType
import java.lang.reflect.Type

class FlowCallAdapterFactory private constructor() : CallAdapter.Factory() {
    override fun get(
        returnType: Type,
        annotations: Array<Annotation>,
        retrofit: Retrofit
    ): CallAdapter<*, *>? {
        if (getRawType(returnType) != Flow::class.java) {
            return null
        }
        check(returnType is ParameterizedType)
        val responseType = getParameterUpperBound(0, returnType)
        val rawFlowType = getRawType(responseType)
        return if (rawFlowType == Response::class.java) {
            check(responseType is ParameterizedType)
            com.app.data.FlowCallAdapter<Any>(
                getParameterUpperBound(
                    0,
                    responseType
                )
            )
        } else {
            com.app.data.BodyCallAdapter<Any>(responseType)
        }
    }

    companion object {
        @JvmStatic
        fun create() = FlowCallAdapterFactory()
    }
}