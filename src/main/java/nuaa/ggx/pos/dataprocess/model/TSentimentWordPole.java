package nuaa.ggx.pos.dataprocess.model;

import static javax.persistence.GenerationType.IDENTITY;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * TSentimentWordPole entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "t_sentiment_word_pole", catalog = "nuaacempos")
public class TSentimentWordPole implements java.io.Serializable {

	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = 1339980378668281181L;
	private Integer id;
	private String word;
	private Short pole;

	// Constructors

	/** default constructor */
	public TSentimentWordPole() {
	}
	
	/** minimal constructor */
	public TSentimentWordPole(Integer id) {
		this.id = id;
	}

	/** full constructor */
	public TSentimentWordPole(Integer id, String word, Short pole) {
		this.id = id;
		this.word = word;
		this.pole = pole;
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
	
	@Column(name = "word")
	public String getWord() {
		return this.word;
	}

	public void setWord(String word) {
		this.word = word;
	}

	@Column(name = "pole")
	public Short getPole() {
		return this.pole;
	}

	public void setPole(Short pole) {
		this.pole = pole;
	}

}