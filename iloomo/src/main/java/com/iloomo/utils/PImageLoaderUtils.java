package com.iloomo.utils;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.gif.GifDrawable;
import com.iloomo.paysdk.R;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.imageaware.ImageViewAware;

/**
 * UIL 工具类
 * Created by sky on 15/7/26.
 */
public class PImageLoaderUtils {


    public static PImageLoaderUtils pImageLoaderUtils;

    public static PImageLoaderUtils getInstance() {
        if (pImageLoaderUtils == null) {
            pImageLoaderUtils = new PImageLoaderUtils();
        }
        return pImageLoaderUtils;
    }

    /**
     * display local image
     *
     * @param uri
     * @param imageView
     * @param options
     */
    public void displayLocalImage(String uri, ImageView imageView, DisplayImageOptions options) {
        ImageLoader.getInstance().displayImage("file://" + uri, new ImageViewAware(imageView), options, null, null);
    }

    /**
     * display Drawable image
     *
     * @param uri
     * @param imageView
     * @param options
     */
    public void displayDrawableImage(String uri, ImageView imageView, DisplayImageOptions options) {
        ImageLoader.getInstance().displayImage("drawable://" + uri, new ImageViewAware(imageView), options, null, null);
    }


    public void displayuserHand(String uri, ImageView imageView, Context context) {
        Glide.with(context)
                .load(uri)
                .placeholder(R.drawable.default_head)
                .override(100, 100)
                .error(R.drawable.default_head).crossFade().into(imageView);
    }

    public void setImageHead(ImageView iview, String url, Context context) {
        Glide.with(context).load(url).placeholder(R.drawable.default_head)
                .error(R.drawable.default_head).transform(new GlideRoundTransform(context, 100)).crossFade().into(iview);
    }

    public void displaySmallpix(String uri, ImageView imageView, Context context) {
        Glide.with(context)
                .load(uri)
                .placeholder(R.drawable.loadingview)
                .error(R.drawable.gray).crossFade().into(imageView);
    }

}
