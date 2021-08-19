package com.rivierasoft.personaldictionary.adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.rivierasoft.personaldictionary.MyDatabase;
import com.rivierasoft.personaldictionary.OnItemClickListener;
import com.rivierasoft.personaldictionary.R;
import com.rivierasoft.personaldictionary.databinding.LayoutWordBinding;
import com.rivierasoft.personaldictionary.model.Word;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class WordsAdapter extends RecyclerView.Adapter<WordsAdapter.WordsViewHolder> {

    private Context context;
    private ArrayList<Word> words;
    private OnItemClickListener listener;

    public WordsAdapter(Context context, ArrayList<Word> words, OnItemClickListener listener) {
        this.context = context;
        this.words = words;
        this.listener = listener;
    }

    @NonNull
    @NotNull
    @Override
    public WordsViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        return new WordsViewHolder(LayoutWordBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    @Override
    public void onBindViewHolder(@NonNull @NotNull WordsAdapter.WordsViewHolder holder, int position) {
        Word word = words.get(position);
        holder.binding.wordTV.setText(word.getText());
        holder.binding.wordView.setBackground(context.getDrawable(word.getColor()));
        holder.binding.wordTVNote.setText(word.getNote());

        if (word.isFavorite())
            holder.binding.wordIVFav.setImageResource(R.drawable.ic_favorite);
        else holder.binding.wordIVFav.setImageResource(R.drawable.ic_favorite_border);

        holder.binding.wordTV.setOnClickListener(v ->{
            if (holder.binding.wordLLOpe.getVisibility() == View.GONE) {
                if (word.getNote() != null)
                    holder.binding.wordTVNote.setVisibility(View.VISIBLE);
                holder.binding.wordLLOpe.setVisibility(View.VISIBLE);
            } else {
                holder.binding.wordTVNote.setVisibility(View.GONE);
                holder.binding.wordLLOpe.setVisibility(View.GONE);
            }
        });

        holder.binding.wordIVFav.setOnClickListener(v ->
                listener.onClick(position, word.getId(), "fav"));

        holder.binding.wordIVVoice.setOnClickListener(v ->
                listener.onClick(position, word.getId(), "voice"));

        holder.binding.wordIVDelete.setOnClickListener(v ->
            listener.onClick(position, word.getId(), "del"));

        holder.binding.wordIVEdit.setOnClickListener(v ->
            listener.onClick(position, word.getId(), "edit"));
    }

    @Override
    public int getItemCount() {
        return words.size();
    }

    class WordsViewHolder extends RecyclerView.ViewHolder {

        private LayoutWordBinding binding;

        public WordsViewHolder(LayoutWordBinding binding) {
            super(binding.getRoot());

            this.binding = binding;
        }
    }
}
