package ptit.service;

import java.math.BigInteger;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ptit.repository.StaffDAO;

import ptit.entity.Staff;
@Service
public class StaffServiceImpl implements StaffService{
	@Autowired
	private StaffDAO staffDAO;
	
    public StaffServiceImpl(StaffDAO StaffDAO) {
        this.staffDAO = StaffDAO;
    }

    @Override
    public List<Staff> findAll() {
        return this.staffDAO.findAll();
    }

    @Override
    public void save(Staff Staff) {
        this.staffDAO.save(Staff);
    }

//	@Override
//	public Staff findById(BigInteger staffID) {
//		return staffDAO.findById(staffID);
//	}

//	@Override
//	public void update(BigInteger staffID, Staff staff) {
//		this.staffDAO.update(staffID, staff);
//		
//	}
//
//	@Override
//	public void remove(BigInteger staffID) {
//		this.staffDAO.remove(staffID);
//		
//	}

    
}

