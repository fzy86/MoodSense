package com.example.moodsense.demo.service;

import com.example.moodsense.demo.model.Location;
import com.example.moodsense.demo.model.Mood;
import com.example.moodsense.demo.model.MoodDistribution;

import java.util.List;

public interface MoodService {
    List<MoodDistribution> getMoodDistribution(String remoteUser);
    List<Location> getHappyNearByLocations(String remoteUser);
    void save(Mood mood);
}
