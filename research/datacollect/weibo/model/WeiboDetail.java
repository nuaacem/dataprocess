package rivers.yeah.research.datacollect.weibo.model;

import java.sql.Timestamp;

public class WeiboDetail {
	private Long id;
	private Integer forwardNum;
	private Integer commentNum;
	private Integer likeNum;
	private Timestamp modifyTime;
	
	public WeiboDetail(Long id, Integer forwardNum, Integer commentNum,
			Integer likeNum, Timestamp modifyTime) {
		this.id = id;
		this.forwardNum = forwardNum;
		this.commentNum = commentNum;
		this.likeNum = likeNum;
		this.modifyTime = modifyTime;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
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
	public Timestamp getModifyTime() {
		return modifyTime;
	}
	public void setModifyTime(Timestamp modifyTime) {
		this.modifyTime = modifyTime;
	}
}
