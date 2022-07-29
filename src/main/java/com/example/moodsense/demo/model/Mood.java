package com.example.moodsense.demo.model;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Data
@Entity
public class Mood {
    @Id
    @GeneratedValue
    private int id;

    private String person;

    @NotNull(message = "You must enter latitude for Demo purpose")
    @Min(value = -10, message = "Your latitude must be greater than -10")
    @Max(value = 10, message = "Your latitude must be smaller than 10")
    private int latitude;

    @NotNull(message = "You must enter longitude for Demo purpose")
    @Min(value = -10, message = "Your longitude must be greater than -10")
    @Max(value = 10, message = "Your longitude must be smaller than 10")
    private int longitude;

    @Enumerated(EnumType.STRING)
    private MoodStatus moodStatus;
}
