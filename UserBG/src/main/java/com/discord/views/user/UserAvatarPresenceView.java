package com.discord.views.user;

import android.widget.RelativeLayout;
import android.content.Context;
import android.util.AttributeSet;

import com.discord.views.StatusView;

import c.a.h.g1;

@SuppressWarnings("unused")
public final class UserAvatarPresenceView extends RelativeLayout {
    public g1 h;

    public UserAvatarPresenceView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    private StatusView getStatusView() { return null; }
    public final void setAvatarBackgroundColor(int color) {}
}