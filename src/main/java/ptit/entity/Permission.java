//package ptit.entity;
//
//
//import java.util.Collection;
//
//import jakarta.persistence.Column;
//import jakarta.persistence.Entity;
//import jakarta.persistence.FetchType;
//import jakarta.persistence.GeneratedValue;
//import jakarta.persistence.GenerationType;
//import jakarta.persistence.Id;
//import jakarta.persistence.OneToMany;
//import jakarta.persistence.Table;
//
//@Entity
//@Table(name ="permission")
//public class Permission {
//	@Id
//	@GeneratedValue(strategy = GenerationType.IDENTITY)
//	@Column(name ="permissionID")
//	private int permissionID;
//	
//	@Column(name ="email")
//	private String email;
//	
//	@Column(name ="role")
//	private int role;
//	
//	@OneToMany(mappedBy="permissionID",fetch=FetchType.EAGER)
//	private Collection<Account> account;
//
//	public Permission() {
//		
//	}
//
//	public Permission(int permissionID, String email, int role, Collection<Account> account) {
//		super();
//		this.permissionID = permissionID;
//		this.email = email;
//		this.role = role;
//		this.account = account;
//	}
//
//	public int getPermissionID() {
//		return permissionID;
//	}
//
//	public void setPermissionID(int permissionID) {
//		this.permissionID = permissionID;
//	}
//
//	public String getEmail() {
//		return email;
//	}
//
//	public void setEmail(String email) {
//		this.email = email;
//	}
//
//	public int getRole() {
//		return role;
//	}
//
//	public void setRole(int role) {
//		this.role = role;
//	}
//
//	public Collection<Account> getAccount() {
//		return account;
//	}
//
//	public void setAccount(Collection<Account> account) {
//		this.account = account;
//	}
//	
//	
//	
//}
