package com.clouddo.commons.common.util

import java.util.*

/**
 * UUID工具类
 * @author ming
 */
object UUIDGenerator {
    @JvmStatic fun getUUID(): String {
        var s = UUID.randomUUID().toString()
        s = s.replace("-", "")
        return s
    }

    @JvmStatic fun getUUID(number: Int): Array<String?>? {
        if (number < 1) {
            return null
        }
        val ss = arrayOfNulls<String>(number)
        for (i in 0 until number) {
            ss[i] = getUUID()
        }
        return ss
    }
}
