package nuaa.ggx.pos.dataprocess.model;

import java.sql.Timestamp;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;

import static javax.persistence.GenerationType.IDENTITY;

import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * TKeyword entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "t_keyword", catalog = "nuaacempos")
public class TKeyword implements java.io.Serializable {

	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = 4297702156026003110L;
	private Integer id;
	private String keyword;
	private Integer updateNum;
	private Timestamp updateTime;
	private Integer state;
	private TSubject TSubject;

	// Constructors

	/** default constructor */
	public TKeyword() {
	}
	public TKeyword(String keyword) {
		this.keyword = keyword;
	}

	/** full constructor */
	public TKeyword(String keyword, Integer updateNum,
			Integer state) {
		this.keyword = keyword;
		this.updateNum = updateNum;
		this.state = state;
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

	@Column(name = "keyword", length = 20)
	public String getKeyword() {
		return this.keyword;
	}

	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}

	@Column(name = "update_num")
	public Integer getUpdateNum() {
		return this.updateNum;
	}

	public void setUpdateNum(Integer updateNum) {
		this.updateNum = updateNum;
	}
	
	@Column(name = "update_time", length = 19)
	public Timestamp getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(Timestamp updateTime) {
		this.updateTime = updateTime;
	}
	@Column(name = "state")
	public Integer getState() {
		return this.state;
	}

	public void setState(Integer state) {
		this.state = state;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "subject_id")
	public TSubject getTSubject() {
		return TSubject;
	}
	public void setTSubject(TSubject tSubject) {
		this.TSubject = tSubject;
	}


}