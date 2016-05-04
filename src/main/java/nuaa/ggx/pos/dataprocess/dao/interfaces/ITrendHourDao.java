package nuaa.ggx.pos.dataprocess.dao.interfaces;

import java.util.List;

import nuaa.ggx.pos.dataprocess.model.TTrendHour;


public interface ITrendHourDao extends IBaseDao<TTrendHour> {
	public List<TTrendHour> findByTypeAndOid(Short type, Integer oid);
}

