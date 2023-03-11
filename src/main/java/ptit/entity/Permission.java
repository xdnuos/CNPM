package ptit.entity;

import java.io.Serializable;
import java.util.Collection;

import jakarta.persistence.*;


/**
 * The persistent class for the permission database table.
 * 
 */
@Entity
@Table(name="permission")
public class Permission implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long permissionID;
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "permission", cascade = CascadeType.ALL)
    private Collection<Account> account;
	
	
	private String name;


	public Permission() {
	}

	public Permission( String name) {
//		this.permissionID = permissionID;
//		this.account = account;
		this.name = name;
	}

	public Long getPermissionID() {
		return this.permissionID;
	}

	public void setPermissionID(Long permissionID) {
		this.permissionID = permissionID;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}
	public Collection<Account> getAccount() {
		return account;
	}

	public void setAccount(Collection<Account> account) {
		this.account = account;
	}
}