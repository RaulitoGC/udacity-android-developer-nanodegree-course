package com.rguzman.jokesprovider;

import java.util.Random;

public class JokeProvider {

    private final static String[] JOKES = {"Why do we park our car in the driveway and drive our car on the parkway? ",
            "Why do we park our car in the driveway and drive our car on the parkway? ",
            "If vegetarians eat vegetables, what do humanitarians eat? ",
            "My boss is so unpopular even his own shadow refuses to follow him.",
            "Why are eggs not very much into jokes? Because they could crack up.",
            "I never hold my farts in. Only assholes do that."};

    public String getJoke() {
        return JOKES[new Random().nextInt(JOKES.length)];
    }
}
