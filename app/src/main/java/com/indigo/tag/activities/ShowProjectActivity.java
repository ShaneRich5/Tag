package com.indigo.tag.activities;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import com.indigo.tag.R;
import com.indigo.tag.events.LoginEvent;
import com.indigo.tag.events.LogoutEvent;
import com.indigo.tag.models.Project;

import org.greenrobot.eventbus.Subscribe;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Shane on 7/22/2016.
 */
public class ShowProjectActivity extends BaseActivity {
    public static final String TAG = ShowProjectActivity.class.getSimpleName();

    @BindView(R.id.toolbar) Toolbar mToolbar;
    @BindView(R.id.fab_add_location) FloatingActionButton mFabAddLocation;
    @BindView(R.id.edit_text_name) EditText mEditTextName;
    @BindView(R.id.edit_text_description) EditText mEditTextDescription;

    private Project mProject;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_project);
        ButterKnife.bind(this);
        initializeProject();
        setupToolbar();
        setupRecyclerView();
    }

    private void setupRecyclerView() {

    }

    private void initializeProject() {
        mProject = (Project) getIntent().getSerializableExtra(Project.class.getSimpleName());

        if (mProject == null) {
            Toast.makeText(this, "Failed to load project", Toast.LENGTH_SHORT).show();
            finish();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_show_project, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("ConstantConditions")
    private void setupToolbar() {
        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle(mProject.getName());
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mEditTextName.setText(mProject.getName());
        mEditTextDescription.setText(mProject.getDescription());
    }

    @OnClick(R.id.fab_add_location)
    public void onFabAddLocationClicked(FloatingActionButton fab) {

    }

    @Subscribe
    public void onEvent(LoginEvent event) {

    }

    @Subscribe
    public void onEvent(LogoutEvent event) {

    }
}
