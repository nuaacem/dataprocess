package nuaa.ggx.pos.dataprocess.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * TWeiboContentSeg entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "t_weibo_content_seg", catalog = "nuaacempos")
public class TWeiboContentSeg implements java.io.Serializable {

	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = 1648857082360349025L;
	private Integer id;
	private String wid;
	private String txt;
	private String theme;
	private Integer count;
	private String pubtime;

	// Constructors

	/** default constructor */
	public TWeiboContentSeg() {
	}

	/** full constructor */
	public TWeiboContentSeg(String wid, String txt, String theme,
			Integer count, String pubtime) {
		this.wid = wid;
		this.txt = txt;
		this.theme = theme;
		this.count = count;
		this.pubtime = pubtime;
	}

	// Property accessors
	@Id
	@GeneratedValue
	@Column(name = "id", unique = true, nullable = false)
	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@Column(name = "wid", length = 16)
	public String getWid() {
		return this.wid;
	}

	public void setWid(String wid) {
		this.wid = wid;
	}

	@Column(name = "txt", length = 500)
	public String getTxt() {
		return this.txt;
	}

	public void setTxt(String txt) {
		this.txt = txt;
	}

	@Column(name = "theme", length = 16)
	public String getTheme() {
		return this.theme;
	}

	public void setTheme(String theme) {
		this.theme = theme;
	}

	@Column(name = "count")
	public Integer getCount() {
		return this.count;
	}

	public void setCount(Integer count) {
		this.count = count;
	}

	@Column(name = "pubtime", length = 50)
	public String getPubtime() {
		return this.pubtime;
	}

	public void setPubtime(String pubtime) {
		this.pubtime = pubtime;
	}

}