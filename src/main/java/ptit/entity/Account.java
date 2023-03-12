package ptit.entity;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.Calendar;
import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@Table(name = "account")
public class Account implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "accountID")
	private Long accountID;
	
	@NotEmpty(message = "Please enter email")
	@Email(message = "Wrong email")
	@Column(name = "email")
	private String email;
	
	@Size(min = 6, max = 225, message = "Password must be 6-225 digit")
	@NotEmpty(message = "Please enter password")
	@Column(name = "password")
	private String password;

	@Temporal(TemporalType.DATE)
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Calendar create_date;
	
	@NotNull(message="Permission cannot be null")
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "permissionID", nullable = false)
	private Permission permission;
	
	@Valid
	@OneToOne(mappedBy = "account",fetch = FetchType.EAGER)
	private Staff staff;

//	@OneToOne(mappedBy = "account")
//	private Customer customer;
    
	public Account() {
		
	}
	private Boolean status;
	public Account(Long accountID, @Email @NotEmpty(message = "Vui lòng nhập Email") String email,
			@Size(min = 6, max = 225, message = "Tên nhân viên phải từ 6-225 kí tự") @NotEmpty(message = "Vui lòng nhập password") String password,
			Calendar create_date, Boolean status, Permission permission, Staff staff, Customer customer) {
		this.accountID = accountID;
		this.email = email;
		this.password = password;
		this.create_date = create_date;
		this.status = status;
		this.permission = permission;
		this.staff = staff;
//		this.customer = customer;
	}

	public Long getAccountID() {
		return accountID;
	}

	public void setAccountID(Long accountID) {
		this.accountID = accountID;
	}


	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Calendar getCreate_date() {
		return create_date;
	}

	public void setCreate_date(Calendar create_date) {
		this.create_date = create_date;
	}

	public Boolean getStatus() {
		return status;
	}

	public void setStatus(Boolean status) {
		this.status = status;
	}

	
	public Permission getPermission() {
		return permission;
	}

	public void setPermission(Permission permission) {
		this.permission = permission;
	}

//	public Customer getCustomer() {
//		return customer;
//	}
//
//	public void setCustomer(Customer customer) {
//		this.customer = customer;
//	}
	
//	public Customer getCustomerID() {
//		return customer;
//	}
	
//	public void setCustomerID(Customer customerID) {
//		this.customer = customerID;
//	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Staff getStaff() {
		return staff;
	}

	public void setStaff(Staff staff) {
		this.staff = staff;
	}
	
	
	
}
