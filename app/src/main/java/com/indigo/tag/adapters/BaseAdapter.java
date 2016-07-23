package com.indigo.tag.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Shane on 7/22/2016.
 */
public abstract class BaseAdapter<E, V extends RecyclerView.ViewHolder> extends RecyclerView.Adapter<V> {
    protected final EventBus mBus = EventBus.getDefault();
    protected final Context mContext;
    protected List<E> mEntities;

    public BaseAdapter(Context context, List<E> entities) {
        this.mContext = context;
        this.mEntities = entities;
    }

    public BaseAdapter(Context context) {
        this(context, new ArrayList<>());
    }

    public void setEntities(List<E> entities) {
        mEntities = entities;
        notifyDataSetChanged();
    }

    public void addEntities(List<E> entities) {
        mEntities.addAll(entities);
        notifyDataSetChanged();
    }

    public void addEntity(E entity) {
        mEntities.add(entity);
        notifyDataSetChanged();
    }

    public List<E> getEntities() {
        return mEntities;
    }

    public E getEntity(int position) {
        return getEntities().get(position);
    }

    public int getItemCount() {
        return mEntities.size();
    }
}
