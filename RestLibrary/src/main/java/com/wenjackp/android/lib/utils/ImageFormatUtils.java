package com.wenjackp.android.lib.utils;

import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.PixelFormat;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * 图片格式转换工具类
 *
 * @author Single
 * @version 1.2
 */
public final class ImageFormatUtils {

    private final static int QUALITY_FULL = 100;

    public static byte[] bitmapToByteArray(Bitmap mBitmap, CompressFormat format, int quality) {
        if (mBitmap != null) {
            ByteArrayOutputStream mByteArrayOutputStream = new ByteArrayOutputStream();
            mBitmap.compress(format, QUALITY_FULL, mByteArrayOutputStream);
            return mByteArrayOutputStream.toByteArray();
        }
        return null;
    }

    public static byte[] bitmapToByteArray(Bitmap mBitmap, CompressFormat format) {
        if (mBitmap != null) {
            ByteArrayOutputStream mByteArrayOutputStream = new ByteArrayOutputStream();
            mBitmap.compress(format, QUALITY_FULL, mByteArrayOutputStream);
            return mByteArrayOutputStream.toByteArray();
        }
        return null;
    }

    public static byte[] bitmapToByteArray(Bitmap mBitmap) {
        if (mBitmap != null) {
            ByteArrayOutputStream mByteArrayOutputStream = new ByteArrayOutputStream();
            mBitmap.compress(CompressFormat.JPEG, QUALITY_FULL, mByteArrayOutputStream);
            return mByteArrayOutputStream.toByteArray();
        }
        return null;
    }

    public static Bitmap byteToBitmap(byte[] mByte) {
        if (mByte != null && mByte.length > 0) {
            return BitmapFactory.decodeByteArray(mByte, 0, mByte.length);
        }
        return null;
    }

    public static Drawable bitmapToDrawable(Bitmap mBitmap) {
        BitmapDrawable mDrawable = new BitmapDrawable(mBitmap);
        return mDrawable;
    }

    public static Bitmap drawableToBitmap(Drawable mDrawable) {
        Bitmap mBitmap = null;

        if (mDrawable instanceof BitmapDrawable) {
            mBitmap = ((BitmapDrawable) mDrawable).getBitmap();
        } else {
            mBitmap = Bitmap.createBitmap(mDrawable.getIntrinsicWidth(), mDrawable.getIntrinsicHeight(), mDrawable.getOpacity() != PixelFormat.OPAQUE ? Bitmap.Config.ARGB_8888 : Bitmap.Config.RGB_565);
            Canvas mCanvas = new Canvas(mBitmap);
            mDrawable.setBounds(0, 0, mDrawable.getIntrinsicWidth(), mDrawable.getIntrinsicHeight());
            mDrawable.draw(mCanvas);
        }

        return mBitmap;
    }

    public static InputStream bitmapToStream(Bitmap mBitmap) {
        byte[] mBuffer = bitmapToByteArray(mBitmap);
        return new ByteArrayInputStream(mBuffer);
    }

    public static InputStream drawableToStream(Drawable mDrawable) {
        Bitmap mBitmap = drawableToBitmap(mDrawable);
        return bitmapToStream(mBitmap);
    }

    public static Bitmap inputStreamToBitmap(InputStream mInputStream) {
        return BitmapFactory.decodeStream(mInputStream);
    }

    public static byte[] inputStreamToByteArray(InputStream mInputStream) throws IOException {
        ByteArrayOutputStream mByteArrayOutputStream = new ByteArrayOutputStream();

        byte[] tempBuffer = new byte[1024];
        int tempLength = 0;

        while ((tempLength = mInputStream.read(tempBuffer)) != -1) {
            mByteArrayOutputStream.write(tempBuffer, 0, tempLength);
        }

        return mByteArrayOutputStream.toByteArray();
    }

    public static InputStream byteArrayToInputStream(byte[] mByte) {
        if (mByte != null && mByte.length > 0) {
            return new ByteArrayInputStream(mByte);
        }

        return null;
    }
}