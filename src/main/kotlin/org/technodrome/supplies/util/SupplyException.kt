package org.technodrome.supplies.util

import java.lang.Exception

class SupplyException(override val message: String?, override val cause: Throwable) : Exception(message, cause) {
}