package com.IIE.Industrial_Innovation_Engine_server.service;

import com.IIE.Industrial_Innovation_Engine_server.dto.UserDTO;
import java.util.List;

public interface UserService {
    List<UserDTO> getUsers();
    UserDTO createUser(UserDTO userDTO);
    UserDTO updateUser(Long userId, UserDTO userDTO);
    void deleteUser(Long userId);
    UserDTO updateUserStatus(Long userId, String status);
    List<UserDTO> searchUsers(String keyword);

}
