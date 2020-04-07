import java.util.Scanner;
import java.util.ArrayList;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;

public class Cod_Dec{
	private int []g;
	private int []m;
	private int []c;
	private int []a;
	private int []e;
	private int []b;
	private int r;
	private int k;
	private int n;
	private int d;
	private int e_d;

	public Cod_Dec(int r, int k, int d){//конструктор
		this.r = r;
		g = new int[r + 1];
		this.k = k;
		n = r + k;
		m = new int[k];
		c = new int[r];
		a = new int[n];
		e = new int[n];
		b = new int[n];
		this.d = d;
	}

	public void create_g(int []array){//задает порождающий многочлен(на вход принимается индекс и значние, которое записывается по этому индексу)
		if(array.length == g.length){
			for(int i = 0; i < array.length; i++){
				g[i] = array[i];
			}
			print_array(g, "g");//вызов метода, который выводит многочлен на экран
		}
		else System.out.println("The dimensions don't match");
	}

	public void create_m(int []m1){//создание сообщения(принимается массив который записывается в  массив m)
		if(m1.length == k){
			for(int i = 0; i < k; i++){
				this.m[i] = m1[i];
			}
		}
		else{
			System.out.println("Error size of vector m");
		}
		print_array(m, "m");
	}

	public void create_c(){//создание многочлена с
		int []x = new int[r + 1];
		x[0] = 1;
		for(int i = 1; i < x.length; i++){
			x[i] = 0;
		}
		int []tmp = multiply(m, x);//метод для перемножения многочленов
		//print_array(tmp, "m(x) * x^r");
		c = mod(tmp);//метод деления многочленов
		//print_array(c, "c");
	}

	private int[] multiply(int []m1, int []m2){//перемножение многочленов
		int []res = new int[m1.length + m2.length - 1];
		for(int i = 0; i < m1.length; i++){
			for(int j = 0; j < m2.length; j++){
				res[i + j] += m1[i] * m2[j];
			}
		}
		return res;
	}

	private int[] mod(int []tmp){//деление многочленов
		int tmp_deg = tmp.length - 1;
		int i = 0;
		int zero = 0;
		int []tmp1 = new int[1];
		tmp1[0] = 0;
		while(tmp_deg >= r){
			int deg = tmp_deg - r;//понижение степени многочлена
			int []x = new int[deg + 1];
			x[0] = 1;
			//print_array(x, "x^");
			int []t = multiply(x, g);//перемножение многочленов
			//print_array(t, "x^ * g(x)");
			tmp = minus(tmp, t);//метод вычитания многочленов
			//print_array(tmp, "(m(x) * x^) - (x^ * g(x))");
			zero = count_zero(tmp);//подсчет количества нулей в векторе
			if(zero == tmp.length){//если многочлен стал нулевым то выходим из деления
				return tmp1;
			}
			else{
				tmp = cut0(tmp);//убираем все нули, которые стоят в начале
				//print_array(tmp, "(m(x) * x^) - (x^ * g(x))_cut");
				tmp_deg = tmp.length - 1;
			}
		}
		return tmp;
	}

	private int[] minus(int []m1, int []m2){//разница между двумя многочленами
		int []res = new int[m1.length];
		for(int i = 0; i < m1.length; i++){
			res[i] = m1[i] - m2[i];
			if(res[i] == -1) res[i] = 1;
		}
		return res;
	}

	private int count_zero(int []array){//подсчет количества нулей в многочлене
		int count = 0;
		for(int i = 0; i < array.length; i++){
			if(array[i] == 0) count++;
		}
		return count;
	}

	private int[] cut0(int []array){//убираем все нули пока не встретим 1
		int count = 0;
		int i = 0;
		if(array[i] != 1){
			while(array[i] != 1){
				count++;
				i++;
			}
		}
		int []array1 = new int[array.length - count];
		for(int j = 0; j < array1.length; j++){
			array1[j] = array[j + count];
		}
		return array1;
	}

