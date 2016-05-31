package cn.cem.Bean;

import java.util.List;

/**
 * @author HXF
 * user Bean
 */
public class User {
	private String uid;
	private String name;
	private List<String> commentUids;
	
	public String getUid() {
		return uid;
	}
	public void setUid(String uid) {
		this.uid = uid;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public List<String> getCommentUids() {
		return commentUids;
	}
	public void setCommentUids(List<String> commentUids) {
		this.commentUids = commentUids;
	}
	
}
