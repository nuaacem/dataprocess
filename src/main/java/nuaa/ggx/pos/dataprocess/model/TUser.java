package nuaa.ggx.pos.dataprocess.model;

import java.sql.Timestamp;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * TUser entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "t_user", catalog = "nuaacempos")
public class TUser implements java.io.Serializable {

	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = -8179610994491786956L;
	private Integer id;
	private String username;
	private String password;
	private String trueName;
	private Integer typeId;
	private String mobileNumber;
	private String email;
	private String lastSignInIp;
	private Timestamp createTime;
	private Timestamp lastSignInTime;
	private Integer state;
	private Set<TSubject> TSubjects = new HashSet<TSubject>(0);

	// Constructors

	/** default constructor */
	public TUser() {
	}

	public TUser(Integer id) {
		this.id = id;
	}
	
	/** full constructor */
	public TUser(String username, String password, String trueName,
			Integer typeId, String mobileNumber, String email,
			String lastSignInIp, Timestamp createTime, Timestamp lastSignInTime,
			Integer state, Set<TSubject> TSubjects) {
		this.username = username;
		this.password = password;
		this.trueName = trueName;
		this.typeId = typeId;
		this.mobileNumber = mobileNumber;
		this.email = email;
		this.lastSignInIp = lastSignInIp;
		this.createTime = createTime;
		this.lastSignInTime = lastSignInTime;
		this.state = state;
		this.TSubjects = TSubjects;
	}

	// Property accessors
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "id", unique = true, nullable = false)
	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@Column(name = "username", length = 20)
	public String getUsername() {
		return this.username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	@Column(name = "password", length = 255)
	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Column(name = "true_name", length = 20)
	public String getTrueName() {
		return this.trueName;
	}

	public void setTrueName(String trueName) {
		this.trueName = trueName;
	}

	@Column(name = "type_id")
	public Integer getTypeId() {
		return this.typeId;
	}

	public void setTypeId(Integer typeId) {
		this.typeId = typeId;
	}

	@Column(name = "mobile_number", length = 20)
	public String getMobileNumber() {
		return this.mobileNumber;
	}

	public void setMobileNumber(String mobileNumber) {
		this.mobileNumber = mobileNumber;
	}

	@Column(name = "email", length = 50)
	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@Column(name = "last_sign_in_ip", length = 20)
	public String getLastSignInIp() {
		return this.lastSignInIp;
	}

	public void setLastSignInIp(String lastSignInIp) {
		this.lastSignInIp = lastSignInIp;
	}

	@Column(name = "create_time", length = 19)
	public Timestamp getCreateTime() {
		return this.createTime;
	}

	public void setCreateTime(Timestamp createTime) {
		this.createTime = createTime;
	}

	@Column(name = "last_sign_in_time", length = 19)
	public Timestamp getLastSignInTime() {
		return this.lastSignInTime;
	}

	public void setLastSignInTime(Timestamp lastSignInTime) {
		this.lastSignInTime = lastSignInTime;
	}

	@Column(name = "state")
	public Integer getState() {
		return this.state;
	}

	public void setState(Integer state) {
		this.state = state;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "TUser")
	public Set<TSubject> getTSubjects() {
		return this.TSubjects;
	}

	public void setTSubjects(Set<TSubject> TSubjects) {
		this.TSubjects = TSubjects;
	}

}