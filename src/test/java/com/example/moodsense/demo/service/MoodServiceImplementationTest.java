package com.example.moodsense.demo.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.example.moodsense.demo.model.Coordinate;
import com.example.moodsense.demo.model.Location;
import com.example.moodsense.demo.model.Mood;
import com.example.moodsense.demo.model.MoodDistribution;
import com.example.moodsense.demo.model.MoodStatus;
import com.example.moodsense.demo.repositary.MoodRepository;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ContextConfiguration(classes = {MoodServiceImplementation.class})
@ExtendWith(SpringExtension.class)
class MoodServiceImplementationTest {
    @MockBean
    private MoodRepository moodRepository;

    @Autowired
    private MoodServiceImplementation moodServiceImplementation;


    /**
     * Method under test: {@link MoodServiceImplementation#getMoodDistribution(String)}
     */
    @Test
    void testGetMoodDistribution() {
        ArrayList<MoodDistribution> moodDistributionList = new ArrayList<>();
        when(this.moodRepository.getMoodDistribution((String) any())).thenReturn(moodDistributionList);
        List<MoodDistribution> actualMoodDistribution = this.moodServiceImplementation.getMoodDistribution("Remote User");
        assertSame(moodDistributionList, actualMoodDistribution);
        assertTrue(actualMoodDistribution.isEmpty());
        verify(this.moodRepository).getMoodDistribution((String) any());
    }

    /**
     * Method under test: {@link MoodServiceImplementation#getHappyNearByLocations(String)}
     */
    @Test
    void testGetHappyNearByLocations() {
        when(this.moodRepository.getAllHappyCoordinates((String) any())).thenReturn(new ArrayList<>());
        assertTrue(this.moodServiceImplementation.getHappyNearByLocations("Remote User").isEmpty());
        verify(this.moodRepository).getAllHappyCoordinates((String) any());
    }

    /**
     * Method under test: {@link MoodServiceImplementation#getBestLocationFromCoordinate(Coordinate)}
     */
    @Test
    void testGetBestLocationFromCoordinate() {
        Location home = new Location();
        home.setLatitude(1);
        home.setLongitude(1);
        home.setName("home");

        Location office = new Location();
        office.setLatitude(3);
        office.setLongitude(4);
        office.setName("office");

        Location mall = new Location();
        mall.setLatitude(10);
        mall.setLongitude(10);
        mall.setName("mall");

        ArrayList<Location> locationList = new ArrayList<>();
        locationList.add(home);
        locationList.add(office);
        locationList.add(mall);

        when(this.moodRepository.getAllLocations()).thenReturn(locationList);
        Coordinate coordinate = mock(Coordinate.class);
        when(coordinate.getLatitude()).thenReturn(1);
        when(coordinate.getLongitude()).thenReturn(1);
        assertSame(home, this.moodServiceImplementation.getBestLocationFromCoordinate(coordinate));
    }

    /**
     * Method under test: {@link MoodServiceImplementation#save(Mood)}
     */
    @Test
    void testSave() {
        Mood mood = new Mood();
        mood.setId(1);
        mood.setLatitude(1);
        mood.setLongitude(1);
        mood.setMoodStatus(MoodStatus.HAPPY);
        mood.setPerson("Person");
        when(this.moodRepository.save((Mood) any())).thenReturn(mood);

        Mood mood1 = new Mood();
        mood1.setId(1);
        mood1.setLatitude(1);
        mood1.setLongitude(1);
        mood1.setMoodStatus(MoodStatus.HAPPY);
        mood1.setPerson("Person");
        this.moodServiceImplementation.save(mood1);
        verify(this.moodRepository).save((Mood) any());
        assertEquals(1, mood1.getId());
        assertEquals("Person", mood1.getPerson());
        assertEquals(MoodStatus.HAPPY, mood1.getMoodStatus());
        assertEquals(1, mood1.getLongitude());
        assertEquals(1, mood1.getLatitude());
    }

}