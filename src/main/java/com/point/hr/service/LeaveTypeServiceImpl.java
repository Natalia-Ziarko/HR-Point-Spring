package com.point.hr.service;

import com.point.hr.entity.LeaveType;
import com.point.hr.repository.LeaveTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class LeaveTypeServiceImpl implements LeaveTypeService {

    private final LeaveTypeRepository leaveTypeRepository;

    @Autowired
    public LeaveTypeServiceImpl(LeaveTypeRepository leaveTypeRepository) {
        this.leaveTypeRepository = leaveTypeRepository;
    }

    @Override
    public Optional<LeaveType> findByShortName(String theShortName) {
        return leaveTypeRepository.findByShortName(theShortName);
    }

    @Override
    public Optional<LeaveType> findByLongName(String theLongName) {
        return leaveTypeRepository.findByLongName(theLongName);
    }

    @Override
    public Optional<LeaveType> findById(Integer theId) {
        return leaveTypeRepository.findById(theId);
    }

    @Override
    public List<LeaveType> findAll() {
        return leaveTypeRepository.findAll();
    }
}
