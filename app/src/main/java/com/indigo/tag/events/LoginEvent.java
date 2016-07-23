package com.indigo.tag.events;

import com.google.firebase.auth.FirebaseUser;

/**
 * Created by Shane on 7/22/2016.
 */
public class LoginEvent {
    private FirebaseUser user;

    public LoginEvent(FirebaseUser user) {
        this.user = user;
    }

    public FirebaseUser getUser() {
        return user;
    }
}
