package ptit.service;

import java.util.List;

import ptit.entity.Manufacturer;

public interface ManufacturerService {
	List<Manufacturer> findAll();
	
	Manufacturer findByManufacturerID(int manufacturerID);
	
	void save(Manufacturer manufacturer);
}
