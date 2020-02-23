package com.whammy.user

import java.lang.Exception

class UserNotFoundException(override val message: String): Exception()