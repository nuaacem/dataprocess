package nuaa.ggx.pos.dataprocess.model;

import static javax.persistence.GenerationType.IDENTITY;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Table;

/**
 * TTrendHour entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "t_trend_day", catalog = "nuaacempos")
public class TTrendDay implements java.io.Serializable {


	/**
	 * 
	 */
	private static final long serialVersionUID = -6745948371778356009L;
	
	private Integer id;
	private Integer oid;
	private Short type;
	private Integer day;
	private Integer totalNum;
	private Integer positiveNum;
	private Integer neutralNum;
	private Integer negativeNum;

	// Constructors

	/** default constructor */
	public TTrendDay() {
	}

	/** minimal constructor */
	public TTrendDay(Integer id) {
		this.id = id;
	}

	/** full constructor */
	public TTrendDay(Integer id, Integer oid, Short type, Integer day,
			Integer totalNum, Integer positiveNum, Integer neutralNum,
			Integer negativeNum) {
		this.id = id;
		this.oid = oid;
		this.type = type;
		this.day = day;
		this.totalNum = totalNum;
		this.positiveNum = positiveNum;
		this.neutralNum = neutralNum;
		this.negativeNum = negativeNum;
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

/*	@ManyToOne(fetch = FetchType.LAZY)*/
	@JoinColumn(name = "oid")
	public Integer getOid() {
		return this.oid;
	}

	public void setOid(Integer oid) {
		this.oid = oid;
	}

	@Column(name = "type")
	public Short getType() {
		return this.type;
	}

	public void setType(Short type) {
		this.type = type;
	}

	@Column(name = "day")
	public Integer getDay() {
		return this.day;
	}

	public void setDay(Integer day) {
		this.day = day;
	}

	@Column(name = "total_num")
	public Integer getTotalNum() {
		return this.totalNum;
	}

	public void setTotalNum(Integer totalNum) {
		this.totalNum = totalNum;
	}

	@Column(name = "positive_num")
	public Integer getPositiveNum() {
		return this.positiveNum;
	}

	public void setPositiveNum(Integer positiveNum) {
		this.positiveNum = positiveNum;
	}

	@Column(name = "neutral_num")
	public Integer getNeutralNum() {
		return this.neutralNum;
	}

	public void setNeutralNum(Integer neutralNum) {
		this.neutralNum = neutralNum;
	}

	@Column(name = "negative_num")
	public Integer getNegativeNum() {
		return this.negativeNum;
	}

	public void setNegativeNum(Integer negativeNum) {
		this.negativeNum = negativeNum;
	}

}