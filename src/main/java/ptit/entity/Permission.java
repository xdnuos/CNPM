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
	private int permissionID;
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "permission", cascade = CascadeType.ALL)
    private Collection<Account> account;
	
	public Collection<Account> getAccount() {
		return account;
	}

	public void setAccount(Collection<Account> account) {
		this.account = account;
	}

	private String name;

	private int role;

	public Permission() {
	}

	public int getPermissionID() {
		return this.permissionID;
	}

	public void setPermissionID(int permissionID) {
		this.permissionID = permissionID;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getRole() {
		return this.role;
	}

	public void setRole(int role) {
		this.role = role;
	}

}