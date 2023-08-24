package com.example.leavemngmtsystm.controllers;

import com.example.leavemngmtsystm.models.LeaveDetails;
import com.example.leavemngmtsystm.models.UserInfo;
import com.example.leavemngmtsystm.service.LeaveManageService;
import com.example.leavemngmtsystm.service.UserInfoService;
import lombok.RequiredArgsConstructor;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import javax.validation.Valid;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Iterator;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class LeaveManageController {

    private final LeaveManageService leaveManageService;

    private final UserInfoService userInfoService;

    @GetMapping(value = "/user/apply-leave")
    public ModelAndView applyLeave(ModelAndView mav) {

        mav.addObject("leaveDetails", new LeaveDetails());
        mav.setViewName("applyLeave");
        return mav;
    }

    @PostMapping(value = "/user/apply-leave")
    public ModelAndView submitApplyLeave(ModelAndView mav, @Valid LeaveDetails leaveDetails,
                                         BindingResult bindingResult) {

        UserInfo userInfo = userInfoService.getUserInfo();
        if (bindingResult.hasErrors()) {
            mav.setViewName("applyLeave");
        } else {
            leaveDetails.setUsername(userInfo.getEmail());
            leaveDetails.setEmployeeName(userInfo.getFirstName() + " " + userInfo.getLastName());
            leaveManageService.applyLeave(leaveDetails);
            mav.addObject("successMessage", "Your Leave Request is registered!");
            mav.setView(new RedirectView("/leave-management-system/user/home"));
        }
        return mav;
    }

    @GetMapping(value = "/user/get-all-leaves")
    public @ResponseBody
    String getAllLeaves(@RequestParam(value = "pending", defaultValue = "false") boolean pending,
                        @RequestParam(value = "accepted", defaultValue = "false") boolean accepted,
                        @RequestParam(value = "rejected", defaultValue = "false") boolean rejected) throws Exception {

        Iterator<LeaveDetails> iterator = leaveManageService.getAllLeaves().iterator();
        if (pending || accepted || rejected)
            iterator = leaveManageService.getAllLeavesOnStatus(pending, accepted, rejected).iterator();
        JSONArray jsonArr = new JSONArray();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Calendar calendar = Calendar.getInstance();

        while (iterator.hasNext()) {
            LeaveDetails leaveDetails = iterator.next();
            calendar.setTime(leaveDetails.getToDate());
            calendar.add(Calendar.DATE, 1);

            JSONObject jsonObj = new JSONObject();
            jsonObj.put("title", leaveDetails.getEmployeeName());
            jsonObj.put("start", dateFormat.format(leaveDetails.getFromDate()));
            jsonObj.put("end", dateFormat.format(calendar.getTime()));
            if (leaveDetails.isActive())
                jsonObj.put("color", "#0878af");
            if (!leaveDetails.isActive() && leaveDetails.isAcceptRejectFlag())
                jsonObj.put("color", "green");
            if (!leaveDetails.isActive() && !leaveDetails.isAcceptRejectFlag())
                jsonObj.put("color", "red");
            jsonArr.put(jsonObj);
        }

        return jsonArr.toString();
    }

    @GetMapping(value = "/user/manage-leaves")
    public ModelAndView manageLeaves(ModelAndView mav) {

        mav.addObject("leavesList", leaveManageService.getAllActiveLeaves());
        mav.setViewName("manageLeaves");
        return mav;
    }

    @GetMapping(value = "/user/manage-leaves/{action}/{id}")
    public ModelAndView acceptOrRejectLeaves(ModelAndView mav, @PathVariable("action") String action,
                                             @PathVariable("id") int id) {
        LeaveDetails leaveDetails = leaveManageService.getLeaveDetailsOnId(id).orElseThrow();
        if (action.equals("accept")) {
            leaveDetails.setAcceptRejectFlag(true);
            leaveDetails.setActive(false);
        } else if (action.equals("reject")) {
            leaveDetails.setAcceptRejectFlag(false);
            leaveDetails.setActive(false);
        }
        leaveManageService.updateLeaveDetails(leaveDetails);
        mav.addObject("successMessage", "Updated Successfully!");
        mav.setView(new RedirectView("/leave-management-system/user/manage-leaves"));
        return mav;
    }

    @GetMapping(value = "/user/my-leaves")
    public ModelAndView showMyLeaves(ModelAndView mav) {

        UserInfo userInfo = userInfoService.getUserInfo();
        List<LeaveDetails> leavesList = leaveManageService.getAllLeavesOfUser(userInfo.getEmail());
        mav.addObject("leavesList", leavesList);
        mav.setViewName("myLeaves");
        return mav;
    }
}
