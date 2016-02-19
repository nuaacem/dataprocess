package nuaa.ggx.pos.dataprocess.dao.interfaces;

import java.util.List;

import nuaa.ggx.pos.dataprocess.model.Weibo;

public interface IWeiboDao {
	
	public List<Weibo> getWeibosBetweenTime(String startTime, String endTime);

}
