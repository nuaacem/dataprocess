package rivers.yeah.research.expriment;

import java.util.Random;

import org.junit.Test;

public class GenRandom {

	@Test
	public void testNormRand() {
		Random random = new Random();
		System.out.println((int)(90 + random.nextGaussian() + 2.5));
	}
	
	public double Norm_rand(double miu, double sigma2) {
		double N = 12;
		double x = 0, temp = N;
		do {
			x = 0;
			for (int i = 0; i < N; i++) {
				x = x + (Math.random());
			}
			x = (x - temp / 2) / (Math.sqrt(temp / 12));
			x = miu + x * Math.sqrt(sigma2);
		} while (x <= 0); // 在此我把小于0的数排除掉了
		return x;
	}
}
