package com.smartplant.smartplantandroid.main.network.exceptions

import com.smartplant.smartplantandroid.core.exceptions.BaseAppException

open class ApiCallException(message: String, cause: Throwable? = null) : BaseAppException(message, cause)
