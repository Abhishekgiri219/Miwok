package com.example.miwok;

public class Word {

    /**
     * corresponding image for the word
     */

    private static final int NO_IMAGE_PROVIDED = -1;

    private int mImage = NO_IMAGE_PROVIDED;

    /** Default translation for the word */
    private String mDefaultTranslation;

    /** Miwok translation for the word */
    private String mMiwokTranslation;

    /**
     * pronunciation for the corresponding word
     */

    private int mAudio;

    /**
     * Create a new Word object.
     *
     * @param defaultTranslation is the word in a language that the user is already familiar with
     *                           (such as English)
     * @param miwokTranslation is the word in the Miwok language
     */
    public Word(int image,String defaultTranslation, String miwokTranslation,int audio) {
        mImage = image;
        mDefaultTranslation = defaultTranslation;
        mMiwokTranslation = miwokTranslation;
        mAudio = audio;
    }

    public Word(String defaultTranslation, String miwokTranslation,int audio) {
        mDefaultTranslation = defaultTranslation;
        mMiwokTranslation = miwokTranslation;
        mAudio = audio;
    }

    public int getMimage() { return mImage; }

    /**
     * Get the default translation of the word.
     */
    public String getDefaultTranslation() {
        return mDefaultTranslation;
    }

    /**
     * Get the Miwok translation of the word.
     */
    public String getMiwokTranslation() {
        return mMiwokTranslation;
    }

    public int getAudio() { return mAudio; }

    public boolean hasImage(){
        return mImage != NO_IMAGE_PROVIDED;
    }

}
