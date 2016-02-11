package rivers.yeah.research.datacollect.weibo.model;

import java.sql.Timestamp;

public class Weibo {
	private Long id;
	private String mid;
	private Long uid;
	private Long forwardId;
	private String content;
	private Integer mediaType;
	private String domainId;
	private Integer forwardNum;
	private Integer commentNum;
	private Integer likeNum;
	private String pubTime;
	private Timestamp collectTime;
	private Timestamp modifyTime;
	
	public Weibo() {
		
	}
	
	public Weibo(Long id, String mid, Long uid, Long forwardId,
			String content, Integer mediaType, String domainId,
			Integer forwardNum,	Integer commentNum, Integer likeNum, String pubTime) {
		this.id = id;
		this.mid = mid;
		this.uid = uid;
		this.forwardId = forwardId;
		this.content = content;
		this.mediaType = mediaType;
		this.domainId = domainId;
		this.forwardNum = forwardNum;
		this.commentNum = commentNum;
		this.likeNum = likeNum;
		this.pubTime = pubTime;
	}
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getMid() {
		return mid;
	}
	public void setMid(String mid) {
		this.mid = mid;
	}
	public Long getUid() {
		return uid;
	}
	public void setUid(Long uid) {
		this.uid = uid;
	}
	public Long getForwardId() {
		return forwardId;
	}
	public void setForwardId(Long forwardId) {
		this.forwardId = forwardId;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public Integer getMediaType() {
		return mediaType;
	}
	public void setMediaType(Integer mediaType) {
		this.mediaType = mediaType;
	}
	public String getDomainId() {
		return domainId;
	}
	public void setDomainId(String domainId) {
		this.domainId = domainId;
	}
	public Integer getForwardNum() {
		return forwardNum;
	}
	public void setForwardNum(Integer forwardNum) {
		this.forwardNum = forwardNum;
	}
	public Integer getCommentNum() {
		return commentNum;
	}
	public void setCommentNum(Integer commentNum) {
		this.commentNum = commentNum;
	}
	public Integer getLikeNum() {
		return likeNum;
	}
	public void setLikeNum(Integer likeNum) {
		this.likeNum = likeNum;
	}
	public String getPubTime() {
		return pubTime;
	}
	public void setPubTime(String pubTime) {
		this.pubTime = pubTime;
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
