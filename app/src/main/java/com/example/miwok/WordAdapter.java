package com.example.miwok;

import android.content.Context;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;

import java.util.ArrayList;

public class WordAdapter extends ArrayAdapter<Word> {

    private int mColor;

    public WordAdapter(Context context, ArrayList<Word> wordList,int color){
        super(context,0,wordList);
        mColor = color;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if(convertView == null)
            convertView = LayoutInflater.from(getContext()).inflate(
                    R.layout.list_item, parent, false);

        Word currentWord = getItem(position);

        TextView miwokTranslation = convertView.findViewById(R.id.miwok_text_view);
        miwokTranslation.setText(currentWord.getMiwokTranslation());

        TextView defaultTranslation = convertView.findViewById(R.id.default_text_view);
        defaultTranslation.setText(currentWord.getDefaultTranslation());

        ImageView imageView = convertView.findViewById(R.id.image);

        if(currentWord.hasImage()){
            imageView.setImageResource(currentWord.getMimage());
            imageView.setVisibility(View.VISIBLE);
        }
        else{
            imageView.setVisibility(View.GONE);
        }

        View textContainer = convertView.findViewById(R.id.list_item);

        int color = ContextCompat.getColor(getContext(), mColor);

        textContainer.setBackgroundColor(color);

        return convertView;
    }
}
