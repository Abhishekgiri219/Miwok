package com.example.miwok;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Objects;


public class NumbersFragment extends Fragment {

    private MediaPlayer mMediaPlayer;

    private AudioManager mAudioManager;
    private final AudioManager.OnAudioFocusChangeListener  mOnAudioFocusChangeListener = new AudioManager.OnAudioFocusChangeListener(){
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

    private final MediaPlayer.OnCompletionListener mOnCompletionListener = new MediaPlayer.OnCompletionListener(){
        @Override
        public void onCompletion(MediaPlayer mp) {
            releaseMediaPlayer();
        }
    };



    public NumbersFragment() {
        // Required empty public constructor
    }


    public static NumbersFragment newInstance() {
        return new NumbersFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.word_list, container, false);


        // audio manager requests audio focus
        mAudioManager = (AudioManager) Objects.requireNonNull(getActivity()).getSystemService(Context.AUDIO_SERVICE);

        final ArrayList<Word> words = new ArrayList<>();
        words.add(new Word(R.drawable.number_one,"one", "lutti",R.raw.number_one));
        words.add(new Word(R.drawable.number_two,"two", "otiiko",R.raw.number_two));
        words.add(new Word(R.drawable.number_three,"three", "tolookosu",R.raw.number_three));
        words.add(new Word(R.drawable.number_four,"four", "oyyisa",R.raw.number_four));
        words.add(new Word(R.drawable.number_five,"five", "massokka",R.raw.number_five));
        words.add(new Word(R.drawable.number_six,"six", "temmokka",R.raw.number_six));
        words.add(new Word(R.drawable.number_seven,"seven", "kenekaku",R.raw.number_seven));
        words.add(new Word(R.drawable.number_eight,"eight", "kawinta",R.raw.number_eight));
        words.add(new Word(R.drawable.number_nine,"nine", "wo’e",R.raw.number_nine));
        words.add(new Word(R.drawable.number_ten,"ten", "na’aacha",R.raw.number_ten));

        WordAdapter itemsAdapter = new WordAdapter(getActivity(), words,R.color.teal_200);

        ListView listView = rootView.findViewById(R.id.list);

        listView.setAdapter(itemsAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Word word = words.get(position);

//                Log.v("NumbersFragment.java","clicked");

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

    /**
     * Clean up the media player by releasing its resources.
     */
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

    @Override
    public void onStop() {
        super.onStop();
        releaseMediaPlayer();
    }
}