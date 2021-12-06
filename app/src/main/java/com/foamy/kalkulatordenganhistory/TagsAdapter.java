package com.foamy.kalkulatordenganhistory;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;

public class TagsAdapter extends RecyclerView.Adapter<TagsAdapter.TagsViewHolder> {
    Context context;
    ArrayList<String>arrayList;

    public TagsAdapter(ArrayList<String> arrayList) {
        this.arrayList = arrayList;
    }

    @NonNull
    @Override
    public TagsAdapter.TagsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        TagsViewHolder holder = new TagsViewHolder(inflater.inflate(R.layout.layout_history,parent,false));
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull TagsAdapter.TagsViewHolder holder, @SuppressLint("RecyclerView") int position) {
        holder.tagText.setText(arrayList.get(position));
        holder.Llayout.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                Toast.makeText(v.getContext(), arrayList.get(position)+" Removed", Toast.LENGTH_SHORT).show();
                arrayList.remove(position);
                notifyItemRemoved(position);
                MainActivity.List_History = arrayList;
                MainActivity.strlistHistory = MainActivity.gson.toJson(MainActivity.List_History);
                MainActivity.pref.edit().putString(context.getString(R.string.string_history), MainActivity.strlistHistory).apply();
                MainActivity.rec_History.setAdapter(new TagsAdapter(arrayList));
                MainActivity.rec_History.setLayoutManager(new LinearLayoutManager(context));
                return true;
            }
        });
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class TagsViewHolder extends RecyclerView.ViewHolder {
        public TextView tagText;
        public LinearLayout Llayout;
        public TagsViewHolder(@NonNull View itemView) {
            super(itemView);
            tagText = itemView.findViewById(R.id.txtHistory);
            Llayout = itemView.findViewById(R.id.ea);
        }
    }
}
