package com.jaydenxiao.common.commonutils;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.widget.ImageView;


import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DecodeFormat;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.jaydenxiao.common.R;

import java.io.File;

/**
 * Description : 图片加载工具类 使用glide框架封装
 */
public class ImageLoaderUtils {

    public static void display(Context context, ImageView imageView, String url, int placeholder, int error) {
        if (imageView == null) {
            throw new IllegalArgumentException("argument error");
        }
//        Glide.with(context).load(url).placeholder(placeholder)
//                .error(error).crossFade().into(imageView);
        Glide.with(context)
                .load(url)
                .apply(new RequestOptions()
                        .error(error)
                        .placeholder(placeholder))
                .into(imageView);
    }

    public static void display(Context context, ImageView imageView, String url) {
        if (imageView == null) {
            throw new IllegalArgumentException("argument error");
        }
//        Glide.with(context).load(url)
//                .diskCacheStrategy(DiskCacheStrategy.ALL)
//                .centerCrop()
//                .placeholder(R.drawable.ic_image_loading)
//                .error(R.drawable.ic_empty_picture)
//                .crossFade().into(imageView);

        Glide.with(context)
                .load(url)
                .apply(new RequestOptions()
                        .error(R.drawable.no_content_tip)
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .placeholder(R.drawable.ic_image_loading))
                .into(imageView);
    }

    public static void display(Context context, ImageView imageView, File url) {
        if (imageView == null) {
            throw new IllegalArgumentException("argument error");
        }
//        Glide.with(context).load(url)
//                .diskCacheStrategy(DiskCacheStrategy.ALL)
//                .centerCrop()
//                .placeholder(R.drawable.ic_image_loading)
//                .error(R.drawable.ic_empty_picture)
//                .crossFade().into(imageView);
        Glide.with(context)
                .load(url)
                .apply(new RequestOptions()
                        .error(R.drawable.no_content_tip)
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .placeholder(R.drawable.ic_image_loading))
                .into(imageView);
    }
    public static void displaySmallPhoto(Context context, ImageView imageView, String url) {
        if (imageView == null) {
            throw new IllegalArgumentException("argument error");
        }
//        Glide.with(context).load(url).asBitmap()
//                .diskCacheStrategy(DiskCacheStrategy.ALL)
//                .placeholder(R.drawable.ic_image_loading)
//                .error(R.drawable.ic_empty_picture)
//                .thumbnail(0.5f)
//                .into(imageView);

        Glide.with(context).asBitmap()
                .apply(new RequestOptions()
                        .error(R.drawable.no_content_tip)
                        .placeholder(R.drawable.ic_image_loading)
                        .diskCacheStrategy(DiskCacheStrategy.ALL))
                .thumbnail(0.5f)
                .load(url).into(imageView);

    }
    public static void displayBigPhoto(Context context, ImageView imageView, String url) {
        if (imageView == null) {
            throw new IllegalArgumentException("argument error");
        }
//        Glide.with(context).load(url).asBitmap()
//                .format(DecodeFormat.PREFER_ARGB_8888)
//                .diskCacheStrategy(DiskCacheStrategy.ALL)
//                .placeholder(R.drawable.ic_image_loading)
//                .error(R.drawable.ic_empty_picture)
//                .into(imageView);

        Glide.with(context).asBitmap()
                .apply(new RequestOptions()
                        .format(DecodeFormat.PREFER_ARGB_8888)
                        .error(R.drawable.no_content_tip)
                        .placeholder(R.drawable.ic_image_loading)
                        .diskCacheStrategy(DiskCacheStrategy.ALL))
                .load(url).into(imageView);
    }
    public static void display(Context context, ImageView imageView, int url) {
        if (imageView == null) {
            throw new IllegalArgumentException("argument error");
        }
//        Glide.with(context).load(url)
//                .diskCacheStrategy(DiskCacheStrategy.ALL)
//                .centerCrop()
//                .placeholder(R.drawable.ic_image_loading)
//                .error(R.drawable.ic_empty_picture)
//                .crossFade().into(imageView);
        Glide.with(context)
                .load(url)
                .apply(new RequestOptions()
                        .error(R.drawable.no_content_tip)
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .placeholder(R.drawable.ic_image_loading))
                .into(imageView);
    }
    public static void displayRound(Context context,ImageView imageView, String url) {
        if (imageView == null) {
            throw new IllegalArgumentException("argument error");
        }
//        Glide.with(context).load(url)
//                .diskCacheStrategy(DiskCacheStrategy.ALL)
//                .error(R.drawable.toux2)
//                .centerCrop().transform(new GlideRoundTransformUtil(context)).into(imageView);

        Glide.with(context)
                .load(url)
                .apply(new RequestOptions()
                        .error(R.drawable.toux2)
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .centerCrop()
                        .transform(new GlideRoundTransformUtil(context)))
                .into(imageView);
    }
    public static void displayRound(Context context,ImageView imageView, int resId) {
        if (imageView == null) {
            throw new IllegalArgumentException("argument error");
        }
//        Glide.with(context).load(resId)
//                .diskCacheStrategy(DiskCacheStrategy.ALL)
//                .error(R.drawable.toux2)
//                .centerCrop().transform(new GlideRoundTransformUtil(context)).into(imageView);
        Glide.with(context)
                .load(resId)
                .apply(new RequestOptions()
                        .error(R.drawable.toux2)
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .centerCrop()
                        .transform(new GlideRoundTransformUtil(context)))
                .into(imageView);
    }

}
