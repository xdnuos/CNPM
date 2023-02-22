package ptit.entity;

import java.math.BigInteger;
import java.util.Collection;
import java.util.Date;

import jakarta.persistence.*;

@Entity
@Table(name="account")
public class Account {
	@Id
	
	private BigInteger accountID;
	
	@Column(name ="email")
	private String email;
	
	@Column(name="password")
	private String password;
	
	@Temporal(TemporalType.DATE)
	private Date createDate;
	//permissionID
	@OneToMany(mappedBy="accountID",fetch=FetchType.EAGER)
	private Collection<Staff> staffs;
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
	public Date getCreateDate() {
		return createDate;
	}
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	public Collection<Staff> getStaffs() {
		return staffs;
	}
	public void setStaffs(Collection<Staff> staffs) {
		this.staffs = staffs;
	}
	
}
