package ptit.service;

import java.util.List;

import ptit.entity.Permission;

public interface PermissionService {
	Permission findByName(String name);
	
	List<Permission> findAll();
	
}
