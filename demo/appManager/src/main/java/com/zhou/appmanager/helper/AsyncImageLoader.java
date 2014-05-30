package com.zhou.appmanager.helper;

import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.os.Message;
import android.widget.ImageView;

import com.zhou.appmanager.service.MyApplication;

import java.io.IOException;
import java.io.InputStream;
import java.lang.ref.SoftReference;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;

/**
 * 图片异步加载器
 */
public class AsyncImageLoader {

    //SoftReference是软引用，是为了更好的为了系统回收变量
    private static HashMap<String, SoftReference<Drawable>> imageCache;

    public AsyncImageLoader() {
        if (imageCache == null)
            imageCache = new HashMap<String, SoftReference<Drawable>>();
    }

    public static void putDrawable(String url, Drawable drawable) {
        if (imageCache == null)
            imageCache = new HashMap<String, SoftReference<Drawable>>();
        imageCache.put(url, new SoftReference<Drawable>(drawable));
    }

    /**
     * @param imageUrl  图片路径也是缓存的key
     * @param imageView 目标
     * @param type      @see DrawableLoaderFactory.LoaderType
     * @return
     */
    public Drawable loadDrawable(final String imageUrl, final ImageView imageView, final DrawableLoaderFactory.LoaderType type) {
        if (imageCache.containsKey(imageUrl)) {
            //从缓存中获取
            SoftReference<Drawable> softReference = imageCache.get(imageUrl);
            Drawable drawable = softReference.get();
            if (drawable != null) {
                imageView.setImageDrawable(drawable);
                return drawable;
            }
        }
        final Handler handler = new Handler() {
            @Override
            public void handleMessage(Message message) {
                String path = null;
                if (imageView.getTag() != null && String.class.isInstance(imageView.getTag()))
                    path = (String) imageView.getTag();
                if (imageUrl.equals(path)) {
                    imageView.setImageDrawable((Drawable) message.obj);
                }
            }
        };

        //建立新一个新的线程下载图片
        new Thread() {
            @Override
            public void run() {
                Drawable drawable = DrawableLoaderFactory.getLoader(type).loadImage(imageUrl);
                imageCache.put(imageUrl, new SoftReference<Drawable>(drawable));
                Message message = handler.obtainMessage(0, drawable);
                handler.sendMessage(message);
            }
        }.start();
        return null;
    }

    public static interface DrawableLoader {
        public abstract Drawable loadImage(String path);
    }

    public static class DrawableLoaderFactory {

        public enum LoaderType {
            URL, AppIcon;
        }

        public static DrawableLoader getLoader(LoaderType type) {
            DrawableLoader loader = null;
            switch (type) {
                case AppIcon:
                    loader = new IconLoader();
                    break;
                case URL:
                    loader = new UrlLoader();
                    break;
                default:
                    break;
            }
            return loader;
        }

        static class UrlLoader implements DrawableLoader {
            public Drawable loadImage(String url) {
                URL m;
                InputStream i = null;
                try {
                    m = new URL(url);
                    i = (InputStream) m.getContent();
                } catch (MalformedURLException e1) {
                    e1.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                Drawable d = Drawable.createFromStream(i, "src");
                return d;
            }
        }

        static class IconLoader implements DrawableLoader {

            @Override
            public Drawable loadImage(String pkgName) {
                PackageManager pm = MyApplication.getInstance().getPackageManager();
                try {
                    return pm.getApplicationIcon(pkgName);
                } catch (PackageManager.NameNotFoundException e) {
                    e.printStackTrace();
                    return null;
                }
            }
        }
    }


    //回调接口
    public interface ImageCallback {
        public void imageLoaded(Drawable imageDrawable, ImageView imageView, String imageUrl);
    }
}