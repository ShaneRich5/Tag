package com.indigo.tag.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.indigo.tag.R;
import com.indigo.tag.activities.ShowProjectActivity;
import com.indigo.tag.models.Project;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Shane on 7/22/2016.
 */
public class ProjectAdapter extends BaseAdapter<Project, ProjectAdapter.ProjectViewHolder> {
    public static final String TAG = ProjectAdapter.class.getSimpleName();

    public ProjectAdapter(Context context) {
        super(context);
    }

    @Override
    public ProjectViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final View rootView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_row_project, parent, false);
        return new ProjectViewHolder(rootView);
    }

    @Override
    public void onBindViewHolder(ProjectViewHolder holder, int position) {
        final Project project = getEntity(position);
        holder.mTextViewName.setText(project.getName());
        holder.mLinearLayoutContainer.setOnClickListener(view -> {
            final Intent intent = new Intent(mContext, ShowProjectActivity.class);
            intent.putExtra(Project.class.getSimpleName(), project);
            mContext.startActivity(intent);
        });
    }

    class ProjectViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.linear_layout_container) LinearLayout mLinearLayoutContainer;
        @BindView(R.id.text_name) TextView mTextViewName;

        public ProjectViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
