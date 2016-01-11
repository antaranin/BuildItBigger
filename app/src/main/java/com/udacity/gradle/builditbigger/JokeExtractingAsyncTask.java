package com.udacity.gradle.builditbigger;

import android.os.AsyncTask;

import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.extensions.android.json.AndroidJsonFactory;
import com.google.api.client.googleapis.services.AbstractGoogleClientRequest;
import com.google.api.client.googleapis.services.GoogleClientRequestInitializer;
import com.udacity.jokeshower.myApi.MyApi;

import java.io.IOException;

/**
 * Created by Arin on 10/01/16.
 */
public class JokeExtractingAsyncTask extends AsyncTask<Void, Void, String>
{
    private MyApi myApiService = null;

    @Override
    protected final String doInBackground(Void... params)
    {
        if (myApiService == null)
        {  // Only do this once
            MyApi.Builder builder = new MyApi.Builder(AndroidHttp.newCompatibleTransport(),
                    new AndroidJsonFactory(), null)
                    // options for running against local devappserver
                    // - 10.0.2.2 is localhost's IP address in Android emulator
                    // - turn off compression when running against local devappserver
                    .setRootUrl("http://10.0.2.2:8080/_ah/api/")
                    .setGoogleClientRequestInitializer(new GoogleClientRequestInitializer()
                    {
                        @Override
                        public void initialize(AbstractGoogleClientRequest<?> abstractGoogleClientRequest) throws IOException
                        {
                            abstractGoogleClientRequest.setDisableGZipContent(true);
                        }
                    });
            // end options for devappserver

            myApiService = builder.build();
        }

        try
        {
            return myApiService.getAJoke().execute().getJoke();
        }
        catch (IOException e)
        {
            return e.getMessage();
        }
    }
}

