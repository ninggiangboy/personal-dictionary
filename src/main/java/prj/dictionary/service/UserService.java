package prj.dictionary.service;

import prj.dictionary.dto.ChangePasswordDTO;
import prj.dictionary.dto.SignUpUserDTO;
import prj.dictionary.dto.UpdateUserDTO;
import prj.dictionary.entity.User;

public interface UserService {
    void createUser(SignUpUserDTO signUpUserDTO);

    void createAdmin(SignUpUserDTO signUpUserDTO);

    void update(UpdateUserDTO updateUserDTO, User currentUser);

    void changePassword(ChangePasswordDTO changePasswordDTO, User user);

    void resetPassword(String randomPassword, User user);

    void changeImage(String fileName, User user);


    void switchStatus(User user);

    void switchRole(User user);

    User findUserOnly(String search);

    User findUserAndAdmin(String search);

    User findByEmail(String email);

    boolean existsByUsername(String username);

    boolean existsByEmail(String email);

    User findById(Integer userId);
}
