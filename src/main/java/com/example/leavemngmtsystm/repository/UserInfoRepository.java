package com.example.leavemngmtsystm.repository;

import com.example.leavemngmtsystm.models.UserInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.util.List;

@Repository(value = "userInfoRepository")
public interface UserInfoRepository extends JpaRepository<UserInfo, Serializable> {

    UserInfo findByEmail(String email);

    List<UserInfo> findAllByOrderById();

    UserInfo findById(int id);

    @Transactional
    @Modifying
    @Query(value = "update userinfo set active=false where id=?", nativeQuery = true)
    void blockUser(int id);

    @Transactional
    @Modifying
    @Query(value = "update userinfo set active=true where id=?", nativeQuery = true)
    void unBlockUser(int id);
}
