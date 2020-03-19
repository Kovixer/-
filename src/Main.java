import java.util.ArrayList;

public class Main{
	public static void main(String[] args) throws Exception{
		ArrayList<int[]> list = new ArrayList<>();
		ArrayList<int[]> task2 = new ArrayList<>();
		ArrayList<int[]> task3 = new ArrayList<>();
		int r = 3;//степень порождающего многочлена
		int k = 4;//длина сообщения
		int d = 3;
		double p = 0.2;//значение ошибки для вычисления верхней границы ошибки
		double []p_bit = {0, 0.1, 0.2, 0.3, 0.4, 0.5, 0.6, 0.7, 0.8, 0.9, 1};//значения ошибки на бит для вычисления точного значения ошибки
		double []pe = new double[p_bit.length];
		double []p_p = new double[p_bit.length];
		int []m = new int[k];
		m[0] = 1;
		m[1] = 1;
		m[2] = 0;
		m[3] = 0;
		Cod_Dec cod_dec = new Cod_Dec(r, k, d);
		cod_dec.set_g(0, 1);
		cod_dec.set_g(1, 1);
		cod_dec.set_g(2, 0);
		cod_dec.set_g(3, 1);
		cod_dec.print_g();
		cod_dec.create_m(m);
		cod_dec.create_c();
		cod_dec.create_a();
		cod_dec.create_e();
		cod_dec.create_b();
		cod_dec.sindrom();
		Cod_Dec []c = new Cod_Dec[16];
		for(int i = 1; i < 16; i++){//составление всех возможных сообщений для длины k
			c[i] = new Cod_Dec(r, k, d);
			int []m1 = new int[k];
			Integer number = new Integer(i);
			String num = Integer.toBinaryString(number);
			if(i < 2){
				m1[0] = 0;
				m1[1] = 0;
				m1[2] = 0;
				m1[3] = (int)num.charAt(0) % 2;
			}
			if(i >= 2 && i < 4){
				m1[0] = 0;
				m1[1] = 0;
				m1[2] = (int)num.charAt(1) % 2;
				m1[3] = (int)num.charAt(0) % 2;
			}
			if(i >= 4 && i < 8){
				m1[1] = (int)num.charAt(2) % 2;
				m1[2] = (int)num.charAt(1) % 2;
				m1[3] = (int)num.charAt(0) % 2;
				m1[0] = 0;
			}
			if(i >= 8 && i < 16){
				m1[0] = (int)num.charAt(3) % 2;
				m1[1] = (int)num.charAt(2) % 2;
				m1[2] = (int)num.charAt(1) % 2;
				m1[3] = (int)num.charAt(0) % 2;
			}
			c[i].set_g(0, 1);
			c[i].set_g(1, 1);
			c[i].set_g(2, 0);
			c[i].set_g(3, 1);
			c[i].print_g();
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
		Cod_Dec []q = new Cod_Dec[8];
		for(int i = 1; i < 8; i++){
			q[i] = new Cod_Dec(r, l, d);
			int []m1 = new int[l];
			Integer number = new Integer(i);
			String num = Integer.toBinaryString(number);
			if(i < 2){
				m1[0] = 0;
				m1[1] = 0;
				m1[2] = (int)num.charAt(0) % 2;
			}
			if(i >= 2 && i < 4){
				m1[0] = 0;
				m1[1] = (int)num.charAt(1) % 2;
				m1[2] = (int)num.charAt(0) % 2;
			}
			if(i >= 4 && i < 8){
				m1[0] = (int)num.charAt(2) % 2;
				m1[1] = (int)num.charAt(1) % 2;
				m1[2] = (int)num.charAt(0) % 2;
			}
			q[i].set_g(0, 1);
			q[i].set_g(1, 1);
			q[i].set_g(2, 0);
			q[i].set_g(3, 1);
			q[i].print_g();
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
		l = k + 1;
		Cod_Dec []t = new Cod_Dec[32];
		for(int i = 1; i < 32; i++){
			t[i] = new Cod_Dec(r, l, d);
			int []m1 = new int[l];
			Integer number = new Integer(i);
			String num = Integer.toBinaryString(number);
			if(i < 2){
				m1[0] = 0;
				m1[1] = 0;
				m1[2] = 0;
				m1[3] = 0;
				m1[4] = (int)num.charAt(0) % 2;
			}
			if(i >= 2 && i < 4){
				m1[0] = 0;
				m1[1] = 0;
				m1[2] = 0;
				m1[3] = (int)num.charAt(1) % 2;
				m1[4] = (int)num.charAt(0) % 2;
			}
			if(i >= 4 && i < 8){
				m1[0] = 0;
				m1[1] = 0;
				m1[2] = (int)num.charAt(2) % 2;
				m1[3] = (int)num.charAt(1) % 2;
				m1[4] = (int)num.charAt(0) % 2;
			}
			if(i >= 8 && i < 16){
				m1[0] = 0;
				m1[1] = (int)num.charAt(3) % 2;
				m1[2] = (int)num.charAt(2) % 2;
				m1[3] = (int)num.charAt(1) % 2;
				m1[4] = (int)num.charAt(0) % 2;
			}
			if(i >= 16 && i < 32){
				m1[0] = (int)num.charAt(4) % 2;
				m1[1] = (int)num.charAt(3) % 2;
				m1[2] = (int)num.charAt(2) % 2;
				m1[3] = (int)num.charAt(1) % 2;
				m1[4] = (int)num.charAt(0) % 2;
			}
			t[i].set_g(0, 1);
			t[i].set_g(1, 1);
			t[i].set_g(2, 0);
			t[i].set_g(3, 1);
			t[i].print_g();
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
}