package com.example.miwok;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Objects;

public class ColorsFragment extends Fragment {

    private MediaPlayer mMediaPlayer;

    private AudioManager mAudioManager;
    private final AudioManager.OnAudioFocusChangeListener mOnAudioFocusChangeListener = new AudioManager.OnAudioFocusChangeListener() {
        @Override
        public void onAudioFocusChange(int focusChange) {
            if(focusChange == AudioManager.AUDIOFOCUS_LOSS_TRANSIENT || focusChange == AudioManager.AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK){
                mMediaPlayer.pause();
                mMediaPlayer.seekTo(0);
            }
            else
            if(focusChange == AudioManager.AUDIOFOCUS_GAIN){
                mMediaPlayer.start();
            }
            else
            if(focusChange == AudioManager.AUDIOFOCUS_LOSS){
                releaseMediaPlayer();
            }
        }
    };
    private final MediaPlayer.OnCompletionListener mOnCompletionListener = new MediaPlayer.OnCompletionListener() {
        @Override
        public void onCompletion(MediaPlayer mp) {
            releaseMediaPlayer();
        }
    };

    public ColorsFragment() {
        // Required empty public constructor
    }


    // TODO: Rename and change types and number of parameters
    public static ColorsFragment newInstance() {
        ColorsFragment fragment = new ColorsFragment();
        return fragment;
    }

    public void onStop() {
        super.onStop();
        releaseMediaPlayer();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.word_list, container, false);

        mAudioManager = (AudioManager) Objects.requireNonNull(getActivity()).getSystemService(Context.AUDIO_SERVICE);

        final ArrayList<Word> words = new ArrayList<Word>();
        words.add(new Word(R.drawable.color_red,"red", "weṭeṭṭi",R.raw.color_red));
        words.add(new Word(R.drawable.color_mustard_yellow,"mustard yellow", "chiwiiṭә",R.raw.color_mustard_yellow));
        words.add(new Word(R.drawable.color_dusty_yellow,"dusty yellow", "ṭopiisә",R.raw.color_dusty_yellow));
        words.add(new Word(R.drawable.color_green,"green", "chokokki",R.raw.color_green));
        words.add(new Word(R.drawable.color_brown,"brown", "ṭakaakki",R.raw.color_brown));
        words.add(new Word(R.drawable.color_gray,"gray", "ṭopoppi",R.raw.color_gray));
        words.add(new Word(R.drawable.color_black,"black", "kululli",R.raw.color_black));
        words.add(new Word(R.drawable.color_white,"white", "kelelli",R.raw.color_white));

        WordAdapter itemsAdapter = new WordAdapter(getActivity(), words,R.color.teal_200);

        ListView listView = rootView.findViewById(R.id.list);

        listView.setAdapter(itemsAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Word word = words.get(position);

                int result = mAudioManager.requestAudioFocus(mOnAudioFocusChangeListener,AudioManager.STREAM_MUSIC,AudioManager.AUDIOFOCUS_GAIN_TRANSIENT);

                if(result == AudioManager.AUDIOFOCUS_REQUEST_GRANTED){

                    //Body of your click handler
                    Thread thread = new Thread(new Runnable(){
                        @Override
                        public void run(){
                            mMediaPlayer = MediaPlayer.create(getActivity(),word.getAudio());
                            mMediaPlayer.start();
                            mMediaPlayer.setOnCompletionListener(mOnCompletionListener);
                        }
                    });
                    thread.start();

                }
            }
        });

        return rootView;
    }

    private void releaseMediaPlayer() {
        // If the media player is not null, then it may be currently playing a sound.
        if (mMediaPlayer != null) {
            // Regardless of the current state of the media player, release its resources
            // because we no longer need it.
            mMediaPlayer.release();

            // Set the media player back to null. For our code, we've decided that
            // setting the media player to null is an easy way to tell that the media player
            // is not configured to play an audio file at the moment.
            mMediaPlayer = null;

            mAudioManager.abandonAudioFocus(mOnAudioFocusChangeListener);
        }
    }
}