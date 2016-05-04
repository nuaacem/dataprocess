package nuaa.ggx.pos.dataprocess.service.impl;

import java.util.List;

import nuaa.ggx.pos.dataprocess.dao.interfaces.ITrendHourDao;
import nuaa.ggx.pos.dataprocess.model.TTrendHour;
import nuaa.ggx.pos.dataprocess.service.interfaces.ITrendHourManageService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("TrendHourManageService")
public class TrendHourManageService implements ITrendHourManageService{
    
	@Autowired
	private ITrendHourDao trendhourDao;
	
	@Override
	public List<TTrendHour> findByTypeAndOid(Short type, Integer oid) {
		return trendhourDao.findByTypeAndOid(type, oid);
	}

}
