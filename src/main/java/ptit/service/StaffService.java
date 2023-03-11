package ptit.service;

import java.util.List;
import java.util.Optional;

//import org.springframework.data.domain.Page;
//import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import ptit.entity.Account;
import ptit.entity.Staff;


public interface StaffService {
	List<Staff> findAll();
	
	Staff save(Staff staff);
	
	Optional<Staff> findById(Long staffID);
	
    void deleteById(Long staffID);
	
    //Page<Staff> findAll(Pageable pageable);
   
	List<Staff> findAll(Sort sort);

	List<Account> findAllAccount();
	
	//List<Staff> findByNameLikeOrderByName(String fullname);
}
