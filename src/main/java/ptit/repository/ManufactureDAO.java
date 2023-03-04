package ptit.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import ptit.entity.Manufacturer;

public interface ManufactureDAO extends JpaRepository<Manufacturer, Long> {
	Manufacturer findByManufacturerID(int manufacturerID);
	
	List<Manufacturer> findAll();
}
