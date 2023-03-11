package ptit.repository;


//import java.util.List;
//import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ptit.entity.Staff;

@Repository
public interface StaffDAO extends JpaRepository<Staff, Long> {

	//List<Staff> findByNameLikeOrderByName(String fullname);
	Staff save(Staff staff);

	
}
