package com.aliucord.plugins;

import android.app.Activity;
import android.content.Context;
import android.content.ContextWrapper;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.BitmapDrawable;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.view.ViewGroup;
import android.view.Gravity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentManager;
import com.aliucord.Main;
import com.aliucord.HttpUtils;
import com.aliucord.Utils;
import com.aliucord.entities.Plugin;
import com.discord.databinding.UserProfileHeaderViewBinding;
import com.discord.widgets.user.profile.UserProfileHeaderView;
import com.discord.widgets.user.profile.UserProfileHeaderViewModel;
import com.discord.views.user.UserAvatarPresenceView;
import com.discord.models.user.User;
import com.discord.utilities.images.MGImages;

import com.facebook.drawee.view.SimpleDraweeView;

import com.hipmob.gifanimationdrawable.GifAnimationDrawable;

import java.io.InputStream;
import java.io.BufferedInputStream;
import java.net.URLConnection;
import java.lang.Thread;
import java.lang.reflect.Field;
import java.lang.Throwable;
import java.lang.Long;
import java.util.*;
import java.util.regex.Pattern;
import java.util.regex.Matcher;

@SuppressWarnings({"unchecked", "unused"})
public class UserBG extends Plugin {
    @NonNull
    @Override
    public Manifest getManifest() {
        Manifest manifest = new Manifest();
        manifest.authors = new Manifest.Author[]{ new Manifest.Author("AAGaming", 373833473091436546L) };
        manifest.description = "Adds backgrounds to profiles.";
        manifest.version = "1.0.1";
        manifest.updateUrl = "https://raw.githubusercontent.com/aa-aliucord/plugins/builds/updater.json";
        return manifest;
    }

    private static final String url = "https://raw.githubusercontent.com/Discord-Custom-Covers/usrbg/master/dist/usrbg.css";
    private final String regex = ".*?\\('(.*?)'";
    private String css;
    private static final String className = "com.discord.widgets.user.profile.UserProfileHeaderView";
    public static Map<String, List<String>> getClassesToPatch() {
        Map<String, List<String>> map = new HashMap<>();
        map.put(className, Arrays.asList("updateViewState"));
        return map;
    }

    @Override
    public void start(Context ctx) {
        try {
            new Thread(() -> {
                try {
                    css = HttpUtils.stringRequest(url, null);
                } catch (Throwable e) { Main.logger.error(e); }
            }).start();

            Field bindingField = UserProfileHeaderView.class.getDeclaredField("binding");
            bindingField.setAccessible(true);
            float radii = Utils.dpToPx(48);
            patcher.patch(className, "updateViewState", (_this, args, ret) -> {
                try {
                    // UserProfileHeaderViewModel.ViewState.Loaded loaded = args.get(0);
                    // ViewGroup headerView = ((ViewGroup) _this);
                    // headerView.getChildAt(0).setBackgroundColor(Color.parseColor("#fff"));
                    UserProfileHeaderViewBinding binding = ((UserProfileHeaderViewBinding) bindingField.get(_this));
                    // Main.logger.info("hi");
                    Matcher matcher = Pattern.compile(Long.toString(((UserProfileHeaderViewModel.ViewState.Loaded) args.get(0)).getUser().getId()) + regex, Pattern.DOTALL).matcher(css);
    
                    if (matcher.find()) {
                        // Main.logger.info("Full match: " + matcher.group(0));
                        
                        // Main.logger.info("Group 1: " + matcher.group(1));
                        String imageUrl = matcher.group(1);

                        new Thread(() -> {
                            try {
                                // InputStream stream = HttpUtils.request("https://dummyimage.com/1920x1080/000/fff.png", null);
                                InputStream stream = HttpUtils.request(imageUrl, null);
                                BufferedInputStream bStream = new BufferedInputStream(stream);
                                // Main.logger.info("got image");
                                String mime = URLConnection.guessContentTypeFromStream(bStream);
                                Drawable drawable;
                                final GifAnimationDrawable gif;
                                if (mime == "image/gif") {
                                    // Main.logger.info("gif");
                                    gif = new GifAnimationDrawable(bStream);
                                    drawable = gif;
                                } else {
                                    BitmapDrawable bitmap = new BitmapDrawable(ctx.getResources(), stream);
                                    bitmap.setGravity(Gravity.CENTER_HORIZONTAL);
                                    drawable = bitmap;
                                    gif = null;
                                }
                                // Main.logger.info(mime);
                                new Handler(Looper.getMainLooper()).post(() -> {
                                    SimpleDraweeView drawee = ((UserAvatarPresenceView) binding.b).h.b;
                                    MGImages.setRoundingParams(drawee, radii, false, null, null, null);
                                    ((View) binding.getRoot().getParent()).setBackground(drawable);
                                    if (mime == "image/gif") {
                                        gif.start();
                                    }
                                });
                            } catch (Throwable e) { Main.logger.error(e); }
                        }).start();

                    }
                } catch (Throwable e) { Main.logger.error(e); }
                return ret;
            });
        } catch (Throwable e) { Main.logger.error(e); }
    }

    @Override
    public void stop(Context context) {
        patcher.unpatchAll();
        css = null;
    }
}