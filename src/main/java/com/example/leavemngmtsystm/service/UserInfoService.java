package com.example.leavemngmtsystm.service;


import com.example.leavemngmtsystm.models.UserInfo;
import com.example.leavemngmtsystm.repository.UserInfoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service(value = "userInfoService")
@RequiredArgsConstructor
public class UserInfoService {

    private final UserInfoRepository userInfoRepository;

    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public UserInfo getUserInfo(){

	 return this.findUserByEmail(SecurityContextHolder.getContext().getAuthentication().getName());
	
    }
    
    public UserInfo findUserByEmail(String email) {
	return userInfoRepository.findByEmail(email);
    }

    public void saveUser(UserInfo userInfo) {
	userInfo.setPassword(bCryptPasswordEncoder.encode(userInfo.getPassword()));
	userInfo.setActive(false);
	userInfoRepository.save(userInfo);

    }

    public List<UserInfo> getUsers() {

	return userInfoRepository.findAllByOrderById();
    }

    public UserInfo getUserById(int id) {

	return userInfoRepository.findById(id);
    }

    public void deleteUser(int id) {
	userInfoRepository.deleteById(id);
    }

    public void blockUser(int id) {

	userInfoRepository.blockUser(id);

    }

    public void unBlockUser(int id) {

	userInfoRepository.unBlockUser(id);
    }

}
