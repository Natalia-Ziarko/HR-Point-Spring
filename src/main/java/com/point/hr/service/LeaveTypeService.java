package com.point.hr.service;

import com.point.hr.entity.LeaveType;

import java.util.List;
import java.util.Optional;

public interface LeaveTypeService {
    Optional<LeaveType> findByShortName(String shortName);

    Optional<LeaveType> findByLongName(String longName);

    Optional<LeaveType> findById(Integer id);

    List<LeaveType> findAll();

}
