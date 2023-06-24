package ptit.repository;


import java.util.List;

//import java.util.List;
//import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import ptit.entity.Staff;

@Repository
public interface StaffDAO extends JpaRepository<Staff, Long> {

	//List<Staff> findByNameLikeOrderByName(String fullname);
	Staff save(Staff staff);

	@Query("from Staff as s where CAST(s.staffID AS string) LIKE %:searchValueParam% or s.fullname LIKE %:searchValueParam% or s.phone LIKE %:searchValueParam% or s.cccd LIKE %:searchValueParam%")
	List<Staff> findStaffs(@Param("searchValueParam") String searchValue);
}
