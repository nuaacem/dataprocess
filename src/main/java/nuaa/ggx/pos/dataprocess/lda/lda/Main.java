package nuaa.ggx.pos.dataprocess.lda.lda;

import java.io.File;

import org.junit.Test;

public class Main {
	
	@Test
	public void continousTrain() {
		String configPath = "./cfg/yelda.properties";
		for (int i = 1; i < 15; i++) {
			LdaArgs option = LdaArgs.initLdaArgs(configPath);
			option.dfile = i + ".txt";
			option.modelName = "model-final-" + i;
			Trainer trainer = new Trainer();
			if (!trainer.init(option)) {
				System.out.println("Trainer init failed!");
				return;
			}
			trainer.trnModel.data.localVoc.writeWordMap(option.dir + File.separator + "wordmap" + i + ".txt");
			trainer.train();
		}
		
	}
	
	public static void main(String[] args) {
		String configPath = "./cfg/yelda.properties";
		LdaArgs option = LdaArgs.initLdaArgs(configPath);
		try {
			if (!option.initflag){
				System.out.println("Args init failed!");
				return;
			}
			
			if (1 == option.est || 1 == option.estc){
				Trainer trainer = new Trainer();
				if (!trainer.init(option)) {
					System.out.println("Trainer init failed!");
					return;
				}
				trainer.train();
			}
			else if (1 == option.inf){
				Inferencer inferencer = new Inferencer(option);
				if (!inferencer.init()) {
					System.out.println("Inferencer init failed!");
					return;
				}
				
				inferencer.inference();
			
//				for (int i = 0; i < newModel.phi.length; ++i){
//					//phi: K * V
//					System.out.println("-----------------------\ntopic" + i  + " : ");
//					for (int j = 0; j < 10; ++j){
//						System.out.println(inferencer.globalVoc.id2word.get(j) + "\t" + newModel.phi[i][j]);
//					}
//				}
			}
		}
		catch (Exception e){
			System.out.println("Error in main: " + e.getMessage());
			e.printStackTrace();
			return;
		}
	}
}
