package ptit.entity;

import java.math.BigInteger;
import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import jakarta.persistence.*;


@Entity
@Table(name = "staff")
public class Staff {
	@Id
	@Column(name ="staffID")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private BigInteger staffID;
	
	@Column(name = "fullname", unique = true, length = 225)
	private String fullname;
	
	@Column(nullable = false)
	private Boolean sex;
	
	@Column(nullable = false, length = 15)
	private String phone;
	
	@Temporal(TemporalType.DATE)
	@DateTimeFormat(pattern ="MM/dd/yyyy")
	private Date birth;
	
	@Column(name ="cccd")
	private String cccd;
	
	@ManyToOne
	@JoinColumn(name="accountID")
	private Account accountID;
    
	
}
