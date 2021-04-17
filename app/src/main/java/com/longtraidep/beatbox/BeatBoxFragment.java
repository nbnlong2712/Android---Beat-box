package com.longtraidep.beatbox;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.longtraidep.beatbox.databinding.FragmentBeatBoxBinding;
import com.longtraidep.beatbox.databinding.ListItemSoundBinding;

import java.util.List;

public class BeatBoxFragment extends Fragment {
    private BeatBox mBeatBox;

    public static BeatBoxFragment newInstance()
    {
        return new BeatBoxFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);    //để khi xoay màn hình, sound ko bị destroy mà vẫn tiếp tục
        mBeatBox = new BeatBox(getActivity());
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        FragmentBeatBoxBinding binding = DataBindingUtil.inflate(inflater, R.layout.fragment_beat_box, container, false);

        binding.recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 3));

        binding.recyclerView.setAdapter(new SoundAdapter(mBeatBox.getSounds()));

        return binding.getRoot();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mBeatBox.release();
    }

    /////////////////////////////////////////-----   Class ViewHolder   -------///////////////////////////////////////////////
    private class SoundHolder extends RecyclerView.ViewHolder{
        private ListItemSoundBinding mBinding;
        private SoundHolder(ListItemSoundBinding binding)
        {
            super(binding.getRoot());
            mBinding = binding;
            mBinding.setViewModel(new SoundViewModel(mBeatBox));
        }
        public void bind(Sound sound)
        {
            mBinding.getViewModel().setSound(sound);
            mBinding.executePendingBindings();
        }
    }

    /////////////////////////////////////////-----  Class ViewAdapter   --------///////////////////////////////////////////////
    private class SoundAdapter extends RecyclerView.Adapter<SoundHolder>
    {
        private List<Sound> mSounds;

        public SoundAdapter(List<Sound> sounds)
        {
            mSounds = sounds;
        }

        @NonNull
        @Override
        public SoundHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            LayoutInflater inflater = LayoutInflater.from(getActivity());
            ListItemSoundBinding binding = DataBindingUtil.inflate(inflater, R.layout.list_item_sound, parent, false);
            return new SoundHolder(binding);
        }

        @Override
        public void onBindViewHolder(@NonNull SoundHolder holder, int position) {
            Sound sound = mSounds.get(position);
            holder.bind(sound);
        }

        @Override
        public int getItemCount() {
            return mSounds.size();
        }
    }
}
