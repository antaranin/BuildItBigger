package com.udacity.gradle.builditbigger;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ProgressBar;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;

import butterknife.Bind;
import butterknife.ButterKnife;
import udacity.jokeshower.JokeActivity;


public class MainActivity extends FragmentActivity
{
    private InterstitialAd advertisment;
    @Bind(R.id.waiting_view)
    ProgressBar waiting_view;

    @Bind(R.id.fragment_holder)
    FrameLayout fragment_holder;

    private String loadedJoke;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        advertisment = new InterstitialAd(this);
        advertisment.setAdUnitId("ca-app-pub-3940256099942544/1033173712");
        advertisment.setAdListener(new AdListener()
        {
            @Override
            public void onAdClosed()
            {
                super.onAdClosed();
                requestNewAdd();
                followOnWithAJoke(loadedJoke);
            }
        });
        requestNewAdd();
    }

    public void tellJoke(View view)
    {
        new JokeAsyncTask().execute();
    }

    private void requestNewAdd()
    {
        AdRequest request = new AdRequest.Builder()
                .addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
                .build();
        advertisment.loadAd(request);
    }

    private void onJokeLoaded(String joke)
    {
        loadedJoke = joke;
        if(advertisment.isLoaded())
            advertisment.show();
        else
            followOnWithAJoke(joke);
    }

    private void followOnWithAJoke(String joke)
    {
        String jokeExtra = JokeActivity.JOKE_EXTRA;
        startActivity(new Intent(this, JokeActivity.class).putExtra(jokeExtra, joke));
    }

    private void setDisplayWaiting(boolean display)
    {
        waiting_view.setVisibility(display ? View.VISIBLE : View.INVISIBLE);
        fragment_holder.setVisibility(display ? View.INVISIBLE : View.VISIBLE);
    }

    private class JokeAsyncTask extends JokeExtractingAsyncTask
    {
        @Override
        protected void onPreExecute()
        {
            super.onPreExecute();
            setDisplayWaiting(true);
        }

        @Override
        protected void onPostExecute(String joke)
        {
            super.onPostExecute(joke);
            setDisplayWaiting(false);
            onJokeLoaded(joke);
        }
    }

}
