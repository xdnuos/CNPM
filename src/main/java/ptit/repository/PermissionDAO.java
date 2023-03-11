package ptit.repository;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import ptit.entity.Permission;

@Repository
public interface PermissionDAO extends JpaRepository<Permission, Long> {
	@Query("from Permission as u where u.name = :name")
	Permission findByName(@Param("name") String name);
	
	List<Permission> findAll();

	
}
