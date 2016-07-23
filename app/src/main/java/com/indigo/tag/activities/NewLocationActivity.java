package com.indigo.tag.activities;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.crash.FirebaseCrash;
import com.google.firebase.database.DatabaseReference;
import com.indigo.tag.R;
import com.indigo.tag.events.LoginEvent;
import com.indigo.tag.events.LogoutEvent;
import com.indigo.tag.models.Coordinate;
import com.indigo.tag.models.Location;

import org.greenrobot.eventbus.Subscribe;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class NewLocationActivity extends BaseActivity {
    public static final String TAG = NewLocationActivity.class.getSimpleName();

    @BindView(R.id.coordinator_layout)
    CoordinatorLayout mCoordinatorLayout;
    @BindView(R.id.button_update)
    Button mButtonUpdate;
    @BindView(R.id.edit_text_name)
    EditText mEditViewName;
    @BindView(R.id.edit_text_latitude)
    EditText mEditTextLatitude;
    @BindView(R.id.edit_text_longitude)
    EditText mEditTextLongitude;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;

    private final int MY_PERMISSIONS_REQUEST_LOCATION = 1234;

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

        try {
            final double latitude = Double.parseDouble(mEditTextLatitude.getText().toString());
            final double longitude = Double.parseDouble(mEditTextLongitude.getText().toString());
            final Location location = new Location(name, new Coordinate(latitude, longitude));

            final DatabaseReference locationRef = mDatabase.child("locations").push();
            locationRef.child(locationRef.getKey())
                    .setValue(location)
                    .addOnSuccessListener(aVoid -> {
                        final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

                        if (user == null || user.isAnonymous()) {
                            return;
                        }

                        mDatabase.child("users").child(user.getUid())
                                .child("locations")
                                .child(locationRef.getKey())
                                .setValue(true);

                    }).addOnFailureListener(e -> {
                FirebaseCrash.report(e);
                Log.e(TAG, "location:onError", e);
            });

        } catch (NumberFormatException e) {
            FirebaseCrash.report(e);
            Log.e(TAG, "latitude", e);
            mEditTextLatitude.setError("Ensure you enter a number");
        }

    }

    @OnClick(R.id.button_update)
    public void onUpdateButtonClicked(Button button) {

        boolean fineLocationPermissionCheck = ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED;
        boolean courseLocationPermissionCheck = ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED;

        if (fineLocationPermissionCheck && courseLocationPermissionCheck) {

            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.READ_CONTACTS)) {

                final AlertDialog.Builder alertBuilder = new AlertDialog.Builder(this);
                alertBuilder.setTitle("Location Request")
                        .setMessage("Permission required to access device location")
                        .setPositiveButton("Ok", (dialogInterface, i) -> {
                            ActivityCompat.requestPermissions(this,
                                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION,
                                            Manifest.permission.ACCESS_COARSE_LOCATION},
                                    MY_PERMISSIONS_REQUEST_LOCATION);
                        })
                        .setNegativeButton("Cancel", (dialogInterface, i) -> {
                            dialogInterface.dismiss();
                        })
                        .show();
            } else {
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION,
                                Manifest.permission.ACCESS_COARSE_LOCATION},
                        MY_PERMISSIONS_REQUEST_LOCATION);
            }
        }
    }

    private void retrieveDeviceLocation() {
        final LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED)
            return;

        final android.location.Location lastKnownLocation = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);

        final String latitude = "" + lastKnownLocation.getLatitude();
        final String longitude = "" + lastKnownLocation.getLongitude();

        mEditTextLatitude.setText(latitude);
        mEditTextLongitude.setText(longitude);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_LOCATION:

                if (grantResults.length > 0 &&
                        grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    retrieveDeviceLocation();

                } else {

                    Snackbar.make(mCoordinatorLayout, "Need permission to automatically update location", Snackbar.LENGTH_LONG)
                            .setAction("Allow", view -> {

                            })
                            .show();

                }
                return;
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
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
