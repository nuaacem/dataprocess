package nuaa.ggx.pos.dataprocess.action.impl;

import java.util.List;

import javax.annotation.Resource;

import org.apache.log4j.Logger;

import nuaa.ggx.pos.dataprocess.action.interfaces.IJob;
import nuaa.ggx.pos.dataprocess.service.interfaces.IKeywordService;
import nuaa.ggx.pos.dataprocess.service.interfaces.ISubjectManageService;
import nuaa.ggx.pos.dataprocess.service.interfaces.ITrendDayManageService;
import nuaa.ggx.pos.dataprocess.service.interfaces.ITrendHourManageService;
import nuaa.ggx.pos.dataprocess.service.interfaces.IWeiboContentSegService;
import nuaa.ggx.pos.dataprocess.util.Constants;

public class TrendAnalyseJob implements IJob{

private static Logger log = Logger.getLogger(TrendAnalyseJob.class);
	
	@Resource
	private IWeiboContentSegService iWeiboContentSegService;
	
	@Resource
	private ITrendHourManageService iTrendHourManageService;
	
	@Resource
	private ITrendDayManageService iTrendDayManageService;
	
	@Resource
	private IKeywordService iKeywordService;
	
	@Resource
	private ISubjectManageService iSubjectManageService;
	
	public void execute(){
		Constants.WEIBO_CRAWLER_POOL.execute(new ExecuteWork());
	}
	
	class ExecuteWork implements Runnable{
		
		@Override
		public void run() {
			try {
				while (iWeiboContentSegService.updateWeioboContentSeg()) {
				}
				List<String> keywordsName = iKeywordService.getAllKeywordsName();
				for (String keywordname : keywordsName) {
					iTrendHourManageService.updateTrendHourByKeyword(keywordname);
				}
				for (String keywordname : keywordsName) {
					iTrendDayManageService.updateTrendDayByKeyword(keywordname);
				}
				iTrendHourManageService.updateTrendHourBySubjects();
				iTrendDayManageService.updateTrendDayBySubjects();
				iKeywordService.updateKeywordsNum();
				iSubjectManageService.updateSubjectsNum();
			} catch (RuntimeException e) {
				e.printStackTrace();
			}
		}		
	}

}
