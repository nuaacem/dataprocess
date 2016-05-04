package nuaa.ggx.pos.dataprocess.service.impl;

import java.util.List;

import nuaa.ggx.pos.dataprocess.dao.interfaces.ITrendDayDao;
import nuaa.ggx.pos.dataprocess.model.TTrendDay;
import nuaa.ggx.pos.dataprocess.service.interfaces.ITrendDayManageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("TrendDayManageService")
public class TrendDayManageService implements ITrendDayManageService{
    
	@Autowired
	private ITrendDayDao trenddayDao;
	
	@Override
	public List<TTrendDay> findByTypeAndOid(Short type, Integer oid) {
		return trenddayDao.findByTypeAndOid(type, oid);
	}

}
