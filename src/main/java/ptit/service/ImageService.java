package ptit.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ptit.entity.Image;
import ptit.repository.ImageDAO;

@Service
public class ImageService {
	@Autowired
	private ImageDAO imageDAO;
	
	public List<Image> findByProductId(Long id) {
		return this.imageDAO.findByProductId(id);
	}
	
	public void deleteByID(Long id) {
		this.imageDAO.deleteById(id);
	}
}
