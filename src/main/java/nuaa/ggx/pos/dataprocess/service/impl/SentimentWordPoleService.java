package nuaa.ggx.pos.dataprocess.service.impl;

import nuaa.ggx.pos.dataprocess.dao.interfaces.ISentimentWordPoleDao;
import nuaa.ggx.pos.dataprocess.service.interfaces.ISentimentWordPoleService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("SentimentWordPoleService")
public class SentimentWordPoleService implements ISentimentWordPoleService{

	@Autowired
	private ISentimentWordPoleDao sentimentWordPoleDao;

	@Override
	public int getWordsPole(String[] words) {
		int pole = 0;
		for (int i=0; i<words.length; i++) {
			pole = pole + sentimentWordPoleDao.getWordPole(words[i]);
		}
		return pole;
	}
}
