package nuaa.ggx.pos.dataprocess.util;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Constants {
	
	public static ExecutorService LDA_TRAIN_POOL = Executors.newFixedThreadPool(10);
}
