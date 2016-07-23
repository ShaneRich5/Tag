package com.indigo.tag.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.indigo.tag.R;
import com.indigo.tag.adapters.ProjectAdapter;
import com.indigo.tag.events.LoginEvent;
import com.indigo.tag.events.LogoutEvent;
import com.indigo.tag.models.Project;
import com.indigo.tag.views.DividerItemDecoration;

import org.greenrobot.eventbus.Subscribe;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends BaseActivity {
    public static final String TAG = MainActivity.class.getSimpleName();

    @BindView(R.id.toolbar) Toolbar toolbar;
    @BindView(R.id.coordinator_layout) CoordinatorLayout mCoordinatorLayout;
    @BindView(R.id.recycler_projects) RecyclerView mRecyclerViewProject;
    @BindView(R.id.fab_add_project) FloatingActionButton mFabAddProject;

    private ProjectAdapter mProjectAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        setupCoordinateRecycler();
        loadProjects();
    }

    private void loadProjects() {
        // TODO
    }

    private void setupCoordinateRecycler() {
        mProjectAdapter = new ProjectAdapter(this);
        mRecyclerViewProject.setAdapter(mProjectAdapter);
        mRecyclerViewProject.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL_LIST));
        mRecyclerViewProject.setLayoutManager(new LinearLayoutManager(this));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()) {
            case R.id.action_connect:
                startActivity(new Intent(MainActivity.this, LoginActivity.class));
                break;
            case R.id.action_settings:
                Snackbar.make(mCoordinatorLayout, "Open Settings", Snackbar.LENGTH_SHORT).show();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @OnClick(R.id.fab_add_project)
    public void onAddProjectButtonClicked(FloatingActionButton fab) {
        final View promptView = LayoutInflater.from(this)
                .inflate(R.layout.dialog_new_project, null);

        AlertDialog.Builder alertBuilder = new AlertDialog.Builder(this);

        final EditText nameEditText = (TextInputEditText) promptView
                .findViewById(R.id.text_input_name);

        alertBuilder
                .setCancelable(true)
                .setTitle("New Project")
                .setView(promptView)
                .setPositiveButton("Save", (dialog, i) -> {
                    final String name = nameEditText.getText().toString();

                    if (name.isEmpty()) {
                        Toast.makeText(MainActivity.this, "No name provided", Toast.LENGTH_SHORT)
                                .show();
                        return;
                    }

                    final Project newProject = new Project();
                    newProject.setName(name);
                    mProjectAdapter.addEntity(newProject);
                }).setNegativeButton("Cancel", (dialog, i) -> {
                    dialog.cancel();
                }).create().show();
    }

    @Subscribe
    public void onEvent(LoginEvent event) {

    }

    @Subscribe
    public void onEvent(LogoutEvent event) {

    }
}
