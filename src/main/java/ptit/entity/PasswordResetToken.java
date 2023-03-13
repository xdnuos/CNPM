package ptit.entity;

import javax.persistence.*;
import java.util.Date;
@Entity
public class PasswordResetToken {
    private static final int EXPIRATION = 60 * 24;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String token;

    @OneToOne(targetEntity = Account.class, fetch = FetchType.EAGER)
    @JoinColumn(nullable = false, name = "accountID")
    private Account account;

    private Date expiryDate;
    
    

    public PasswordResetToken() {
		super();
		// TODO Auto-generated constructor stub
	}

	public PasswordResetToken(Long id, String token, Account account, Date expiryDate) {
		super();
		this.id = id;
		this.token = token;
		this.account = account;
		this.expiryDate = expiryDate;
	}

	public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }


    public Date getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(Date expiryDate) {
        this.expiryDate = expiryDate;
    }

    public PasswordResetToken(String token, Account account) {
        this.token = token;
        this.account = account;
    }

	public Account getAccount() {
		return account;
	}

	public void setAccount(Account account) {
		this.account = account;
	}
}