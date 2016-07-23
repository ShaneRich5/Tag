package com.indigo.tag.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.indigo.tag.events.LoginEvent;
import com.indigo.tag.events.LogoutEvent;

import org.greenrobot.eventbus.EventBus;

/**
 * Created by Shane on 7/22/2016.
 */
public abstract class BaseActivity extends AppCompatActivity {
    public static final String TAG = BaseActivity.class.getSimpleName();

    protected final DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReferenceFromUrl("https://tagg-72278.firebaseio.com/");
    protected final FirebaseAuth mAuth = FirebaseAuth.getInstance();
    private FirebaseAuth.AuthStateListener mAuthListener;
    protected EventBus baseEventBus;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        baseEventBus = EventBus.getDefault();
        baseEventBus.register(this);

        mAuthListener = firebaseAuth -> {
            final FirebaseUser user = firebaseAuth.getCurrentUser();

            if (user == null || user.isAnonymous())
                baseEventBus.post(new LogoutEvent());
            else
                baseEventBus.post(new LoginEvent(user));
        };
    }

    @Override
    protected void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (mAuthListener != null)
            mAuth.removeAuthStateListener(mAuthListener);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (baseEventBus != null && baseEventBus.isRegistered(this))
            baseEventBus.unregister(this);
    }

    public void signUserOut() {
        mAuth.signOut();
    }

    public boolean isUserSignedIn() {
        return getFirebaseUser() != null;
    }

    public FirebaseUser getFirebaseUser() {
        return FirebaseAuth.getInstance().getCurrentUser();
    }

    public void openLoginActivity() {
        startActivity(new Intent(this, LoginActivity.class));
    }
}
