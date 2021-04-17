package com.longtraidep.beatbox;

import android.content.Context;
import android.content.res.AssetFileDescriptor;
import android.content.res.AssetManager;
import android.media.AudioManager;
import android.media.SoundPool;
import android.util.Log;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class BeatBox {
    private static final String TAG = "BeatBox";
    private static final String SOUNDS_FOLDER = "sample_sounds";
    private static final int MAX_SOUNDS = 5;
    private AssetManager mAssets;
    private List<Sound> mSounds = new ArrayList<>();
    private SoundPool mSoundPool;

    public BeatBox(Context context)
    {
        mAssets = context.getAssets();
        mSoundPool = new SoundPool(MAX_SOUNDS, AudioManager.STREAM_MUSIC, 0); //param1: số sound có thể chơi, param2: loại nhạc chơi, param3: ko care
        loadSounds();
    }

    public void play(Sound sound)
    {
        Integer soundId = sound.getSoundId();
        if(soundId == null)
        {
            return;
        }
        mSoundPool.play(soundId, 1.0f, 1.0f, 1,0,1.0f);
    }

    public void release()
    {
        mSoundPool.release();
    }

    private void loadSounds()
    {
        String[] soundNames;    //mảng chứa tên của các sound
        try{
            soundNames = mAssets.list(SOUNDS_FOLDER);
            Log.i(TAG, "Found " + soundNames.length + " sounds");
        }
        catch (IOException ioe)
        {
            Log.e(TAG, "Could not list assets", ioe);
            return;
        }
        for (String filename : soundNames)
        {
            try{
                String assetPath = SOUNDS_FOLDER + "/" + filename; //lọc ra âm thanh có tên filename, cộng với SOUND_FOLDER để có được đường dẫn tới sound đó
                Sound sound = new Sound(assetPath);   //lấy sound từ đường dẫn assetPath
                load(sound);
                mSounds.add(sound);      //add sound vào list
            }
            catch (IOException ioe)
            {
                Log.e(TAG, "Could not load sound "+filename, ioe);
            }
        }
    }

    private void load(Sound sound) throws IOException
    {
        AssetFileDescriptor afd = mAssets.openFd(sound.getAssetPath());
        int soundId = mSoundPool.load(afd, 1);
        sound.setSoundId(soundId);
    }

    public List<Sound> getSounds()
    {
        return mSounds;
    }
}
