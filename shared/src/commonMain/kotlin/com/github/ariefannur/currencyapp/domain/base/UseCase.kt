package com.github.ariefannur.currencyapp.domain.base

import com.rickclephas.kmp.nativecoroutines.NativeCoroutineScope
import com.rickclephas.kmp.nativecoroutines.NativeCoroutines
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.Flow

abstract class UseCase<out Type, in Params> where Type : Any {

    @NativeCoroutines
    abstract suspend fun run(params: Params): Flow<Type>

    @NativeCoroutineScope
    val coroutineScope: CoroutineScope = MainScope()

    operator fun invoke(
        params: Params,
        callback: (DataState<Type>) -> Unit = {}
    ) {
        coroutineScope.launch(Dispatchers.Default) {
            try {
                callback.invoke(DataState.Loading)
                val deferred = async {  run(params) }
                deferred.await().collect {
                    callback.invoke(DataState.Success(it))
                }
            } catch (e: Exception) {
                println("Error ${e.message}")
                callback.invoke(DataState.Failure(404, e.message ?: ""))
            }
        }
    }

    class None


    fun runNative(params: Params, callback: (DataStateNative<Type>) -> Unit = {}) {
        coroutineScope.launch(Dispatchers.Default) {
            try {
                callback.invoke(DataStateNative(NATIVE_LOADING))
                val deferred = async {  run(params) }
                deferred.await().collect {
                    callback.invoke(DataStateNative(NATIVE_SUCCESS, it))
                }
            } catch (e: Exception) {
                println("Error ${e.message}")
                callback.invoke(DataStateNative( state = NATIVE_ERROR, error = ErrorNative(404, e.message ?: "")))
            }
        }
    }
}