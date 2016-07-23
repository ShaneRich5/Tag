package com.indigo.tag.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.indigo.tag.R;
import com.indigo.tag.events.LoginEvent;
import com.indigo.tag.events.LogoutEvent;

import org.greenrobot.eventbus.Subscribe;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Shane on 7/22/2016.
 */
public class LoginActivity extends BaseActivity {
    public static final String TAG = LoginActivity.class.getSimpleName();
    private static final String LOGIN_STATE = "login_state";
    private static final String REGISTER_STATE = "register_state";

    @BindView(R.id.text_input_first_name) TextInputEditText mEditTextFirstName;
    @BindView(R.id.text_input_last_name) TextInputEditText mEditTextLastName;
    @BindView(R.id.text_input_email) TextInputEditText mEditTextEmail;
    @BindView(R.id.text_input_password) TextInputEditText mEditTextPassword;
    @BindView(R.id.text_input_confirm) TextInputEditText mEditTextConfirm;
    @BindView(R.id.text_connect_state) TextView mTextViewConnectState;
    @BindView(R.id.button_connect) Button mButtonConnect;
    @BindView(R.id.button_cancel) Button mButtonCancel;

    private String mConnectState = LOGIN_STATE;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.button_connect)
    public void onConnectButtonClicked(Button connectButton) {
        if (mConnectState.equals(LOGIN_STATE))
            attemptLogin();
        else if (mConnectState.equals(REGISTER_STATE))
            attemptRegister();

        startActivity(new Intent(LoginActivity.this, MainActivity.class));
    }

    private void attemptRegister() {
        final String email = mEditTextEmail.getText().toString();
        final String password = mEditTextPassword.getText().toString();
        final String firstName = mEditTextFirstName.getText().toString();
        final String lastName = mEditTextLastName.getText().toString();
        final String confirm = mEditTextConfirm.getText().toString();

        if (email.isEmpty() || password.isEmpty() || firstName.isEmpty() ||
                lastName.isEmpty() || confirm.isEmpty()) {
            // TODO
            return;
        }

        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnSuccessListener(authResult -> {
                    final FirebaseUser user = authResult.getUser();
                    final DatabaseReference userRef = mDatabase.child("users").child(user.getUid());

                    userRef.child("first_name").setValue(firstName);
                    userRef.child("last_name").setValue(lastName);

                    startActivity(new Intent(LoginActivity.this, MainActivity.class));
                })
                .addOnCompleteListener(task -> {
                    Log.d(TAG, "createUserWithEmail:onComplete:" + task.isSuccessful());
                    if (!task.isSuccessful())
                        Toast.makeText(LoginActivity.this, "Authentication failed.",
                                Toast.LENGTH_SHORT).show();
                });
    }

    private void attemptLogin() {
        final String email = mEditTextEmail.getText().toString();
        final String password = mEditTextPassword.getText().toString();

        if (email.isEmpty()) {
            // TODO
            return;
        }

        if (password.isEmpty()) {
            // TODO
            return;
        }

        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, task -> {
                    Log.d(TAG, "signInWithEmail:onComplete:" + task.isSuccessful());

                    if (!task.isSuccessful()) {
                        Log.w(TAG, "signInWithEmail", task.getException());
                        Toast.makeText(LoginActivity.this, "Authentication failed.",
                                Toast.LENGTH_SHORT).show();
                        return;
                    }

                    startActivity(new Intent(LoginActivity.this, MainActivity.class));
                });
    }

    @OnClick(R.id.button_cancel)
    public void onCancelButtonClicked(Button cancelButton) {
        startActivity(new Intent(LoginActivity.this, MainActivity.class));
    }

    @OnClick(R.id.text_connect_state)
    public void onConnectButtonClicked(TextView toggleText) {
        if (mConnectState.equals(LOGIN_STATE)) {
            mConnectState = REGISTER_STATE;
            toggleRegisterViewsVisibility(true);
        } else if (mConnectState.equals(REGISTER_STATE)) {
            mConnectState = LOGIN_STATE;
            toggleRegisterViewsVisibility(false);
        }
    }

    @Subscribe
    public void onEvent(LoginEvent event) {
        Toast.makeText(this, "Already registered", Toast.LENGTH_SHORT).show();
        startActivity(new Intent(LoginActivity.this, MainActivity.class));
    }

    @Subscribe
    public void onEvent(LogoutEvent event) {

    }

    private void toggleRegisterViewsVisibility(boolean isVisible) {
        int visibility = (isVisible) ? View.VISIBLE : View.GONE;

        mEditTextFirstName.setVisibility(visibility);
        mEditTextLastName.setVisibility(visibility);
        mEditTextConfirm.setVisibility(visibility);

        final String connectHelpMessage = (isVisible) ?
                getResources().getString(R.string.already_registered) :
                getResources().getString(R.string.not_registered);

        mTextViewConnectState.setText(connectHelpMessage);
    }
}
