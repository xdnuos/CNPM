package ptit.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ptit.entity.Manufacturer;
import ptit.repository.ManufactureDAO;
import ptit.service.ManufacturerService;

@Service
public class ManufactureServiceImpl implements ManufacturerService{
	@Autowired
	private ManufactureDAO manufactureDAO;

	@Override
	public List<Manufacturer> findAll() {
		// TODO Auto-generated method stub
		return this.manufactureDAO.findAll();
	}

	@Override
	public Manufacturer findByManufacturerID(int manufacturerID) {
		// TODO Auto-generated method stub
		return this.manufactureDAO.findByManufacturerID(manufacturerID);
	}

	@Override
	public void save(Manufacturer manufacturer) {
		// TODO Auto-generated method stub
		this.manufactureDAO.save(manufacturer);
	}
	
	
}
