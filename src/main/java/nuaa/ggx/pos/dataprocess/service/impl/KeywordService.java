package nuaa.ggx.pos.dataprocess.service.impl;

import java.util.List;

import nuaa.ggx.pos.dataprocess.dao.interfaces.IKeywordDao;
import nuaa.ggx.pos.dataprocess.service.interfaces.IKeywordService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("KeywordService")
public class KeywordService implements IKeywordService{

	@Autowired
	private IKeywordDao iKeywordDao;
	
	@Override
	public List<String> getKeywordsName() {
		return iKeywordDao.getKeywordsName();
	}
}
