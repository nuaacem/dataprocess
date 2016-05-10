package nuaa.ggx.pos.dataprocess.dao.interfaces;

import nuaa.ggx.pos.dataprocess.model.TSentimentWordPole;

public interface ISentimentWordPoleDao extends IBaseDao<TSentimentWordPole> {
	public int getWordPole(String word);
}

