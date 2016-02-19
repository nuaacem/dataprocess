package nuaa.ggx.pos.dataprocess.action.impl;

import javax.annotation.Resource;

import nuaa.ggx.pos.dataprocess.action.interfaces.IJob;
import nuaa.ggx.pos.dataprocess.service.interfaces.IYeLdaService;
import nuaa.ggx.pos.dataprocess.util.Constants;

public class YeLdaModelTrainJob implements IJob{

	@Resource
	IYeLdaService yeLdaService;
	
	@Override
	public void execute() {
		Constants.LDA_TRAIN_POOL.execute(new TrainJob());
		yeLdaService.doTrain("data.txt");
	}

	class TrainJob implements Runnable {

		@Override
		public void run() {
			yeLdaService.doTrain("data.txt");
		}
		
	}
}
