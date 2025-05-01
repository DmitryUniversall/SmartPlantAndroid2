package com.smartplant.core_android.network.exceptions

import com.smartplant.core_common.exceptions.BaseAppException

open class ApiCallException(message: String, cause: Throwable? = null) : BaseAppException(message, cause)
