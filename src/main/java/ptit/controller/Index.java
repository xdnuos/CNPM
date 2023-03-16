package ptit.controller;
import java.util.Calendar;
import java.util.Date;
import java.util.UUID;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.core.Authentication;

import ptit.entity.Account;
import ptit.entity.Customer;
import ptit.entity.PasswordResetToken;
import ptit.entity.Permission;
import ptit.entity.Staff;
import ptit.repository.AccountDAO;
import ptit.repository.PasswordResetTokenRepository;
import ptit.repository.StaffDAO;
import ptit.service.AccountService;
import ptit.service.CustomerSerivce;
import ptit.service.ISecurityUserService;
import ptit.service.SendEmailService;

@Controller
public class Index {
	@Autowired
	AccountService accountService;
	@Autowired
	CustomerSerivce customerSerivce;
	@Autowired
	StaffDAO staffDAO;
	@Autowired
	PasswordResetTokenRepository passwordResetTokenRepository;
	
	@Autowired
	SendEmailService emailService;
	
	@Autowired
	ISecurityUserService iSecurityUserService;
	
	@GetMapping("/login")
	public String login(Model model,@RequestParam(value = "error",defaultValue = "false") String error,@RequestParam(value = "message",defaultValue = "false") String message) {
		if(error.equals("true")) {
			model.addAttribute("error",true);
		}
		if(!message.equals("false")) {
			model.addAttribute("message",message);
		}
		
		return"/admin/login";
	}

//	@GetMapping("/register")
//	public String showRegistrationForm(Model model) {
//		Customer neCustomer = new Customer();
//		Account account = new Account();
//		neCustomer.setAccount(account);
//		model.addAttribute("customer", new Customer());
//	    model.addAttribute("ERROR","o");
//	    model.addAttribute("phoneError","o");
//	    return "register";
//	}
	
//	@PostMapping("/register")
//	public String processRegister(@Valid Customer customer,BindingResult result, SessionStatus status, ModelMap model) {
//		  if (result.hasErrors()) {
////			  System.out.print(result.getAllErrors());
//			    return "register";
//			  }
//		  
//		  if(!customerSerivce.checkPhone(customer.getPhone())) {
//		    	model.addAttribute("phoneError", "Số điện thoại đã tồn tại, vui lòng sử dụng số điện thoại khác");
//		    	return "register";
//		  }
//		  
//	    if(accountService.checkEmail(customer.getAccount().getEmail())) {
//	    	Permission permission = new Permission();
//	    	permission.setPermissionID((long) 3);
//	    	Calendar calendar = Calendar.getInstance();
//		    BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
//		    String encodedPassword = passwordEncoder.encode(customer.getAccount().getPassword());
//		    customer.getAccount().setPassword(encodedPassword);
//		    customer.getAccount().setCreate_date(calendar);
//		    customer.getAccount().setStatus(true);
//		    customer.getAccount().setPermission(permission);
//	    	customerSerivce.save(customer);
//	    	status.setComplete();
//	    } else {
//	    	model.addAttribute("ERROR", "Email đã tồn tại, vui lòng sử dụng email khác");
//	    	return "register";
//	    }
//	    
//	    return "redirect:login";
//	}
 
	@GetMapping("/logout")
	public String logout(HttpSession session) {
		session.removeAttribute("email");
		return"login";
	}
	
	@GetMapping("/403")
	public String page403(HttpSession session) {
		return"403";
	}
	
	@GetMapping("/")
	public String index() {
		return "redirect:/admin";
	}
	
	@GetMapping("/admin/accountInfo")
	public String accountInfo(Model model, @RequestParam("id") String email,RedirectAttributes redirectAttributes) {
		Account account = accountService.findByEmail(email);
		model.addAttribute("account",account);
		return "/admin/accountInfo";
	}
	
