package com.example.moodsense.demo.controller;

import com.example.moodsense.demo.model.Location;
import com.example.moodsense.demo.model.Mood;
import com.example.moodsense.demo.model.MoodDistribution;
import com.example.moodsense.demo.model.MoodStatus;
import com.example.moodsense.demo.service.MoodService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@Validated
public class MoodSenseController {

    private static final Logger log = LoggerFactory.getLogger(MoodSenseController.class);

    @Autowired
    private MoodService moodService;

    @RequestMapping(value = "/uploadMood", method = RequestMethod.POST)
    public ResponseEntity<Void> uploadMood(HttpServletRequest request,
                                           @RequestParam MoodStatus moodStatus,
                                           @RequestParam Integer latitude,
                                           @RequestParam Integer longitude) {
        String user = request.getRemoteUser();
        log.info("Uploading mood '{}' for user '{}' at ({}, {})", moodStatus, user, latitude, longitude);
        Mood mood = new Mood();
        mood.setPerson(user);
        mood.setLatitude(latitude);
        mood.setLongitude(longitude);
        mood.setMoodStatus(moodStatus);
        moodService.save(mood);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @RequestMapping(value = "/getMoodDistribution", method = RequestMethod.GET)
    public ResponseEntity<List<MoodDistribution>> getMoodDistribution(HttpServletRequest request) {
        String user = request.getRemoteUser();
        log.debug("Fetching mood distribution for user '{}'", user);
        return ResponseEntity.ok(moodService.getMoodDistribution(user));
    }

    @RequestMapping(value = "/getHappyNearByLocations", method = RequestMethod.GET)
    public ResponseEntity<List<Location>> getHappyNearByLocations(HttpServletRequest request) {
        String user = request.getRemoteUser();
        log.debug("Fetching happy nearby locations for user '{}'", user);
        return ResponseEntity.ok(moodService.getHappyNearByLocations(user));
    }
}
