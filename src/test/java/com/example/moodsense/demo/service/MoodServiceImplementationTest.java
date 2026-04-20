package com.example.moodsense.demo.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
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

    @Test
    void testGetMoodDistribution() {
        ArrayList<MoodDistribution> moodDistributionList = new ArrayList<>();
        when(moodRepository.getMoodDistribution(any())).thenReturn(moodDistributionList);
        List<MoodDistribution> actual = moodServiceImplementation.getMoodDistribution("Remote User");
        assertSame(moodDistributionList, actual);
        assertTrue(actual.isEmpty());
        verify(moodRepository).getMoodDistribution(any());
    }

    @Test
    void testGetHappyNearByLocations_empty() {
        when(moodRepository.getAllHappyCoordinates(any(), eq(MoodStatus.HAPPY))).thenReturn(new ArrayList<>());
        when(moodRepository.getAllLocations()).thenReturn(new ArrayList<>());
        assertTrue(moodServiceImplementation.getHappyNearByLocations("Remote User").isEmpty());
        verify(moodRepository).getAllHappyCoordinates(any(), eq(MoodStatus.HAPPY));
    }

    @Test
    void testGetHappyNearByLocations_findsNearestLocation() {
        Location home = new Location();
        home.setLatitude(1);
        home.setLongitude(1);
        home.setName("home");

        Location office = new Location();
        office.setLatitude(3);
        office.setLongitude(4);
        office.setName("office");

        ArrayList<Location> locationList = new ArrayList<>();
        locationList.add(home);
        locationList.add(office);

        Coordinate coordinate = mock(Coordinate.class);
        when(coordinate.getLatitude()).thenReturn(1);
        when(coordinate.getLongitude()).thenReturn(1);

        ArrayList<Coordinate> coordinates = new ArrayList<>();
        coordinates.add(coordinate);

        when(moodRepository.getAllHappyCoordinates(any(), eq(MoodStatus.HAPPY))).thenReturn(coordinates);
        when(moodRepository.getAllLocations()).thenReturn(locationList);

        List<Location> result = moodServiceImplementation.getHappyNearByLocations("Remote User");
        assertEquals(1, result.size());
        assertEquals("home", result.get(0).getName());
    }

    @Test
    void testGetHappyNearByLocations_noLocations_returnsEmptyList() {
        Coordinate coordinate = mock(Coordinate.class);
        when(coordinate.getLatitude()).thenReturn(1);
        when(coordinate.getLongitude()).thenReturn(1);

        ArrayList<Coordinate> coordinates = new ArrayList<>();
        coordinates.add(coordinate);

        when(moodRepository.getAllHappyCoordinates(any(), eq(MoodStatus.HAPPY))).thenReturn(coordinates);
        when(moodRepository.getAllLocations()).thenReturn(new ArrayList<>());

        List<Location> result = moodServiceImplementation.getHappyNearByLocations("Remote User");
        assertTrue(result.isEmpty());
    }

    @Test
    void testSave() {
        Mood mood = new Mood();
        mood.setId(1);
        mood.setLatitude(1);
        mood.setLongitude(1);
        mood.setMoodStatus(MoodStatus.HAPPY);
        mood.setPerson("Person");
        when(moodRepository.save(any(Mood.class))).thenReturn(mood);

        Mood mood1 = new Mood();
        mood1.setId(1);
        mood1.setLatitude(1);
        mood1.setLongitude(1);
        mood1.setMoodStatus(MoodStatus.HAPPY);
        mood1.setPerson("Person");
        moodServiceImplementation.save(mood1);
        verify(moodRepository).save(any(Mood.class));
        assertEquals(1, mood1.getId());
        assertEquals("Person", mood1.getPerson());
        assertEquals(MoodStatus.HAPPY, mood1.getMoodStatus());
        assertEquals(1, mood1.getLongitude());
        assertEquals(1, mood1.getLatitude());
    }
}
