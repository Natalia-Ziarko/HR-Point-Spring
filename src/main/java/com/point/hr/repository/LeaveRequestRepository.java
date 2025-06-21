package com.point.hr.repository;

import com.point.hr.entity.LeaveRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LeaveRequestRepository extends JpaRepository<LeaveRequest, Integer> {

    List<LeaveRequest> findByPersonId(Integer personId);
}
