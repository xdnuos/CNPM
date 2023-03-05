package ptit.entity;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import jakarta.persistence.*;

@SuppressWarnings("serial")
@Entity
@Table(name="account")
public class Account implements Serializable {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name ="accountID")
	private BigInteger accountID;
	
	@Column(name ="email")
	private String email;
	
	@Column(name="password")
	private String password;
	
	@Temporal(TemporalType.DATE)
	@DateTimeFormat(pattern ="yyyy-MM-dd")
	private Date create_date;
	
	@Column(nullable = false)
	private Boolean status;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "permissionID", nullable = false)
	private Permission permission;
	
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "staffID")
	private Staff staffID;

	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "customerID")
	private Customer customerID;
    
	public Account() {
		
	}





public Account(BigInteger accountID, String email, String password, Date create_date, Boolean status,
			Permission permission, Staff staff, Customer customer) {
		super();
		this.accountID = accountID;
		this.email = email;
		this.password = password;
		this.create_date = create_date;
		this.status = status;
		this.permission = permission;
//		this.staff = staff;
//		this.customer = customer;
	}





//	public Permission getPermissionID() {
//		return permissionID;
//	}
//
//	public void setPermissionID(Permission permissionID) {
//		this.permissionID = permissionID;
//	}

	public BigInteger getAccountID() {
	return accountID;
}

public void setAccountID(BigInteger accountID) {
	this.accountID = accountID;
}

public String getEmail() {
	return email;
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





public Staff getStaffID() {
	return staffID;
}





public void setStaffID(Staff staffID) {
	this.staffID = staffID;
}


public Customer getCustomerID() {
	return customerID;
}

public void setCustomerID(Customer customerID) {
	this.customerID = customerID;
}
	
	
}
