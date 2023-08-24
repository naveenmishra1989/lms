package com.example.leavemngmtsystm.service;

import com.example.leavemngmtsystm.models.LeaveDetails;
import com.example.leavemngmtsystm.repository.LeaveManageNativeSqlRepo;
import com.example.leavemngmtsystm.repository.LeaveManageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service(value = "leaveManageService")
@RequiredArgsConstructor
public class LeaveManageService {

    private final LeaveManageRepository leaveManageRepository;

    private final LeaveManageNativeSqlRepo leaveManageNativeRepo;

    @SuppressWarnings("deprecation")
    public void applyLeave(LeaveDetails leaveDetails) {

	int duration = leaveDetails.getToDate().getDate() - leaveDetails.getFromDate().getDate();
	leaveDetails.setDuration(duration + 1);
	leaveDetails.setActive(true);
	leaveManageRepository.save(leaveDetails);
    }

    public List<LeaveDetails> getAllLeaves() {

	return leaveManageRepository.findAll();
    }

    public Optional<LeaveDetails> getLeaveDetailsOnId(int id) {

	return leaveManageRepository.findById(id);
    }

    public void updateLeaveDetails(LeaveDetails leaveDetails) {

	leaveManageRepository.save(leaveDetails);

    }

    public List<LeaveDetails> getAllActiveLeaves() {

	return leaveManageRepository.getAllActiveLeaves();
    }

    public List<LeaveDetails> getAllLeavesOfUser(String username) {

	return leaveManageRepository.getAllLeavesOfUser(username);

    }

    public List<LeaveDetails> getAllLeavesOnStatus(boolean pending, boolean accepted, boolean rejected) {

	StringBuffer whereQuery = new StringBuffer();
	if (pending)
	    whereQuery.append("active=true or ");
	if (accepted)
	    whereQuery.append("(active=false and accept_reject_flag=true) or ");
	if (rejected)
	    whereQuery.append("(active=false and accept_reject_flag=false) or ");

	whereQuery.append(" 1=0");
	
	return leaveManageNativeRepo.getAllLeavesOnStatus(whereQuery);
    }
}
