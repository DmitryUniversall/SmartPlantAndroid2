package com.smartplant.core_common.async

inline fun <T> Result<T>.finally(block: () -> Unit): Result<T> = this.also { block() }
