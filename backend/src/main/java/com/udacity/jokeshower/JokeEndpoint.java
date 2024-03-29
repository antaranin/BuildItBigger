/*
   For step-by-step instructions on connecting your Android application to this backend module,
   see "App Engine Java Endpoints Module" template documentation at
   https://github.com/GoogleCloudPlatform/gradle-appengine-templates/tree/master/HelloEndpoints
*/

package com.udacity.jokeshower;

import com.example.JokeProvider;
import com.google.api.server.spi.config.Api;
import com.google.api.server.spi.config.ApiMethod;
import com.google.api.server.spi.config.ApiNamespace;

/**
 * An endpoint class we are exposing
 */
@Api(
        name = "myApi",
        version = "v1",
        namespace = @ApiNamespace(
                ownerDomain = "jokeshower.udacity.com",
                ownerName = "jokeshower.udacity.com",
                packagePath = ""
        )
)
public class JokeEndpoint
{

    /**
     * A simple endpoint method that takes a name and says Hi back
     */
    @ApiMethod(name = "getAJoke")
    public JokeBean getAJoke()
    {
        JokeBean response = new JokeBean();
        response.setJoke(JokeProvider.getAJoke());



        return response;
    }

}
