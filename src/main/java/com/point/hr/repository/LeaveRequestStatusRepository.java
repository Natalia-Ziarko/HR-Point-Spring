package com.point.hr.repository;

import com.point.hr.entity.LeaveRequestStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LeaveRequestStatusRepository extends JpaRepository<LeaveRequestStatus, Integer> {

}
