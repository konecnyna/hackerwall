package com.hackerwall.images

import android.graphics.Bitmap
import android.graphics.Matrix

import com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool

import com.bumptech.glide.load.resource.bitmap.BitmapTransformation
import com.hackerwall.models.DeviceInfo
import java.nio.charset.Charset
import java.security.MessageDigest
import kotlin.math.min


class ESBTransformation(val deviceInfo: DeviceInfo) : BitmapTransformation() {
    public override fun transform(
        pool: BitmapPool,
        toTransform: Bitmap,
        outWidth: Int,
        outHeight: Int
    ): Bitmap {
        if (toTransform.width == outWidth && toTransform.height == outHeight) {
            return toTransform
        }

        val startX = toTransform.width / 2
        return Bitmap.createBitmap(
            toTransform,
            startX,
            0,
            toTransform.width - startX,
            Math.min(deviceInfo.deviceHeight, toTransform.height),
            Matrix(),
            true
        )
    }


    override fun updateDiskCacheKey(messageDigest: MessageDigest) {
        messageDigest.update(ID_BYTES)
    }

    override fun hashCode(): Int {
        return ID.hashCode()
    }

    companion object {
        private const val ID = "com.hackerwall.images.ESBTransformation"
        private val ID_BYTES: ByteArray = ID.toByteArray(Charset.forName("UTF-8"))
    }
}