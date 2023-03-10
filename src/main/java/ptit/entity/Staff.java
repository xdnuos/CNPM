package ptit.entity;

import java.io.Serializable;
import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "staff")
public class Staff implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "staffID")
	private Long staffID;

	@Size(min = 1, max = 100, message = "Tên nhân viên phải từ 1-100 kí tự")
	@Column(name = "fullname", length = 225)
	@NotEmpty(message = "Vui lòng nhập tên nhân viên")
	private String fullname;

	@Column(nullable = false)
	private Boolean sex;

	@NotEmpty(message = "Vui lòng nhập số điện thoại")
	@Column(nullable = false, length = 15)
	private String phone;

	@Temporal(TemporalType.DATE)
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date birth;
	
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "accountID")
	private Account account;

//	@OneToMany(fetch = FetchType.LAZY, mappedBy = "staff", cascade = CascadeType.ALL)
//    private Collection<Order> order;

	public Staff() {

	}

	public Staff(Long staffID, String fullname, Boolean sex, String phone, Date birth, Account account) {
		this.staffID = staffID;
		this.fullname = fullname;
		this.sex = sex;
		this.phone = phone;
		this.birth = birth;
		this.account = account;
		// this.order = order;
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

	public Boolean getSex() {
		return sex;
	}

	public void setSex(Boolean sex) {
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
//
//	public Collection<Order> getOrder() {
//		return order;
//	}
//
//	public void setOrder(Collection<Order> order) {
//		this.order = order;
//	}

	public Account getAccount() {
		return account;
	}

	public void setAccount(Account account) {
		this.account = account;
	}

}
