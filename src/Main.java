import java.util.ArrayList;
import java.util.Scanner;

public class Main{
	public static void main(String[] args) throws Exception{
		ArrayList<int[]> list = new ArrayList<>();
		ArrayList<int[]> task2 = new ArrayList<>();
		ArrayList<int[]> task3 = new ArrayList<>();
		Scanner in = new Scanner(System.in);
		System.out.print("Input r: ");
		int r = in.nextInt();//степень порождающего многочлена
		System.out.print("Input k: ");
		int k = in.nextInt();//длина сообщения
		System.out.print("Input d: ");
		int d = in.nextInt();
		double p = 0.2;//значение ошибки для вычисления верхней границы ошибки
		double []p_bit = {0, 0.1, 0.2, 0.3, 0.4, 0.5, 0.6, 0.7, 0.8, 0.9, 1};//значения ошибки на бит для вычисления точного значения ошибки
		double []pe = new double[p_bit.length];
		double []p_p = new double[p_bit.length];
		int []g = new int[r + 1];
		System.out.print("Input g: ");
		for(int i = 0; i < k; i++){
			g[i] = in.nextInt();
			if(g[i] > 1) g[i] %= 2;
			if(g[i] < 0 && g[i] > -2) g[i] *= -1;
			if(g[i] < -2){
				g[i] *= -1;
				g[i] %= 2;
			}
		}
		int []m = new int[k];
		System.out.print("Input m: ");
		for(int i = 0; i < k; i++){
			m[i] = in.nextInt();
			if(m[i] > 1) m[i] %= 2;
			if(m[i] < 0 && m[i] > -2) m[i] *= -1;
			if(m[i] < -2){
				m[i] *= -1;
				m[i] %= 2;
			}
		}
		Cod_Dec cod_dec = new Cod_Dec(r, k, d);
		cod_dec.create_g(g);
		cod_dec.create_m(m);
		cod_dec.create_c();
		cod_dec.create_a();
		cod_dec.create_e();
		cod_dec.create_b();
		cod_dec.sindrom();
		int len = (int)Math.pow(2, k);
		Cod_Dec []c = new Cod_Dec[len];
		for(int i = 1; i < len; i++){//составление всех возможных сообщений для длины k
			c[i] = new Cod_Dec(r, k, d);
			int []m1 = new int[k];
			Integer number = new Integer(i);
			String num = Integer.toBinaryString(number);
			m1 = toBinary(k, num, i);
			c[i].create_g(g);
			c[i].create_m(m1);
			c[i].create_c();
			c[i].create_a();
			list.add(c[i].get_a());//составление кодовой книги
		}
		print_books_code(list);//вывод кодовой книги
		p = cod_dec.upper_bound(p);//вычисление верхней границы ошибки
		for(int i = 0; i < p_bit.length; i++){
			pe[i] = cod_dec.exact_value(list, p_bit[i]);//вычисление точного значения ошибки
		}	
		cod_dec.print_to_csv(pe, p_bit, "pe", "p_bit", "data.csv");//вывод в csv файл
		//доп.задание l == k
		for(int i = 0; i < p_bit.length; i++){
			p_p[i] = cod_dec.upper_bound(p_bit[i]);
		}
		cod_dec.print_to_csv(pe, p_p, "pe", "p_p", "task1.csv");
		//доп.задание l < k
		int l = k - 1;
		len = (int)Math.pow(2, l);
		Cod_Dec []q = new Cod_Dec[len];
		for(int i = 1; i < len; i++){
			q[i] = new Cod_Dec(r, l, d);
			int []m1 = new int[l];
			Integer number = new Integer(i);
			String num = Integer.toBinaryString(number);
			m1 = toBinary(l, num, i);
			q[i].create_g(g);
			q[i].create_m(m1);
			q[i].create_c();
			q[i].create_a();
			task2.add(q[i].get_a());
		}
		print_books_code(task2);
		for(int i = 0; i < p_bit.length; i++){
			pe[i] = q[1].exact_value(task2, p_bit[i]);
		}
		for(int i = 0; i < p_bit.length; i++){
			p_p[i] = q[1].upper_bound(p_bit[i]);
		}
		cod_dec.print_to_csv(pe, p_p, "pe", "p_p", "task2.csv");
		//доп.задание l > k
		l = k + 4;
		len = (int)Math.pow(2, l);
		Cod_Dec []t = new Cod_Dec[len];
		for(int i = 1; i < len; i++){
			t[i] = new Cod_Dec(r, l, d);
			int []m1 = new int[l];
			Integer number = new Integer(i);
			String num = Integer.toBinaryString(number);
			m1 = toBinary(l, num, i);
			t[i].create_g(g);
			t[i].create_m(m1);
			t[i].create_c();
			t[i].create_a();
			task3.add(t[i].get_a());
		}
		print_books_code(task3);
		for(int i = 0; i < p_bit.length; i++){
			pe[i] = t[1].exact_value(task3, p_bit[i]);
		}
		for(int i = 0; i < p_bit.length; i++){
			p_p[i] = t[1].upper_bound(p_bit[i]);
		}
		cod_dec.print_to_csv(pe, p_p, "pe", "p_p", "task3.csv");
	}

