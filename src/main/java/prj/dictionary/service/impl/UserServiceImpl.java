package prj.dictionary.service.impl;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import prj.dictionary.dto.ChangePasswordDTO;
import prj.dictionary.dto.SignUpUserDTO;
import prj.dictionary.dto.UpdateUserDTO;
import prj.dictionary.entity.Role;
import prj.dictionary.entity.User;
import prj.dictionary.repository.UserRepository;
import prj.dictionary.service.RoleService;
import prj.dictionary.service.StorageService;
import prj.dictionary.service.UserService;

import java.util.Set;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    UserRepository userRepository;

    @Autowired
    RoleService roleService;

    @Autowired
    StorageService storageService;

    @Autowired
    ModelMapper mapper;

    @Autowired
    PasswordEncoder encoder;

    @Override
    public void createUser(SignUpUserDTO signUpUserDTO) {
        User newUser = mapper.map(signUpUserDTO, User.class);
        newUser.setPassword(encoder.encode(signUpUserDTO.getPassword()));
        newUser.getRoles().add(roleService.getUserRole());
        userRepository.save(newUser);
    }

    @Override
    public void createAdmin(SignUpUserDTO signUpUserDTO) {
        User newAdmin = mapper.map(signUpUserDTO, User.class);
        newAdmin.setPassword(encoder.encode(signUpUserDTO.getPassword()));
        newAdmin.getRoles().addAll(Set.of(roleService.getUserRole(), roleService.getAdminRole()));
        userRepository.save(newAdmin);
    }

    @Override
    public void update(UpdateUserDTO updateUserDTO, User currentUser) {
        User updated = mapper.map(updateUserDTO, User.class);
        mapper.map(updated, currentUser);
        userRepository.save(currentUser);
    }

    @Override
    public void changePassword(ChangePasswordDTO changePasswordDTO, User user) {
        String newPassword = changePasswordDTO.getNewPassword();
        user.setPassword(encoder.encode(newPassword));
        userRepository.save(user);
    }

    @Override
    public void resetPassword(String randomPassword, User user) {
        user.setPassword(encoder.encode(randomPassword));
        userRepository.save(user);
    }

    @Override
    public void changeImage(String fileName, User user) {
        String oldImgUrl = user.getImageUrl();
        user.setImageUrl(fileName);
        userRepository.save(user);
        if (oldImgUrl != null && !oldImgUrl.isEmpty()) {
            storageService.delete(oldImgUrl);
        }
    }

    @Override
    public void switchStatus(User user) {
        if (user != null) {
            Boolean status = user.getIsActive();
            user.setIsActive(status ? Boolean.FALSE : Boolean.TRUE);
            userRepository.save(user);
        }
    }

    public void switchRole(User user) {
        if (user != null) {
            Set<Role> roles = user.getRoles();
            if (roles.contains(roleService.getAdminRole())) {
                roles.remove(roleService.getAdminRole());
            } else {
                roles.add(roleService.getAdminRole());
            }
            userRepository.save(user);
        }
    }

    @Override
    public User findUserOnly(String search) {
        return userRepository.findOnlyUserByUsernameOrEmail(search);
    }

    @Override
    public User findUserAndAdmin(String search) {
        return userRepository.findUserAndAdminByUsernameOrEmail(search);
    }

    @Override
    public User findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public boolean existsByUsername(String username) {
        return userRepository.existsByUsername(username);
    }

    @Override
    public boolean existsByEmail(String email) {
        return userRepository.existsByEmail(email);
    }

    @Override
    public User findById(Integer userId) {
        return userRepository.findById(userId).orElse(null);
    }
}
