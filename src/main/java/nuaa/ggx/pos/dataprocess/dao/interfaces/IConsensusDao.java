package nuaa.ggx.pos.dataprocess.dao.interfaces;

import java.util.List;

import nuaa.ggx.pos.dataprocess.model.TConsensus;

public interface IConsensusDao extends IBaseDao<TConsensus> {
	public List<TConsensus> getConsensusesWhereStateAreNull();
}