	public static void print_books_code(ArrayList<int[]> list){//вывод кодовой книги на экран
		for(int i = 0; i < list.size(); i++){
			int []array = list.get(i);
			System.out.print("array" + i + " = (");
			for(int j = 0; j < array.length; j++){
				System.out.print(array[j]);
			}
			System.out.println(")");
		}
	}

	public static int[] toBinary(int size, String num, int i){//перевод числа в 2 систему счисления
		int []m1 = new int[size];
		if(i < 2){
			m1[size - 1] = (int)num.charAt(0) % 2;//charAt - метод, который возвращает символ по индексу из строки; если 0 преобразовать к int получится число 48, 1 - 49; поэтому я беру число по модулю 2
		}
		if(i >= 2 && i < 4){
			m1[size - 2] = (int)num.charAt(0) % 2;
			m1[size - 1] = (int)num.charAt(1) % 2;
		}
		if(i >= 4 && i < 8){
			m1[size - 3] = (int)num.charAt(0) % 2;
			m1[size - 2] = (int)num.charAt(1) % 2;
			m1[size - 1] = (int)num.charAt(2) % 2;
		}
		if(i >= 8 && i < 16){
			m1[size - 4] = (int)num.charAt(0) % 2;
			m1[size - 3] = (int)num.charAt(1) % 2;
			m1[size - 2] = (int)num.charAt(2) % 2;
			m1[size - 1] = (int)num.charAt(3) % 2;
		}
		if(i >= 16 && i < 32){
			m1[size - 5] = (int)num.charAt(0) % 2;
			m1[size - 4] = (int)num.charAt(1) % 2;
			m1[size - 3] = (int)num.charAt(2) % 2;
			m1[size - 2] = (int)num.charAt(3) % 2;
			m1[size - 1] = (int)num.charAt(4) % 2;
		}
		if(i >= 32 && i < 64){
			m1[size - 6] = (int)num.charAt(0) % 2;
			m1[size - 5] = (int)num.charAt(1) % 2;
			m1[size - 4] = (int)num.charAt(2) % 2;
			m1[size - 3] = (int)num.charAt(3) % 2;
			m1[size - 2] = (int)num.charAt(4) % 2;
			m1[size - 1] = (int)num.charAt(5) % 2;
		}
		if(i >= 64 && i < 128){
			m1[size - 7] = (int)num.charAt(0) % 2;
			m1[size - 6] = (int)num.charAt(1) % 2;
			m1[size - 5] = (int)num.charAt(2) % 2;
			m1[size - 4] = (int)num.charAt(3) % 2;
			m1[size - 3] = (int)num.charAt(4) % 2;
			m1[size - 2] = (int)num.charAt(5) % 2;
			m1[size - 1] = (int)num.charAt(6) % 2;
		}
		if(i >= 128 && i < 256){
			m1[size - 8] = (int)num.charAt(0) % 2;
			m1[size - 7] = (int)num.charAt(1) % 2;
			m1[size - 6] = (int)num.charAt(2) % 2;
			m1[size - 5] = (int)num.charAt(3) % 2;
			m1[size - 4] = (int)num.charAt(4) % 2;
			m1[size - 3] = (int)num.charAt(5) % 2;
			m1[size - 2] = (int)num.charAt(6) % 2;
			m1[size - 1] = (int)num.charAt(7) % 2;
		}
		return m1;
	}
}