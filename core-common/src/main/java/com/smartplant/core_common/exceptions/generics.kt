package com.smartplant.core_common.exceptions


class NetworkUnavailableException(message: String, cause: Throwable? = null) : BaseAppException(message, cause)

class UnauthorizedException(message: String, cause: Throwable? = null) : BaseAppException(message, cause)
