package nuaa.ggx.pos.dataprocess.model;

public class Weibo {
	
	private int id;
	
	private String content;
	
	private String domain;
	
	private int praise_num;
	
	private int forward_num;
	
	private int cmt_num;

	public Weibo() {
		
	}
	
	public Weibo(int id, String content, String domain) {
		this.id = id;
		this.content = content;
		this.domain = domain;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getDomain() {
		return domain;
	}

	public void setDomain(String domain) {
		this.domain = domain;
	}

	public int getPraise_num() {
		return praise_num;
	}

	public void setPraise_num(int praise_num) {
		this.praise_num = praise_num;
	}

	public int getForward_num() {
		return forward_num;
	}

	public void setForward_num(int forward_num) {
		this.forward_num = forward_num;
	}

	public int getCmt_num() {
		return cmt_num;
	}

	public void setCmt_num(int cmt_num) {
		this.cmt_num = cmt_num;
	}
	
}
