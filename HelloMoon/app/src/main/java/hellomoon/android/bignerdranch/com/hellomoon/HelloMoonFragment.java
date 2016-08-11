package hellomoon.android.bignerdranch.com.hellomoon;



import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.MediaController;
import android.widget.VideoView;

/**
 * Created by root on 10.08.16.
 */
public class HelloMoonFragment extends Fragment {


    private Button mPlayButton;
    private Button mPauseButton;
    private Button mStopButton;
    private Button mVideoButton;

    private AudioPlayer mPlayer = new AudioPlayer();

    private VideoView videoPlayer;

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent,
                             Bundle savedIstanceState){
        View v = inflater.inflate(R.layout.fragment_hello_moon, parent, false);

        mVideoButton = (Button)v.findViewById(R.id.hellomoon_videoButton);
        videoPlayer = (VideoView) v.findViewById(R.id.videoPlayer);
        videoPlayer.setMediaController(new MediaController(this.getActivity()));
        Uri myVideoUri = Uri.parse("android.resource://" + "hellomoon.android.bignerdranch.com.hellomoon/raw/apollo_17_stroll");
        videoPlayer.requestFocus();
        videoPlayer.setVideoURI(myVideoUri);
        mVideoButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                videoPlayer.start();

            }
        });



        mPlayButton = (Button)v.findViewById(R.id.hellomoon_playButton);
        mPlayButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                    mPlayer.play(getActivity());
                    mPlayButton.setVisibility(View.INVISIBLE);
                    mPauseButton.setVisibility(View.VISIBLE);


            }
        });

        mPauseButton = (Button)v.findViewById(R.id.hellomoon_pauseButton);
        mPauseButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                mPlayer.pause();
                mPauseButton.setVisibility(View.INVISIBLE);
                mPlayButton.setVisibility(View.VISIBLE);
            }
        });




            mStopButton = (Button)v.findViewById(R.id.hellomoon_stopButton);
        mStopButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                mPlayer.stop();
                mPauseButton.setVisibility(View.INVISIBLE);
                mPlayButton.setVisibility(View.VISIBLE);
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
