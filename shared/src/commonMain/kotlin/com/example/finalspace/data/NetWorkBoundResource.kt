package com.example.finalspace.data

import com.example.finalspace.data.remote.SerialResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map

/**
 * This method is responsible for manage and fetch the queries into the localDB.
 */
inline fun <ResultType, RequestType> networkBoundResource(
    crossinline loadFromDb: () -> Flow<ResultType>,
    crossinline netWorkRequest: suspend () -> SerialResponse<RequestType>,
    crossinline saveCall: suspend (SerialResponse<RequestType>) -> Unit
): Flow<NResponse<ResultType>> = flow {
    emit(NResponse.Loading(loadFromDb().firstOrNull()))
    val netWorkSerialResponse: SerialResponse<RequestType> = netWorkRequest()
    emitAll(
        if (netWorkSerialResponse is SerialResponse.Success) {
            saveCall(netWorkSerialResponse)
            loadFromDb().map { NResponse.Success(it) }
        } else {
            val error = netWorkSerialResponse as SerialResponse.Error
            loadFromDb().map { NResponse.Error(error.message, it) }
        }
    )
}
