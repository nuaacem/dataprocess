package rivers.yeah.research.expriment;

import org.junit.Test;

import rivers.yeah.research.yelda.LdaArgs;
import rivers.yeah.research.yelda.Trainer;
import rivers.yeah.research.yelda.SimlarityAnalyser;

public class LdaTest {

	@Test
	public void similarityCompute() {
		SimlarityAnalyser simlarityAnalyser = new SimlarityAnalyser();
		simlarityAnalyser.init("./models/0606", "./models/0615/22");
		simlarityAnalyser.computeSimilarity();
//		simlarityAnalyser.computeSimilarity(20);
	}
	
	@Test
	public void ldaLearn() {
		String configPath = "./cfg/yelda.properties";
		LdaArgs option = LdaArgs.initLdaArgs(configPath);
		if (!option.initflag) {
			System.out.println("Args init failed!");
			return;
		}

		Trainer learner = new Trainer();
		if (!learner.init(option)) {
			System.out.println("Learner init failed!");
			return;
		}
		learner.train();
	}

	@Test
	public void continualTopic() {
		String configPath = "./cfg/yelda.properties";
		LdaArgs ldaArgs = LdaArgs.initLdaArgs(configPath);
		Trainer learner = null;
		for (int i = 2; i <= 3; i++) {
			System.out.println("topics:" + i * 10);
			ldaArgs.increaseNtopic(i);
			learner = new Trainer();
			learner.init(ldaArgs);
			learner.train();
		}
	}
}
