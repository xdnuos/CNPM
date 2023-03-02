package ptit.service;

import java.math.BigInteger;
import java.util.List;

import ptit.entity.Staff;


public interface StaffService {
	List<Staff> findAll();
	
	void save(Staff staff);
	
	//Staff findById(BigInteger staffID);
	
//    void update(BigInteger staffID, Staff staff);
//    
//    void remove(BigInteger staffID);
    
	
}
