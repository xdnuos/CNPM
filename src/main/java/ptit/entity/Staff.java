package ptit.entity;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.Collection;
import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Entity
@Table(name = "staff")
public class Staff implements Serializable{
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name ="ID")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long staffID;
	
	@NotBlank(message="Full name cannot be blank")
    @Pattern(regexp="^[\\p{L} \\.'\\-]+$", message="Full name must contain only letters and spaces")
	@Column(name = "fullname", length = 225)
	private String fullname;
	
	@NotNull(message="Gender cannot be null")
	private boolean sex;
	
	@Pattern(regexp="\\d{9}|\\d{10}", message="Phone number must be 9 or 10 digits")
	@Column(nullable = false, length = 15,unique=true)
	private String phone;
	
	@NotNull(message="Birth date cannot be null")
	@Temporal(TemporalType.DATE)
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@Past(message="Birth date must be in the past")
	private Date birth;
	
	@Pattern(regexp="\\d{9}|\\d{12}", message="Identity ID must be 9 or 12 digits")
	@Column(name ="cccd",unique=true)
	private String cccd;

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "staff", cascade = CascadeType.ALL)
    private Collection<Order> order;

	@Valid
	@OneToOne(cascade = CascadeType.ALL)
	@MapsId
	@JoinColumn(name = "accountID")
	private Account account;
	
//	@Column(name = "IDManager")
	@ManyToOne
	private Staff manager;
	
	public Staff() {
		
	}

	public Staff(Long staffID, String fullname, Boolean sex, String phone, Date birth, String cccd,
			Account account) {
		super();
		this.staffID = staffID;
		this.fullname = fullname;
		this.sex = sex;
		this.phone = phone;
		this.birth = birth;
		this.cccd = cccd;
	}

	public Long getStaffID() {
		return staffID;
	}

	public void setStaffID(Long staffID) {
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

	public Staff getStaff() {
		return manager;
	}

	public void setStaff(Staff staff) {
		this.manager = staff;
	}
}