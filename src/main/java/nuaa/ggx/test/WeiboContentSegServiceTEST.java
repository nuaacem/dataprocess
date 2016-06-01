package nuaa.ggx.test;

import nuaa.ggx.pos.dataprocess.service.interfaces.IKeywordService;
import nuaa.ggx.pos.dataprocess.service.interfaces.ISubjectManageService;
import nuaa.ggx.pos.dataprocess.service.interfaces.ITrendDayManageService;
import nuaa.ggx.pos.dataprocess.service.interfaces.ITrendHourManageService;
import nuaa.ggx.pos.dataprocess.service.interfaces.IWeiboContentSegService;

import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations="springcontext-config.xml")
public class WeiboContentSegServiceTEST extends AbstractJUnit4SpringContextTests {

	@Autowired
	private IWeiboContentSegService iWeiboContentSegService;
	
	@Autowired
	private ITrendHourManageService iTrendHourManageService;
	
	@Autowired
	private ITrendDayManageService iTrendDayManageService;
	
	@Autowired
	private IKeywordService iKeywordService;
	
	@Autowired
	private ISubjectManageService iSubjectManageService;
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@Test
	public void test() {
		long startTime = System.currentTimeMillis();
		/*try {
			CallNLPIR.loadUserDict("userdic.txt", true);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
		iWeiboContentSegService.saveWeioboContentSeg();
		long endTime = System.currentTimeMillis();
		System.out.println(endTime - startTime + "cbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbb");
		/*List<String> keywordsName = iKeywordService.getAllKeywordsName();
		for (String keywordname : keywordsName) {
			iTrendHourManageService.updateTrendHourByKeyword(keywordname);
		}
		for (String keywordname : keywordsName) {
			iTrendDayManageService.updateTrendDayByKeyword(keywordname);
		}
		iTrendHourManageService.updateTrendHourBySubjects();
		iTrendDayManageService.updateTrendDayBySubjects();
		iKeywordService.updateKeywordsNum();
		iSubjectManageService.updateSubjectsNum();*/
	}

}
