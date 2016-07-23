package com.indigo.tag.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.indigo.tag.R;
import com.indigo.tag.events.LoginEvent;
import com.indigo.tag.events.LogoutEvent;

import org.greenrobot.eventbus.Subscribe;

import butterknife.BindView;
import butterknife.ButterKnife;

public class NewLocationActivity extends BaseActivity {
    public static final String TAG = NewLocationActivity.class.getSimpleName();

    @BindView(R.id.coordinator_layout) CoordinatorLayout mCoordinatorLayout;
    @BindView(R.id.button_update) Button mButtonUpdate;
    @BindView(R.id.edit_text_name) EditText mEditViewName;
    @BindView(R.id.edit_text_latitude) EditText mEditTextLatitude;
    @BindView(R.id.edit_text_longitude) EditText mEditTextLongitude;
    @BindView(R.id.toolbar) Toolbar mToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_location);
        ButterKnife.bind(this);
        setupToolbar();
    }

    @SuppressWarnings("ConstantConditions")
    private void setupToolbar() {
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_new_location, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_save:
                saveNewLocation();
                break;
            case R.id.action_settings:
                Toast.makeText(this, "Settings", Toast.LENGTH_SHORT).show();
                break;
            case android.R.id.home:
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void saveNewLocation() {
        final String name = mEditViewName.getText().toString();
        final double latitude = Double.parseDouble(mEditTextLatitude.getText().toString());
        final double longitude = Double.parseDouble(mEditTextLongitude.getText().toString());

    }

    @Subscribe
    public void onEventMainThread(LoginEvent event) {

    }

    @Subscribe
    public void onEventMainThread(LogoutEvent event) {
        final Snackbar snackbar = Snackbar.make(mCoordinatorLayout, "Login to persist location", Snackbar.LENGTH_INDEFINITE);

        snackbar.setAction("Login", view -> {
                startActivity(new Intent(NewLocationActivity.this, LoginActivity.class));
            }).setAction("Dismiss", view -> {
                snackbar.dismiss();
            }).show();
    }
}
