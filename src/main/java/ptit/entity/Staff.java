package ptit.entity;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.Collection;
import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;

@Entity
@Table(name = "staff")
public class Staff implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name ="staffID")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private BigInteger staffID;
	
	@Column(name = "fullname", length = 225)
	private String fullname;
	
	private boolean sex;
	
	@Column(nullable = false, length = 15)
	private String phone;
	
	@Temporal(TemporalType.DATE)
	@DateTimeFormat(pattern ="MM/dd/yyyy")
	private Date birth;
	
	@Column(name ="cccd")
	private String cccd;

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "staff", cascade = CascadeType.ALL)
    private Collection<Order> order;
	
//	@OneToOne(mappedBy="staffID")
//	private Account account;
	
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "accountID")
	private Account account;
	
	public Staff() {
		
	}

	public Staff(BigInteger staffID, String fullname, Boolean sex, String phone, Date birth, String cccd,
			Account account) {
		super();
		this.staffID = staffID;
		this.fullname = fullname;
		this.sex = sex;
		this.phone = phone;
		this.birth = birth;
		this.cccd = cccd;
	}

	public BigInteger getStaffID() {
		return staffID;
	}

	public void setStaffID(BigInteger staffID) {
		this.staffID = staffID;
	}

	public String getFullname() {
		return fullname;
	}

	public void setFullname(String fullname) {
		this.fullname = fullname;
	}

	public boolean getSex() {
		return sex;
	}

	public void setSex(boolean sex) {
		this.sex = sex;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public Date getBirth() {
		return birth;
	}

	public void setBirth(Date birth) {
		this.birth = birth;
	}

	public String getCccd() {
		return cccd;
	}

	public void setCccd(String cccd) {
		this.cccd = cccd;
	}

	public Collection<Order> getOrder() {
		return order;
	}

	public void setOrder(Collection<Order> order) {
		this.order = order;
	}

	public Account getAccount() {
		return account;
	}

	public void setAccount(Account account) {
		this.account = account;
	}

}
