package com.example;

import java.util.Random;

public class JokeProvider
{
    private static String[] jokes = {"Joke1", "Joke2", "Joke3"};
    private static Random r = new Random();
    public static String getAJoke()
    {
        return jokes[r.nextInt(jokes.length)];
    }

    public static String[] getAllJokes()
    {
        return jokes;
    }
}
