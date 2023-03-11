package ptit.entity;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

@SuppressWarnings("serial")
@Entity
@Table(name = "account")
public class Account implements Serializable {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "accountID")
	private Long accountID;
	
	@Email
	@NotEmpty(message = "Vui lòng nhập Email")
	@Column(name = "email")
	private String email;
	
	@Size(min = 6, max = 225, message = "Tên nhân viên phải từ 6-225 kí tự")
	@NotEmpty(message = "Vui lòng nhập password")
	@Column(name = "password")
	private String password;

	@Temporal(TemporalType.DATE)
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date create_date;

	@Column(nullable = false)
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "permissionID", nullable = false)
	private Permission permission;
	
	@OneToOne(mappedBy = "account")
	private Staff staff;

	@OneToOne(mappedBy = "account")
	private Customer customer;
    
	public Account() {
		
	}
	private Boolean status;
	public Account(Long accountID, @Email @NotEmpty(message = "Vui lòng nhập Email") String email,
			@Size(min = 6, max = 225, message = "Tên nhân viên phải từ 6-225 kí tự") @NotEmpty(message = "Vui lòng nhập password") String password,
			Date create_date, Boolean status, Permission permission, Staff staff, Customer customer) {
		this.accountID = accountID;
		this.email = email;
		this.password = password;
		this.create_date = create_date;
		this.status = status;
		this.permission = permission;
		this.staff = staff;
		this.customer = customer;
	}



	public String getEmail() {
		return email;
	}

	public Long getAccountID() {
		return accountID;
	}

	public void setAccountID(Long accountID) {
		this.accountID = accountID;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Date getCreate_date() {
		return create_date;
	}

	public void setCreate_date(Date create_date) {
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

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}
public Staff getStaff() {
	return staff;
}

public void setStaff(Staff staff) {
	this.staff = staff;
}

public Customer getCustomerID() {
	return customer;
}

public void setCustomerID(Customer customerID) {
	this.customer = customerID;
}
	
}
