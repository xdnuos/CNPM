package ptit.entity;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.Collection;
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
//	@ManyToOne
//	@JoinColumn(name="permissionID")
//	private Permission permissionID;
	
	@OneToMany(mappedBy="accountID",fetch=FetchType.EAGER)
	private Collection<Staff> staff;

	public Account() {
		
	}

public Account(BigInteger accountID, String email, String password, Date create_date, Boolean status,
			Collection<Staff> staff) {
		
		this.accountID = accountID;
		this.email = email;
		this.password = password;
		this.create_date = create_date;
		this.status = status;
		this.staff = staff;
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

	public Collection<Staff> getStaff() {
		return staff;
	}

	public void setStaff(Collection<Staff> staff) {
		this.staff = staff;
	}
	
	
}
