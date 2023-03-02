package ptit.repository;

import java.math.BigInteger;
import java.util.List;


import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.stereotype.Repository;

import ptit.entity.Staff;

@Repository
public interface StaffDAO extends JpaRepository<Staff, BigInteger> {
	
	List<Staff> findAll();

    //Staff findById(BigInteger staffID);

//	void update(BigInteger staffID, Staff staff);
//	
//	void remove(BigInteger staffID);

	
}
