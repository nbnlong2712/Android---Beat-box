package com.longtraidep.beatbox;

public class Sound {
    private String mAssetPath;
    private String mName;
    private Integer mSoundId;

    public Sound(String assetPath)
    {
        mAssetPath = assetPath;
        String[] components = assetPath.split("/");    //tách tên từng sound trong sample_sound ra
        String filename = components[components.length - 1];   //lấy tên từng sound
        mName = filename.replace(".wav", "");   //xóa đuôi ".wav" để hiển thị cho người dùng xem
    }

    public String getAssetPath()
    {
        return mAssetPath;
    }

    public String getName()
    {
        return mName;
    }

    public Integer getSoundId()
    {
        return mSoundId;
    }

    public void setSoundId(Integer soundId)
    {
        mSoundId = soundId;
    }

}
