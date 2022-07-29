package com.example.moodsense.demo.controller;

import com.example.moodsense.demo.model.Location;
import com.example.moodsense.demo.model.Mood;
import com.example.moodsense.demo.model.MoodDistribution;
import com.example.moodsense.demo.model.MoodStatus;
import com.example.moodsense.demo.service.MoodService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@RestController
public class MoodSenseController {
    @Autowired
    private MoodService moodService;

    @RequestMapping(value = "/uploadMood", method = RequestMethod.POST)
    public void uploadMood (HttpServletRequest request,
                            Mood mood,
                            @Validated @RequestParam MoodStatus moodStatus,
                            @Validated @RequestParam Integer latitude,
                            @Validated @RequestParam Integer longitude,
                            HttpServletResponse response) {
        mood.setPerson(request.getRemoteUser());
        mood.setLatitude(latitude);
        mood.setLongitude(longitude);
        mood.setMoodStatus(moodStatus);
        moodService.save(mood);
    }

    @RequestMapping(value = "/getMoodDistribution", method = RequestMethod.GET)
    public ResponseEntity<List<MoodDistribution>> getMoodDistribution(HttpServletRequest request){
        return new ResponseEntity<>(moodService.getMoodDistribution(request.getRemoteUser()),HttpStatus.OK);
    }

    @RequestMapping(value = "/getHappyNearByLocations", method = RequestMethod.GET)
    public ResponseEntity<List<Location>> getHappyNearByLocations(HttpServletRequest request){
        return new ResponseEntity<>(moodService.getHappyNearByLocations(request.getRemoteUser()), HttpStatus.OK);
    }
}
