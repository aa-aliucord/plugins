package com.aliucord.plugins;

import android.app.Activity;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.ContextWrapper;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.text.SpannableStringBuilder;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.fragment.app.FragmentManager;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputLayout;

import com.aliucord.Constants;
import com.aliucord.Main;
import com.aliucord.HttpUtils;
import com.aliucord.Utils;
import com.aliucord.entities.Plugin;
import com.aliucord.widgets.BottomSheet;
import com.discord.databinding.UserProfileHeaderViewBinding;
import com.discord.widgets.user.profile.UserProfileHeaderView;
import com.discord.widgets.user.profile.UserProfileHeaderViewModel;
import com.discord.models.user.User;
import com.discord.utilities.color.ColorCompat;

import com.lytefast.flexinput.R$b;
import com.lytefast.flexinput.R$h;

import java.net.HttpURLConnection;
import java.io.InputStream;
import java.net.URL;
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
        manifest.version = "1.0.0";
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

    // // https://android.googlesource.com/platform/frameworks/support/+/refs/heads/marshmallow-release/v7/mediarouter/src/android/support/v7/app/MediaRouteButton.java#262
    // private Activity getActivity(View v) {
    //     // Gross way of unwrapping the Activity so we can get the FragmentManager
    //     Context context = v.getContext();
    //     while (context instanceof ContextWrapper) {
    //         if (context instanceof Activity) {
    //             return (Activity)context;
    //         }
    //         context = ((ContextWrapper)context).getBaseContext();
    //     }
    //     return null;
    // }

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
            patcher.patch(className, "updateViewState", (_this, args, ret) -> {
                try {
                    // UserProfileHeaderViewModel.ViewState.Loaded loaded = args.get(0);
                    // ViewGroup headerView = ((ViewGroup) _this);
                    // headerView.getChildAt(0).setBackgroundColor(Color.parseColor("#fff"));
                    ColorDrawable cd = new ColorDrawable(0xFFFFFF00);
                    UserProfileHeaderViewBinding binding = ((UserProfileHeaderViewBinding) bindingField.get(_this));
                    Main.logger.info("hi");
                    binding.getRoot().setBackground(cd);
                    Matcher matcher = Pattern.compile(Long.toString(((UserProfileHeaderViewModel.ViewState.Loaded) args.get(0)).getUser().getId()) + regex, Pattern.DOTALL).matcher(css);
    
                    if (matcher.find()) {
                        Main.logger.info("Full match: " + matcher.group(0));
                        
                        Main.logger.info("Group 1: " + matcher.group(1));
                        String imageUrl = matcher.group(1);

                        new Thread(() -> {
                            try {
                                InputStream stream = HttpUtils.request(imageUrl, null);
                                new Handler(Looper.getMainLooper()).post(() -> {
                                    Main.logger.info("got image");
                                    BitmapDrawable drawable = new BitmapDrawable(ctx.getResources(), stream);
                                    binding.getRoot().setBackground(drawable);
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