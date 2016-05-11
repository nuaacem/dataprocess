package nuaa.ggx.pos.dataprocess.dao.interfaces;

import java.util.List;

import nuaa.ggx.pos.dataprocess.model.TTrendDay;


public interface ITrendDayDao extends IBaseDao<TTrendDay> {
	public List<TTrendDay> findByTypeAndOid(Short type, Integer oid);
	public List<TTrendDay> findByTypeAndOidAndDay(Short type, Integer oid, Integer day);
}

