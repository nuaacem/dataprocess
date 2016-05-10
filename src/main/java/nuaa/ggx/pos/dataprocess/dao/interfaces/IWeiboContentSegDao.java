package nuaa.ggx.pos.dataprocess.dao.interfaces;

import java.util.List;

import nuaa.ggx.pos.dataprocess.model.TWeiboContentSeg;

public interface IWeiboContentSegDao extends IBaseDao<TWeiboContentSeg> {
	
	public List<TWeiboContentSeg> getPositiveTWeiboContentSegListByKeyword(String keyword);
	public List<TWeiboContentSeg> getNeutralTWeiboContentSegListByKeyword(String keyword);
	public List<TWeiboContentSeg> getNegativeTWeiboContentSegListByKeyword(String keyword);
	public List<TWeiboContentSeg> getPoleIsNull();
}

