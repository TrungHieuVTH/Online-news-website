package com.messi.king.messinews.model.dao;

import com.messi.king.messinews.model.bean.Users;

import java.time.LocalDateTime;
import java.util.List;

public interface UsersDAO {
    public int add(Users user);
    public Users findById(int id);
    public void delete(Users user);
    public List<Users> findAll();
    public void extendSubscriber(int id, LocalDateTime expireDate);
    public void assignCategories(int editor_id, int[] catesId );
    public void deleteEditorCategories(int editor_id );
    public void updateOTP(int id, String otp);
    public Users findByUsername(String username);
    public List<Users> findAllByRole(int role);
    public Users findByEmail(String email);
    public void updateProfile(int id, String fullName, int role, String email, LocalDateTime dob);
    public void changePassword(int id, String password);
}

