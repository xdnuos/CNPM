package ptit.controller;

import java.io.File;
import java.math.BigDecimal;

import org.apache.commons.io.FilenameUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.multipart.MultipartFile;

import ptit.entity.Product;

@Controller
public class AdminProduct {
	@RequestMapping(value = "/admin/product")
	public String index() {
		return "admin/product";
	}
	
	@RequestMapping(value = "/admin/addproduct")
	public String addProduct() {
		return "admin/addproduct";
	}  
	
//    @PostMapping(value = "admin/addproduct")
//    public String addCar(Product product, Model model,
//    		 @RequestParam(value = "carName") String carName,
//    		 @RequestParam(value = "desc") String desc,
//    		 @RequestParam(value = "price") BigDecimal price,
//    		 SessionStatus status,
//    		 @RequestParam("image") MultipartFile image) {
////        try {
////            imgName = image.getOriginalFilename().replace(image.getOriginalFilename(), FilenameUtils.getBaseName(image.getOriginalFilename()).concat(currentDate) + "." + FilenameUtils.getExtension(image.getOriginalFilename())).toLowerCase();
////            File file = new File(this.getFolderUpload(), imgName);
////            image.transferTo(file);
////          } catch (Exception e) {
////            e.printStackTrace();
////          }
//        
//        product.setName(carName);
//        product.setDescription(desc);
//        product.setPrice(price);
//        product.setImgName(imgName);
////    	carService.save(car);
//    	status.setComplete();
//        return "redirect:/admin";
//    }
}
