package prj.dictionary.service;

import prj.dictionary.entity.Role;

public interface RoleService {
    Role getAdminRole();

    Role getUserRole();
}
