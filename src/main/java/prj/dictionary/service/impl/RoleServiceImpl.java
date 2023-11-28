package prj.dictionary.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import prj.dictionary.constant.IConstant;
import prj.dictionary.entity.Role;
import prj.dictionary.repository.RoleRepository;
import prj.dictionary.service.RoleService;

@Service
public class RoleServiceImpl implements RoleService {
    @Autowired
    RoleRepository roleRepository;

    @Override
    public Role getAdminRole() {
        return roleRepository.findByName(IConstant.ADMIN_ROLE_NAME);
    }

    @Override
    public Role getUserRole() {
        return roleRepository.findByName(IConstant.USER_ROLE_NAME);
    }
}
