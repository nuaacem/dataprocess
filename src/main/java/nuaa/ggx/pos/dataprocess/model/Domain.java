package nuaa.ggx.pos.dataprocess.model;

import java.sql.Timestamp;
import java.util.List;

public class Domain {
	private int id;
	private String domainId;
	private String domain;
	private List<Integer> topics;
	private Timestamp collectTime;
	
	public Domain() {
		
	}
	
	public Domain(String domainId, String domain,Timestamp collectTime) {
		this.domainId = domainId;
		this.domain = domain;
		this.collectTime = collectTime;
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
	
	public List<Integer> getTopics() {
		return topics;
	}

	public void setTopics(List<Integer> topics) {
		this.topics = topics;
	}

	public Timestamp getCollectTime() {
		return collectTime;
	}
	public void setCollectTime(Timestamp collectTime) {
		this.collectTime = collectTime;
	}
	
}
