package com.example.nowingo.newsday.quicksidebardemo;

import android.support.v7.widget.RecyclerView;

import com.example.nowingo.newsday.quicksidebardemo.model.City;
import com.example.nowingo.newsday.quicksidebardemo.model.ExpressCom;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;


/**
 * Adapter holding a list of animal names of type String. Note that each item must be unique.
 */
public abstract class ExpressComAdapter<VH extends RecyclerView.ViewHolder>
    extends RecyclerView.Adapter<VH> {
  private ArrayList<ExpressCom> items = new ArrayList<ExpressCom>();

  public ExpressComAdapter() {
    setHasStableIds(true);
  }

  public void add(ExpressCom object) {
    items.add(object);
    notifyDataSetChanged();
  }

  public void add(int index, ExpressCom object) {
    items.add(index, object);
    notifyDataSetChanged();
  }

  public void addAll(Collection<? extends ExpressCom> collection) {
    if (collection != null) {
      items.addAll(collection);
      notifyDataSetChanged();
    }
  }

  public void addAll(ExpressCom... items) {
    addAll(Arrays.asList(items));
  }

  public void clear() {
    items.clear();
    notifyDataSetChanged();
  }

  public void remove(String object) {
    items.remove(object);
    notifyDataSetChanged();
  }

  public ExpressCom getItem(int position) {
    return items.get(position);
  }

  @Override
  public long getItemId(int position) {
    return getItem(position).hashCode();
  }

  @Override
  public int getItemCount() {
    return items.size();
  }
}
