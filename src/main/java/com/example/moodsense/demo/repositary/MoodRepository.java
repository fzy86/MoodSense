package com.example.moodsense.demo.repositary;

import com.example.moodsense.demo.model.Coordinate;
import com.example.moodsense.demo.model.Location;
import com.example.moodsense.demo.model.Mood;
import com.example.moodsense.demo.model.MoodDistribution;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MoodRepository extends CrudRepository<Mood, String> {

    //@Query(value = "select MoodStatus, count(MoodStatus) from Mood m where PERSON = :remoteUser group by MoodStatus")
    @Query(value = "select m.moodStatus as moodStatus, count(m.moodStatus) as count from Mood m where person = :remoteUser group by m.moodStatus")
    List<MoodDistribution> getMoodDistribution(@Param("remoteUser") String remoteUser);

    @Query(value = "select distinct m.latitude as latitude, m.longitude as longitude from Mood m where person = :remoteUser and moodStatus = 'HAPPY'")
    List<Coordinate> getAllHappyCoordinates(@Param("remoteUser") String remoteUser);

    @Query(value = "select new Location(name, latitude, longitude) from Location")
    List<Location> getAllLocations();
}
