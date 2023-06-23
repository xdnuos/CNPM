package ptit.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ptit.entity.Permission;
import ptit.repository.PermissionDAO;
import ptit.service.PermissionService;

@Service
public class PermissionServiceImpl implements PermissionService{
	@Autowired
	private PermissionDAO permissionDAO;
	
	public PermissionServiceImpl(PermissionDAO permissionDAO) {
		this.permissionDAO = permissionDAO;
	}
	@Override
	public List<Permission> findAll() {
		
		return this.permissionDAO.findAll();
	}
	@Override
	public Permission findByName(String name) {
		return permissionDAO.findByName(name);
	}

	
	
	
}
