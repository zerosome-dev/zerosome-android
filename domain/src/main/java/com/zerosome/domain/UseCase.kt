package com.zerosome.domain

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers

open class UseCase {
    val coroutineScope = CoroutineScope(Dispatchers.IO)

    open fun innerLogic() {}
}