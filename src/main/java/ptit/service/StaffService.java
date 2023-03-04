package ptit.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import ptit.entity.Staff;


public interface StaffService {
	List<Staff> findAll();
	
	void save(Staff staff);
	
	Staff findById(Long staffID);
	
    void deleteById(Long staffID);
	
    //Page<Staff> findAll(Pageable pageable);
   

	List<Staff> findAll(Sort sort);
}
