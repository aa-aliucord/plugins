package com.discord.databinding;

import android.view.View;
import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.viewbinding.ViewBinding;
import com.discord.views.user.UserAvatarPresenceView;

public final class UserProfileHeaderViewBinding implements ViewBinding {
    @NonNull
    public final ConstraintLayout a;
    @NonNull
    public final UserAvatarPresenceView b;
    // @NonNull
    // public final RecyclerView c;
    // @NonNull
    // public final SimpleDraweeSpanTextView d;
    // @NonNull
    // public final UsernameView e;
    // @NonNull
    // public final TextView f;

    public UserProfileHeaderViewBinding(@NonNull ConstraintLayout constraintLayout, /*@NonNull Guideline guideline*/ Object unusedGuideline, @NonNull UserAvatarPresenceView userAvatarPresenceView, /*@NonNull RecyclerView recyclerView*/ Object unused, /*@NonNull SimpleDraweeSpanTextView simpleDraweeSpanTextView*/ Object unused2, /*@NonNull LinearLayout linearLayout*/ Object unused3, /*@NonNull UsernameView usernameView*/ Object unused4, /*@NonNull TextView textView*/ Object unused5, /*@NonNull ConstraintLayout constraintLayout2*/ Object unused6) {
        this.a = constraintLayout;
        this.b = userAvatarPresenceView;
        // this.c = recyclerView;
        // this.d = simpleDraweeSpanTextView;
        // this.e = usernameView;
        // this.f = textView;
    }

    @Override // androidx.viewbinding.ViewBinding
    @NonNull
    public View getRoot() {
        return this.a;
    }
}
