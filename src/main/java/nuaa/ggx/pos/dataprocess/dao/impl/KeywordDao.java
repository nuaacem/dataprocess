package nuaa.ggx.pos.dataprocess.dao.impl;

import nuaa.ggx.pos.dataprocess.dao.interfaces.IKeywordDao;
import nuaa.ggx.pos.dataprocess.model.TKeyword;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;

@Repository("KeywordDao")
public class KeywordDao extends BaseDao<TKeyword> implements IKeywordDao{
	
	private static Logger log = Logger.getLogger(KeywordDao.class);

}


	