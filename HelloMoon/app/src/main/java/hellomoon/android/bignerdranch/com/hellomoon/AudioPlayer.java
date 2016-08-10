package hellomoon.android.bignerdranch.com.hellomoon;

import android.content.Context;
import android.media.MediaPlayer;

/**
 * Created by root on 10.08.16.
 */
public class AudioPlayer {

    private MediaPlayer mPlayer;
    private int playing = 0;

    public void stop(){
        if (mPlayer!= null){
            mPlayer.release();
            playing = 0;
            mPlayer = null;
        }
    }

    public void play(Context c){


        mPlayer = MediaPlayer.create(c, R.raw.one_small_step);

        mPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            public void onCompletion(MediaPlayer mp) {
                stop();
            }
        });
        if (playing == 0) {
            mPlayer.start();
        } else
            mPlayer.seekTo(playing);
            mPlayer.start();

    }

    public void pause(){
        mPlayer.pause();
        playing = mPlayer.getCurrentPosition();
    }


}
