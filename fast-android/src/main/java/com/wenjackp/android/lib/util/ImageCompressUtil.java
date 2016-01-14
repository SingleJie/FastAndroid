package com.wenjackp.android.lib.util;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Rect;
import android.os.Build;

import java.io.ByteArrayOutputStream;

/**
 * 图片压缩工具类
 *
 * @author Single
 * @version 1.0
 */
public class ImageCompressUtil {

    private static final int QUALITY = 100;

    @SuppressLint("NewApi")
    public static long getBitmapSize(Bitmap mBitmap) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR1) {
            return mBitmap.getByteCount();
        }

        return mBitmap.getRowBytes() * mBitmap.getHeight();
    }

    public static Bitmap scaledCompress(Bitmap source, int targetWidth, int targetHeight) {
        Matrix scaler = new Matrix();
        int deltaX = source.getWidth() - targetWidth;
        int deltaY = source.getHeight() - targetHeight;

        if (deltaX < 0 || deltaY < 0) {
            /*
			 * In this case the bitmap is smaller, at least in one dimension,
			 * than the target. Transform it by placing as much of the image as
			 * possible into the target and leaving the top/bottom or left/right
			 * (or both) black.
			 */
            Bitmap b2 = Bitmap.createBitmap(targetWidth, targetHeight, Bitmap.Config.ARGB_8888);
            Canvas c = new Canvas(b2);

            int deltaXHalf = Math.max(0, deltaX / 2);
            int deltaYHalf = Math.max(0, deltaY / 2);
            Rect src = new Rect(deltaXHalf, deltaYHalf, deltaXHalf
                    + Math.min(targetWidth, source.getWidth()), deltaYHalf
                    + Math.min(targetHeight, source.getHeight()));
            int dstX = (targetWidth - src.width()) / 2;
            int dstY = (targetHeight - src.height()) / 2;
            Rect dst = new Rect(dstX, dstY, targetWidth - dstX, targetHeight
                    - dstY);
            c.drawBitmap(source, src, dst, null);
            return b2;
        }

        float bitmapWidthF = source.getWidth();
        float bitmapHeightF = source.getHeight();

        float bitmapAspect = bitmapWidthF / bitmapHeightF;
        float viewAspect = (float) targetWidth / targetHeight;

        if (bitmapAspect > viewAspect) {
            float scale = targetHeight / bitmapHeightF;
            if (scale < .9F || scale > 1F) {
                scaler.setScale(scale, scale);
            } else {
                scaler = null;
            }
        } else {
            float scale = targetWidth / bitmapWidthF;
            if (scale < .9F || scale > 1F) {
                scaler.setScale(scale, scale);
            } else {
                scaler = null;
            }
        }

        Bitmap b1;
        if (scaler != null) {
            // this is used for minithumb and crop, so we want to mFilter here.
            b1 = Bitmap.createBitmap(source, 0, 0, source.getWidth(), source.getHeight(), scaler, true);
        } else {
            b1 = source;
        }

        int dx1 = Math.max(0, b1.getWidth() - targetWidth);
        int dy1 = Math.max(0, b1.getHeight() - targetHeight);

        Bitmap b2 = Bitmap.createBitmap(b1, dx1 / 2, dy1 / 2, targetWidth, targetHeight);

        if (b1 != source) {
            b1.recycle();
        }

        return b2;
    }

    public static Bitmap compressToTargetSize(Bitmap mBitmap, int targetSize) {
        int quality = 100;
        ByteArrayOutputStream mOutputStream = new ByteArrayOutputStream();
        mBitmap.compress(CompressFormat.JPEG, quality, mOutputStream);

        while (mOutputStream.size() > targetSize && quality > 0) {
            mOutputStream.reset();
            quality -= 2;
            mBitmap.compress(CompressFormat.JPEG, quality, mOutputStream);
        }
        return BitmapFactory.decodeByteArray(mOutputStream.toByteArray(), 0, mOutputStream.size());
    }
}
