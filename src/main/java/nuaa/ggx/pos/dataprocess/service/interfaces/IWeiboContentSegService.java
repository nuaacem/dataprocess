package nuaa.ggx.pos.dataprocess.service.interfaces;

import java.util.HashMap;

public interface IWeiboContentSegService {
	public Boolean updateWeioboContentSeg();
	public HashMap<String, Integer[]> getHourPoleNumByKeyword(String keyword);
}