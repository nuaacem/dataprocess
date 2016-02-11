package rivers.yeah.research.yelda.mbolda;

import rivers.yeah.research.yelda.Inferencer;
import rivers.yeah.research.yelda.olda.OldaArgs;

public class Main {
	public static void main(String[] args) {
		String configPath = "./cfg/yembolda.properties";
		OldaArgs option = OldaArgs.initOldaArgs(configPath);
		try {
			if (!option.initflag){
				System.out.println("Args init failed!");
				return;
			}
			
			if (1 == option.est || 1 == option.estc){
				MBOTrainer trainer = new MBOTrainer();
				if (!trainer.initMBOTrainer(option)) {
					System.out.println("Trainer init failed!");
					return;
				}
				trainer.trainMBOlda();
			}
			else if (1 == option.inf){
				Inferencer inferencer = new Inferencer(option);
				if (!inferencer.init()) {
					System.out.println("Inferencer init failed!");
					return;
				}
				
				inferencer.inference();
			}
		}
		catch (Exception e){
			System.out.println("Error in main: " + e.getMessage());
			e.printStackTrace();
			return;
		}
	}
}
