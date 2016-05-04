package nuaa.ggx.pos.dataprocess.service.interfaces;

import java.util.List;

import nuaa.ggx.pos.dataprocess.model.TTrendDay;

public interface ITrendDayManageService {
	public List<TTrendDay> findByTypeAndOid(Short type, Integer oid);
    //public void save(TTrendHour trendhour);
    //public void deleteByTypeAndOid(Short type, Integer oid);
}
