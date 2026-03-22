package com.point.hr.repository;

import com.point.hr.entity.LeaveType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface LeaveTypeRepository extends JpaRepository<LeaveType, Integer> {

    Optional<LeaveType> findByShortName(String shortName);

    Optional<LeaveType> findByLongName(String longName);
}
