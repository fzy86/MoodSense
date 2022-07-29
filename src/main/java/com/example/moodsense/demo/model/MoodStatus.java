package com.example.moodsense.demo.model;

import javax.validation.constraints.NotBlank;

public enum MoodStatus {
    HAPPY("HAPPY"),
    SAD("SAD"),
    NEUTRAL("NEUTRAL");

    @NotBlank
    private String mood;

    MoodStatus(String mood){
        this.mood = mood;
    }

}
