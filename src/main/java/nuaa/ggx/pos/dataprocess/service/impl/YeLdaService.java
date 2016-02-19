package nuaa.ggx.pos.dataprocess.service.impl;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import nuaa.ggx.pos.dataprocess.dao.interfaces.IWeiboDao;
import nuaa.ggx.pos.dataprocess.lda.lda.LdaArgs;
import nuaa.ggx.pos.dataprocess.lda.lda.Trainer;
import nuaa.ggx.pos.dataprocess.model.Weibo;
import nuaa.ggx.pos.dataprocess.segword.nlpir.CallNLPIR;
import nuaa.ggx.pos.dataprocess.service.interfaces.IYeLdaService;

@Service("YeLdaService")
public class YeLdaService implements IYeLdaService {

	@Resource
	IWeiboDao weiboDao;

	String configPath;
	
	public void doTrain(String filename) {
		
		try {
			LdaArgs option = LdaArgs.initLdaArgs(configPath);
			option.dir = generateCorpusFromDB(filename);;
			option.dfile = filename;
			option.est = 1;
			
			if (!option.initflag){
				System.out.println("Args init failed!");
				return;
			}
			
			Trainer trainer = new Trainer();
			
			if (!trainer.init(option)) {
				System.out.println("Trainer init failed!");
				return;
			}
			
			trainer.train();
			
		} catch (ParseException e1) {
			e1.printStackTrace();
		} catch (IOException e1) {
			e1.printStackTrace();
		} catch (Exception e){
			System.out.println("Error in main: " + e.getMessage());
			e.printStackTrace();
		}
		
	}

	private String generateCorpusFromDB(String filename) throws ParseException, IOException {

		DateFormat df = new SimpleDateFormat("yyyy-mm-dd");
		String endTime = df.format(new Date());
		Date date = new Date(df.parse(endTime).getTime() - 24 * 60 * 60);
		String startTime = df.format(date);
		List<Weibo> weiboList = weiboDao.getWeibosBetweenTime(startTime, endTime);

		File outDir = new File("./files/" + startTime);
		if (!outDir.exists()) {
			outDir.mkdirs();
		}

		File inFile = new File(outDir, "rawData.txt");
		OutputStreamWriter out = new OutputStreamWriter(new FileOutputStream(inFile), "UTF-8");

		for (Weibo weibo : weiboList) {
			out.write(weibo.getContent());
			out.write("\r\n");
		}

		out.close();

		List<String> segStrings = CallNLPIR.segWords(inFile.getPath(), 0, true);
		out = new OutputStreamWriter(new FileOutputStream(new File(outDir, filename)), "UTF-8");

		for (int i = 0, l = weiboList.size(); i < l; ++i) {
			
			if (segStrings.get(i).equals("")) {
				continue;
			}
			
			out.write(weiboList.get(i).getDomain() + "\t" + segStrings.get(i));
			out.write("\r\n");
		}
		
		out.close();
		return outDir.getPath();
	}
}
