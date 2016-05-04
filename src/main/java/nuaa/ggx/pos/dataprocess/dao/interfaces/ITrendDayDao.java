package nuaa.ggx.pos.dataprocess.dao.interfaces;

import java.util.List;

import nuaa.ggx.pos.dataprocess.model.TTrendDay;


public interface ITrendDayDao extends IBaseDao<TTrendDay> {
	public List<TTrendDay> findByTypeAndOid(Short type, Integer oid);
}

