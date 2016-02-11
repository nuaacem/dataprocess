package rivers.yeah.research.expriment;

public class Calculate {
	static double Back(String input, int location) {
		char[] all = input.toCharArray();
		for (int i = location; i < all.length; i++) {
			if (all[i] == '+' || all[i] == '-' || all[i] == '/'
					|| all[i] == '*') {
				char tmp[] = new char[i];
				int count = 0;
				for (int j = location; j < i; j++) {
					tmp[count] = all[j];
					count++;
				}
				location = i;
				if (all[i] == '+')
					return Double.parseDouble(String.valueOf(tmp).trim())
							+ Back(input, location + 1);
				if (all[i] == '-')
					return Double.parseDouble(String.valueOf(tmp).trim())
							- Back(input, location + 1);
				if (all[i] == '*')
					return Double.parseDouble(String.valueOf(tmp).trim())
							* Back(input, location + 1);
				if (all[i] == '/')
					return Double.parseDouble(String.valueOf(tmp).trim())
							/ Back(input, location + 1);
			} else {
				if (location != 0) {
					char tmp[] = new char[all.length - location];
					int count = 0;
					boolean flag = false;
					for (int j = location; j < all.length; j++) {
						tmp[count] = all[j];
						count++;
						if (all[j] == '+' || all[j] == '-' || all[j] == '/'
								|| all[j] == '*') {
							flag = false;
							break;
						}
						flag = true;
						// System.out.println("all["+j+"]:"+all[j]);
					}
					if (flag) {
						return Integer.parseInt(String.valueOf(tmp));
					}
				}

			}

		}
		return 0;
	}
	public static void main(String[] args) {
		System.out.println(Back("10-4-5", 0));
	}
}
