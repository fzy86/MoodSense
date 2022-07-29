package com.example.moodsense.demo.service;

import com.example.moodsense.demo.model.Coordinate;
import com.example.moodsense.demo.model.Location;
import com.example.moodsense.demo.model.Mood;
import com.example.moodsense.demo.model.MoodDistribution;
import com.example.moodsense.demo.repositary.MoodRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;

@Service("moodService")
public class MoodServiceImplementation implements MoodService{
    @Autowired
    private MoodRepository moodRepository;

    @Override
    public List<MoodDistribution> getMoodDistribution(String remoteUser) {
        return moodRepository.getMoodDistribution(remoteUser);
    }

    @Override
    public List<Location> getHappyNearByLocations(String remoteUser) {
        return getLocationsFromCoordinates(moodRepository.getAllHappyCoordinates(remoteUser));
    }

    public List<Location> getLocationsFromCoordinates(List<Coordinate> allHappyCoordinates) {
        List<Location> result = new LinkedList<>();
        for (Coordinate coordinate : allHappyCoordinates){
            Location happyLocation = getBestLocationFromCoordinate(coordinate);
            result.add(happyLocation);
        }
        return result;
    }

    public Location getBestLocationFromCoordinate(Coordinate coordinate) {
        Location result = null;
        Double distance = Double.valueOf(Integer.MAX_VALUE);

        for (Location location : moodRepository.getAllLocations()) {
            Double cur_distance = Math.sqrt(
                    Math.pow(location.getLatitude() - coordinate.getLatitude(), 2) +
                            Math.pow(location.getLatitude() - coordinate.getLongitude(), 2)
            );
            if ( cur_distance < distance ){
                result = location;
                distance = cur_distance;
            }
        }

        return result;
    }

    @Override
    public void save(Mood mood) {
        moodRepository.save(mood);
    }
}
