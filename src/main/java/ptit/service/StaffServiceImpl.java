package ptit.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import ptit.repository.StaffDAO;

import ptit.entity.Staff;
@Service
public class StaffServiceImpl implements StaffService{
	@Autowired
	private StaffDAO staffDAO;
	
    @Override
    public List<Staff> findAll() {
        return this.staffDAO.findAll();
    }

    @Override
    public void save(Staff Staff) {
        this.staffDAO.save(Staff);
    }

	@Override
	public Staff findById(Long staffID) {
			Optional<Staff> optional = staffDAO.findById(staffID);
			Staff staff = null;
			if(optional.isPresent()) {
				staff = optional.get();
			}else {
				throw new RuntimeException("Staff not found for id :: "+staffID);
			}
		return staff;
	}

	@Override
	public void deleteById(Long staffID) {
		this.staffDAO.deleteById(staffID);	
	}


	@Override
	public List<Staff> findAll(Sort sort) {
		return (List<Staff>)staffDAO.findAll(sort);
	}
//	@Override
//	public Page<Staff> findAll(Pageable pageable) {
//		return (Page<Staff>)staffDAO.findAll(pageable);
//	}

	

    
}

