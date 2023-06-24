package ptit.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.data.domain.Page;
//import org.springframework.data.domain.PageRequest;
//import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import ptit.repository.AccountDAO;
import ptit.repository.StaffDAO;
import ptit.service.StaffService;
import ptit.entity.Account;
import ptit.entity.Staff;
@Service
public class StaffServiceImpl implements StaffService{
	@Autowired
	private StaffDAO staffDAO;
	
	@Autowired
	private AccountDAO accountDAO;
	
	@Override
	public List<Account> findAllAccount(){
		return (List<Account>)accountDAO.findAll();
	}
    @Override
    public List<Staff> findAll() {
        return (List<Staff>)staffDAO.findAll();
    }

    @Override
    public Staff save(Staff Staff) {
        return staffDAO.save(Staff);
    }
    
	@Override
	public void deleteById(Long staffID) {
		this.staffDAO.deleteById(staffID);	
	}


	public Optional<Staff> findById(Long staffID) {
		return staffDAO.findById(staffID);
	}

	@Override
	public List<Staff> findAll(Sort sort) {
		return (List<Staff>)staffDAO.findAll(sort);
	}
//	@Override
//	public Page<Staff> findAll(Pageable pageable) {
//		return (Page<Staff>)staffDAO.findAll(pageable);
//	}
	@Override
	public List<Staff> findStaff(String searchvalue) {
		return staffDAO.findStaffs(searchvalue);
	}
	

//	public List<Staff> findByNameLikeOrderByName(String fullname) {
//		return staffDAO.findByNameLikeOrderByName("%" + fullname +"%");
//	}

}

