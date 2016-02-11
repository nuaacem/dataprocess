package rivers.yeah.research.expriment;

public class Test {
	int a;
	public static void main(String[] args) {
		String a = new String("a") + "bc";
		String b = "abc";
		String c = new String("abc");
		System.out.println(a.intern() == b);
		System.out.println("abc" == c.intern());
		System.out.println(42l == 42.0f);
	}
}
