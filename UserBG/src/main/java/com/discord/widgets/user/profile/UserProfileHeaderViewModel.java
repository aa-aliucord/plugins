package com.discord.widgets.user.profile;
import com.discord.models.user.User;
public final class UserProfileHeaderViewModel {
    public static abstract class ViewState {
        public static final class Loaded extends ViewState {
            User user;
            public Loaded(User u) {
                this.user = u;
            }
            public final User getUser() {
                return this.user;
            }
        }
    }
}