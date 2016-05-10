package nuaa.ggx.test;

import java.util.HashMap;
import java.util.Iterator;

import nuaa.ggx.pos.dataprocess.service.interfaces.IKeywordService;
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
	IWeiboContentSegService iWeiboContentSegService;
	
	@Autowired
	ITrendHourManageService iTrendHourManageService;
	
	@Autowired
	IKeywordService iKeywordService;
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@Test
	public void test() {
		while (iWeiboContentSegService.updateWeioboContentSeg()) {
		}
		/*List<String> keywordsName = iKeywordService.getKeywordsName();
		for (String keywordname : keywordsName) {
			iTrendHourManageService.updateTrendHourKeyword(keywordname);
		}*/
		iTrendHourManageService.updateTrendHourKeyword("莆田");
		HashMap<String, Integer[]> hourPoleNum = iWeiboContentSegService.getHourPoleNumByKeyword("莆田");
		for (String key : hourPoleNum.keySet()) {
			System.out.println(hourPoleNum.get(key)[0]);
		}
	}

}
