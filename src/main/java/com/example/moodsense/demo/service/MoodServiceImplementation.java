package com.example.moodsense.demo.service;

import com.example.moodsense.demo.model.Coordinate;
import com.example.moodsense.demo.model.Location;
import com.example.moodsense.demo.model.Mood;
import com.example.moodsense.demo.model.MoodDistribution;
import com.example.moodsense.demo.model.MoodStatus;
import com.example.moodsense.demo.repositary.MoodRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service("moodService")
public class MoodServiceImplementation implements MoodService {

    private static final Logger log = LoggerFactory.getLogger(MoodServiceImplementation.class);

    @Autowired
    private MoodRepository moodRepository;

    @Override
    @Transactional(readOnly = true)
    public List<MoodDistribution> getMoodDistribution(String remoteUser) {
        log.debug("Fetching mood distribution for user '{}'", remoteUser);
        return moodRepository.getMoodDistribution(remoteUser);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Location> getHappyNearByLocations(String remoteUser) {
        log.debug("Fetching happy nearby locations for user '{}'", remoteUser);
        List<Coordinate> happyCoordinates = moodRepository.getAllHappyCoordinates(remoteUser, MoodStatus.HAPPY);
        List<Location> allLocations = moodRepository.getAllLocations();
        return getLocationsFromCoordinates(happyCoordinates, allLocations);
    }

    @Override
    @Transactional
    public void save(Mood mood) {
        log.debug("Saving mood for person '{}'", mood.getPerson());
        moodRepository.save(mood);
    }

    private List<Location> getLocationsFromCoordinates(List<Coordinate> allHappyCoordinates, List<Location> allLocations) {
        List<Location> result = new ArrayList<>();
        for (Coordinate coordinate : allHappyCoordinates) {
            Location happyLocation = getBestLocationFromCoordinate(coordinate, allLocations);
            if (happyLocation != null) {
                result.add(happyLocation);
            }
        }
        return result;
    }

    private Location getBestLocationFromCoordinate(Coordinate coordinate, List<Location> locations) {
        Location result = null;
        double distance = Double.MAX_VALUE;

        for (Location location : locations) {
            double cur_distance = Math.sqrt(
                    Math.pow(location.getLatitude() - coordinate.getLatitude(), 2) +
                    Math.pow(location.getLongitude() - coordinate.getLongitude(), 2)
            );
            if (cur_distance < distance) {
                result = location;
                distance = cur_distance;
            }
        }

        return result;
    }
}
