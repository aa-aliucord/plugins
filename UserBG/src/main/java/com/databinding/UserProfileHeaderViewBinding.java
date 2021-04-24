package com.discord.databinding;

import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.Guideline;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewbinding.ViewBinding;
import com.discord.utilities.view.text.SimpleDraweeSpanTextView;
// import com.discord.views.UsernameView;
// import com.discord.views.user.UserAvatarPresenceView;

public final class UserProfileHeaderViewBinding implements ViewBinding {
    @NonNull
    public final ConstraintLayout a;
    // @NonNull
    // public final UserAvatarPresenceView b;
    @NonNull
    public final RecyclerView c;
    @NonNull
    public final SimpleDraweeSpanTextView d;
    // @NonNull
    // public final UsernameView e;
    @NonNull
    public final TextView f;

    public UserProfileHeaderViewBinding(@NonNull ConstraintLayout constraintLayout, @NonNull Guideline guideline, /* @NonNull UserAvatarPresenceView userAvatarPresenceView */ Object unused, @NonNull RecyclerView recyclerView, @NonNull SimpleDraweeSpanTextView simpleDraweeSpanTextView, @NonNull LinearLayout linearLayout, /*@NonNull UsernameView usernameView*/ Object unused2, @NonNull TextView textView, @NonNull ConstraintLayout constraintLayout2) {
        this.a = constraintLayout;
        // this.b = userAvatarPresenceView;
        this.c = recyclerView;
        this.d = simpleDraweeSpanTextView;
        // this.e = usernameView;
        this.f = textView;
    }

    @Override // androidx.viewbinding.ViewBinding
    @NonNull
    public View getRoot() {
        return this.a;
    }
}
