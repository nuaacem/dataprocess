package rivers.yeah.research.datacollect.weibo.model;

import java.sql.Timestamp;

public class User {
	private Long uid;
	private String nickname;
	private Integer approveState;
	private Integer vipLevel;
	private Integer darenLevel;
	private Integer fanNum;
	private Integer followNum;
	private Timestamp collectTime;
	private Timestamp modifyTime;
	
	public User() {

	}
	
	public User(Long uid, String nickname, Integer approveState,
			Integer vipLevel, Integer darenLevel, Integer fanNum,
			Integer followNum) {
		this.uid = uid;
		this.nickname = nickname;
		this.approveState = approveState;
		this.vipLevel = vipLevel;
		this.darenLevel = darenLevel;
		this.fanNum = fanNum;
		this.followNum = followNum;
	}

	public Long getUid() {
		return uid;
	}
	public void setUid(Long uid) {
		this.uid = uid;
	}
	public String getNickname() {
		return nickname;
	}
	public void setNickname(String nickname) {
		this.nickname = nickname;
	}
	public Integer getApproveState() {
		return approveState;
	}
	public void setApproveState(Integer approveState) {
		this.approveState = approveState;
	}
	public Integer getVipLevel() {
		return vipLevel;
	}
	public void setVipLevel(Integer vipLevel) {
		this.vipLevel = vipLevel;
	}
	public Integer getFanNum() {
		return fanNum;
	}
	public void setFanNum(Integer fanNum) {
		this.fanNum = fanNum;
	}
	public Integer getFollowNum() {
		return followNum;
	}
	public void setFollowNum(Integer followNum) {
		this.followNum = followNum;
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
	public Integer getDarenLevel() {
		return darenLevel;
	}
	public void setDarenLevel(Integer darenLevel) {
		this.darenLevel = darenLevel;
	}
	
}
