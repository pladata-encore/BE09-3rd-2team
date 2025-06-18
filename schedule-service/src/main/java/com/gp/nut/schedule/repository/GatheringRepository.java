package com.gp.nut.schedule.repository;

import com.gp.nut.schedule.entity.Gathering;
import java.time.LocalDate;
import java.util.Collection;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GatheringRepository extends JpaRepository<Gathering, Long> {

  List<Gathering> findGatheringByDate(LocalDate date);

  List<Gathering> findGatheringByBossId(Long bossId);
}
