package hellomoon.android.bignerdranch.com.hellomoon;


import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.MediaController;
import android.widget.VideoView;

/**
 * Created by root on 10.08.16.
 */
public class HelloMoonFragment extends Fragment {

    private boolean play;

    private Button mPlayButton;
    private Button mStopButton;

    private AudioPlayer mPlayer = new AudioPlayer();

    private VideoView videoPlayer;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent,
                             Bundle savedIstanceState){
        View v = inflater.inflate(R.layout.fragment_hello_moon, parent, false);

        videoPlayer =  (VideoView)v.findViewById(R.id.videoPlayer);
        videoPlayer.setMediaController(new MediaController(this.getActivity()));
        Uri myVideoUri= Uri.parse("android.resource://"  + "hellomoon.android.bignerdranch.com.hellomoon/raw/apollo_17_stroll");
        videoPlayer.requestFocus();
        videoPlayer.setVideoURI(myVideoUri);
        videoPlayer.start();

        mPlayButton = (Button)v.findViewById(R.id.hellomoon_playButton);
        mPlayButton.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                if (play) {
                    mPlayer.pause();
                    play = false;
                    } else {
                    mPlayer.play(getActivity());
                    play = true;
                }
            }
        });

        mStopButton = (Button)v.findViewById(R.id.hellomoon_stopButton);
        mStopButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                mPlayer.stop();
                play = false;

            }
        });
        return v;
    }
    



    @Override
    public void onDestroy(){
        super.onDestroy();
        mPlayer.stop();
    }



}
