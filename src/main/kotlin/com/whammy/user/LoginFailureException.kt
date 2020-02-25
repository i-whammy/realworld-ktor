package com.whammy.user

import java.lang.Exception

class LoginFailureException(override val message: String): Exception()