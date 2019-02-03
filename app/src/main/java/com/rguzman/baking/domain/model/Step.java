package com.rguzman.baking.domain.model;

import com.google.gson.annotations.SerializedName;

public class Step {
    private int id;
    private String shortDescription;
    private String description;
    @SerializedName("videoURL")
    private String videoUrl;
    @SerializedName("thumbnailURL")
    private String thumbnailUrl;
}