	public void create_a(){//создание кодового слова
		int i = 0;
		int j = 0;
		int tmp = a.length - c.length - m.length;
		while(i < m.length){//в начало копируется вектор m
			a[i] = m[i];
			i++;
		}
		while(tmp > 0){//если длина вектора а больше суммы с и m заполняем нулями
			a[i] = 0;
			i++;
			tmp--;
		}
		while(j < c.length){//в конец копируется вектор с
			a[i] = c[j];
			i++;
			j++;
		}
		print_array(a, "a");
	}

	public int[] get_a(){//возвращаем кодовое слово
		return a;
	}

	public void create_e(){//создание вектора ошибок
		Scanner s = new Scanner(System.in);
		System.out.print("Input e: ");
		for(int i = 0; i < e.length; i++){//ввод вектора ошибок с клавиатуры
			e[i] = s.nextInt();
			e[i] %= 2;
		}
		print_array(e, "e");//вывод вектора на экран
	}

	public void create_b(){//создание сообщения, которое принимает декодер
		for(int i = 0; i < b.length; i++){
			b[i] = (a[i] + e[i]) % 2;
		}
		print_array(b, "b");//вывод вектора на экран
	}

	public void sindrom(){//вычисление синдрома
		int []s = mod(b);
		print_array(s, "s");
		int count = 0;
		for(int i = 0; i < s.length; i++){
			if(s[i] == 1) count++;
		}
		if(count == 0){
			System.out.println("E = 0 => No Error");
			e_d = 0;
		} 
		else{
			System.out.println("E = 1 => Error");
			e_d = 1;
		}
	}

	public double upper_bound(double p){//вычисление верхней границы ошибки
		double pe = 0;
		double sum = 0;
		for(int i = 0; i < d - 1; i++){
			sum += (double)(C(i, n) * Math.pow(p, i) * Math.pow((1 - p), (n - i)));
		}
		pe = 1 - sum;
		System.out.println("Pe+ = " + pe);
		return pe;
	}

	private double C(int a, int b){//формула сочетания
		double res = 0;
		res = (double) (fact(b) / (fact(a) * fact(b - a)));
		return res;
	}

	private int fact(int a){//формула факториала
		int sum = 1;
		for(int i = 2; i <= a; i++){
			sum *= i;
		}
		return sum;
	}

	public double exact_value(ArrayList<int[]> list, double p_bit){//вычисление точного значения ошибки
		int []array = new int[n + 1];
		int count = 0;
		double sum = 0;
		for(int i = 0; i < list.size(); i++){
			count = code_word_weight(list.get(i));
			for(int j = 0; j <= n; j++){
				if(count == j){
					array[j]++;
					break;
				}
			}
		}
		for(int i = 0; i < array.length; i++){
			System.out.println(array[i]);
		}
		for(int i = d; i <= n; i++){
			sum += (double)(array[i] * Math.pow(p_bit, i) * Math.pow((1 - p_bit), (n - i)));
		}
		System.out.println("Pe = " + sum);
		return sum;
	}

	private int code_word_weight(int []array){//подсчет веса вектора
		int count = 0;
		for(int i = 0; i < array.length; i++){
			if(array[i] == 1) count++;
		}
		return count;
	}

	private void print_array(int []array, String str){//вывод многочлена на экран
		System.out.print(str + " = (");
		for(int i = 0; i < array.length; i++){
			System.out.print(array[i]);
		}
		System.out.print(")");
		System.out.print("\n");
	}

	public void print_to_csv(double []array1, double []array2, String name1, String name2, String name_of_file) throws Exception{//вывод значений в csv файл
    	PrintWriter writer = new PrintWriter(new File(name_of_file));
    	StringBuilder sb = new StringBuilder();
    	sb.append(name2);
    	sb.append(";");
    	sb.append(name1);
    	sb.append("\n");
    	for(int i = 0; i < array1.length; i++){
    		sb.append(array2[i]);
    		sb.append(";");
    		sb.append(array1[i]);
    		sb.append("\n");
    	}
    	writer.write(sb.toString());
    	writer.close();
	}
}