	@PostMapping("/admin/accountInfo")
	public String saveAccountInfo(@Valid Account account,BindingResult result, Model model, SessionStatus status,
			@RequestParam("newpassword") String newPassword,
			RedirectAttributes attributes) {
		if(result.hasErrors()) {
			model.addAttribute("account",account);
			return "admin/accountInfo";
		}
		
		Staff staff = staffDAO.findById(account.getStaff().getStaffID()).get();
		staff.setCccd(account.getStaff().getCccd());
//		staff.setBirth(account.getStaff().getBirth());
		staff.setFullname(account.getStaff().getFullname());
		staff.setPhone(account.getStaff().getPhone());
		staff.setSex(account.getStaff().getSex());
//		staff.setBirth(birth);
		//nhung thu k thay doi
		account.setAccountID(account.getAccountID());
		account.setPermission(account.getPermission());
		account.setPassword(account.getPassword());
		account.setStatus(true);
		if(newPassword!="" && newPassword!=null) {
		    BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		    String encodedPassword = passwordEncoder.encode(newPassword);
		    account.setPassword(encodedPassword);
		}
		//thay doi staff
		account.setStaff(staff);
		accountService.save(account);
		
		status.setComplete();
		attributes.addAttribute("message","Save information complete!");
		return "redirect:/admin";
	}
	
	@GetMapping("/fogetpassword")
	public String forgetpassword(Model model,@RequestParam(value = "error",defaultValue = "") String error) {
		if(!error.equals("")) {
			model.addAttribute("error",true);
		}
		return "/admin/forgetpass";
	}
	
	@PostMapping("/fogetpassword")
	public String resetPassword(Model model, @RequestParam("email") String email,RedirectAttributes attributes) {
		Account account = accountService.findByEmail(email);
		
		if(account==null) {
			model.addAttribute("error","Email does not exist");
			return "/admin/forgetpass";
		}
		//send email
        String token = UUID.randomUUID().toString();
        PasswordResetToken passwordResetToken = new PasswordResetToken();
        passwordResetToken.setAccount(account);
        passwordResetToken.setToken(token);
        passwordResetToken.setExpiryDate(new Date(System.currentTimeMillis() + 600000));
        passwordResetTokenRepository.save(passwordResetToken);

        // Gửi email khôi phục mật khẩu
        String subject = "Reset Password";
        String message = "Click here to reset your password: "
                + "http://localhost:8080/reset-password?token=" + token;
        try {
        	emailService.sendEmail(email, subject, message);
        }catch (Exception e) {
        	attributes.addAttribute("message","Sorry! Something is wrong. Please try again");
        	return "redirect:/login";
		}

        model.addAttribute("error","Please check your email!");
		return "/admin/forgetpass";
	}
	
	@GetMapping("/reset-password")
	public String resetpass(Model model, @RequestParam(value = "token", defaultValue = "no") String token,RedirectAttributes attributes) {
		if(token.equals("no")) {
			attributes.addAttribute("message","Sorry! Something is wrong");
		}
		
		String value=iSecurityUserService.validatePasswordResetToken(token);
		if(value.equals("ok")) {
//			model.addAttribute("token",token);
			return "/admin/reset-password";
		}else if (value.equals("invalidToken")) {
			attributes.addAttribute("message","Sorry! Something is wrong");
		}else if (value.equals("expiredToken")) {
			attributes.addAttribute("message","Sorry! This link is expired");
		}
		return "redirect:/login";
	}
	
	@PostMapping("/reset-password")
    public String updatePassword(@RequestParam("token") String token,@RequestParam("password") String newPassword, RedirectAttributes attributes) {
		PasswordResetToken passwordResetToken = passwordResetTokenRepository.findByToken(token);
		
        if (passwordResetToken == null) {
        	System.out.print("ddddddddddddddddddddddddddddd"+token+"ssssssssss");
        	attributes.addAttribute("message","Sorry! Something is wrong");
        	return "redirect:/login";
        }
        
        Account account = passwordResetToken.getAccount();
        
        
        // Cập nhật mật khẩu mới cho người dùng và xoá token khôi phục mật khẩu
	    BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
	    String encodedPassword = passwordEncoder.encode(newPassword);
        account.setPassword(encodedPassword);
        
        accountService.save(account);
        passwordResetTokenRepository.deleteById(passwordResetToken.getId());
        attributes.addAttribute("message", "Password recovery successful!");
        return "redirect:/login";
    }
	
	@GetMapping("/test")
	public String test() {
		return "admin/test";
	}
}
