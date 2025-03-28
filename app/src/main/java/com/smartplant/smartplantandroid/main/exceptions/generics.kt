package com.smartplant.smartplantandroid.main.exceptions

import com.smartplant.smartplantandroid.core.exceptions.BaseAppException


class NetworkUnavailableException(message: String, cause: Throwable? = null) : BaseAppException(message, cause)

class UnauthorizedException(message: String, cause: Throwable? = null) : BaseAppException(message, cause)
