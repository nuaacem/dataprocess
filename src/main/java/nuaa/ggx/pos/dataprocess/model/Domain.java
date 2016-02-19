package nuaa.ggx.pos.dataprocess.model;

import java.sql.Timestamp;

public class Domain {
	
	private int id;
	
	private String domainId;
	
	private String domain;
	
	private Timestamp collectTime;
	
	public Domain() {
		
	}
	
	public Domain(int id, String domain) {
		this.id = id;
		this.domainId = domainId;
		this.domain = domain;
	}

	public String getDomainId() {
		return domainId;
	}

	public void setDomainId(String domainId) {
		this.domainId = domainId;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getId() {
		return id;
	}

	public String getDomain() {
		return domain;
	}
	
	public void setDomain(String domain) {
		this.domain = domain;
	}

	public Timestamp getCollectTime() {
		return collectTime;
	}
	
	public void setCollectTime(Timestamp collectTime) {
		this.collectTime = collectTime;
	}
	
}
