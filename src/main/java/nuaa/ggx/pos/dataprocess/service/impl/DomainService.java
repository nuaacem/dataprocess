package nuaa.ggx.pos.dataprocess.service.impl;

import java.util.ArrayList;
import java.util.List;

import nuaa.ggx.pos.dataprocess.dao.impl.DomainDao;
import nuaa.ggx.pos.dataprocess.model.Domain;


public class DomainService {
	
	private static final DomainDao domainDao = new DomainDao();
	
	public static void addDomain(Domain domain) {		
		domainDao.saveOrUpdate(domain);
	}
	
	public static List<Domain> getDomains() {
		return domainDao.getAll();
	}
	
	public static Domain getByDomainId(String domainId) {
		return domainDao.getByDomainId(domainId);
	}
	
	public static List<String> getCategories() {
		List<String> categories = new ArrayList<String>();
		List<Domain> domains = domainDao.getAll();
		for (Domain domain : domains) {
			categories.add(domain.getDomainId());
		}
		return categories;
	}
	
	public static List<String> getDomainNames() {
		List<String> categories = new ArrayList<String>(40);
		List<Domain> domains = domainDao.getAll();
		for (Domain domain : domains) {
			categories.add(domain.getDomain());
		}
		return categories;
	}
}
