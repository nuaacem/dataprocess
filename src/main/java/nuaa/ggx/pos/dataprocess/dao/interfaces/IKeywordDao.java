package nuaa.ggx.pos.dataprocess.dao.interfaces;

import java.util.List;

import nuaa.ggx.pos.dataprocess.model.TKeyword;

public interface IKeywordDao extends IBaseDao<TKeyword> {
	public int getIdByKeyword(String keyword);
	public List<String> getKeywordsName();
}

