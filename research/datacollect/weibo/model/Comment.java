package rivers.yeah.research.datacollect.weibo.model;

import java.sql.Timestamp;

public class Comment {
	private Long id;
	private Long uid;
	private Long weiboId;
	private String mid;
	private String content;
	private Integer heat;
	private String commentTime;
	private Timestamp collectTime;
	private Timestamp modifyTime;
	
	public Comment(Long id, Long uid, String content, Integer heat,	String commentTime) {
		this.id = id;
		this.uid = uid;
		this.content = content;
		this.heat = heat;
		this.commentTime = commentTime;
	}
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getUid() {
		return uid;
	}
	public void setUid(Long uid) {
		this.uid = uid;
	}
	public Long getWeiboId() {
		return weiboId;
	}
	public void setWeiboId(Long weiboId) {
		this.weiboId = weiboId;
	}
	public String getMid() {
		return mid;
	}
	public void setMid(String mid) {
		this.mid = mid;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public Integer getHeat() {
		return heat;
	}
	public void setHeat(Integer heat) {
		this.heat = heat;
	}
	public String getCommentTime() {
		return commentTime;
	}
	public void setCommentTime(String commentTime) {
		this.commentTime = commentTime;
	}
	public Timestamp getCollectTime() {
		return collectTime;
	}
	public void setCollectTime(Timestamp collectTime) {
		this.collectTime = collectTime;
	}
	public Timestamp getModifyTime() {
		return modifyTime;
	}
	public void setModifyTime(Timestamp modifyTime) {
		this.modifyTime = modifyTime;
	}
}
