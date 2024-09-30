import java.lang.Math;
import java.util.Scanner;
import java.util.Arrays;
import java.io.*;
public class Polynomial{
	double[] coefficients = new double[1];
	int[] exponents = new int[1];

	public Polynomial(){
		coefficients[0] = 0;
		exponents[0] = 0;
	}
	public Polynomial(double[] c, int[] e){
		coefficients = c;
		exponents = e;
	}
	public Polynomial(File fn){
		String i;
		int k = 0;
		int j = 0;
		double neg = 1;
		int ex_neg = 1;
		int x = 0;
		char c;
		int u;
		double v;
		try{
			Scanner read = new Scanner(fn);
			i = read.nextLine();
			int[] temp_e = new int[i.length()];
			double[] temp_c = new double[i.length()];
			while (true){
				if (k == i.length()){
					break;
				}
				c = i.charAt(k);
				if (x == 1){
					if (c != '+' && c != '-'){
						u = Integer.parseInt(String.valueOf(c));
						temp_e[j-1] = ex_neg * u;
						x = 0;
						k++;
						ex_neg = 1;
						continue;
					}
					else if (c == '-'){
						ex_neg = -1;
						k++;
						continue;
					}
					temp_e[j-1] = 1;
					x = 0;
				}
				if (Character.isDigit(c)){
					v = Double.parseDouble(String.valueOf(c));
					temp_c[j] = neg * v;
					j++;
				}
				else if (c == '+'){
					neg = 1;
				}
				else if (c == '-'){
					neg = -1;
				}
				else if (c == 'x'){
					x = 1;
				}
				k++;
			}
			int[] new_e = new int[j];
			double[] new_c = new double[j];
			for (int n = 0; n < j; n++){
				new_e[n] = temp_e[n];
				new_c[n] = temp_c[n];
			}
			exponents = new_e;
			coefficients = new_c;
		}
		catch (Exception e){
			System.out.println("There was an error with reading the file.");
			e.printStackTrace();
		}
	}
	public Polynomial add(Polynomial p){
		int[] a = p.exponents;
		int[] b = exponents;
		int i = 0;
		int check = 0;
		for (int j = 0; j < a.length; j++){
			for (int k = 0; k < b.length; k++){
				if (b[k] == a[j]){
					check++;
				}
			}
			if (check == 0){
				i++;
			}
			check = 0;
		}
		int inc = 0;
		int[] new_e = new int[i + b.length];
		for (int n = 0; n < a.length; n++){
			new_e[n] = a[n];
		}
		for (int o = 0; o < b.length; o++){
			for (int g = 0; g < a.length; g++){
				if (a[g] == b[o]){
					check++;
				}
			}
			if (check == 0){
				new_e[inc + a.length] = b[o];
				inc++;
			}
			check = 0;
		}
		Arrays.sort(new_e);
		double[] new_c = new double[new_e.length];
		for (int l = 0; l < new_c.length; l++){
			int find_a = Arrays.binarySearch(a,new_e[l]);
			int find_b = Arrays.binarySearch(b,new_e[l]);
			if (find_a >= 0){
				new_c[l] = p.coefficients[find_a];
			}
			if (find_b >= 0){
				new_c[l] += coefficients[find_b];
			}
		}
		p.coefficients = new_c;
		p.exponents = new_e;
		return p;
	}
	public double evaluate(double d){
		int i = 0;
		double eval = 0;
		for (double p : coefficients){
			eval += p * Math.pow(d, exponents[i]);
			i++;
		}
		return eval;
	}
	public boolean hasRoot(double d){
		return evaluate(d) == 0;
	}
	public Polynomial multiply(Polynomial p){
		Polynomial mult = new Polynomial();
		double[] c = new double[1];
		double[] tempC = new double[1];
		int[] tempE = new int[1];
		int[] e = new int[1];
		int m = 0;
		for (int i = 0; i < coefficients.length; i++){
			for (int j = 0; j < p.coefficients.length; j++){
				mult.coefficients[m] = p.coefficients[j] * coefficients[i];
				mult.exponents[m] = p.exponents[j] + exponents[i];
				m++;
			}
		}
		for (int k = 0; k < m; k++){
			for (int l = k; l < m; l++){
				if (mult.exponents[k] == mult.exponents[l] && k != l){
					mult.coefficients[k] += mult.coefficients[l];
					for (int n = 0; n < m; n++){
						tempC = new double[c.length-1];
						tempE = new int[e.length-1];
						if(n != l){
							tempC[n] = c[n];
							tempE[n] = e[n];
						}
					}
					c = tempC;
					e = tempE;
				}
			}
		}
		p.coefficients = c;
		p.exponents = e;
		return p;
	}
	public void saveToFile(String s){
		try{
			File file = new File(s);
			FileWriter fw = new FileWriter (file);
			for (int i = 0; i < coefficients.length; i++){
				if (i != 0 && coefficients[i] > 0){
					fw.write("+");
				}
				if (exponents[i] != 0){
					fw.write((int)coefficients[i] + "x" + exponents[i]);
				}
				else{
					fw.write((int)coefficients[i] + "x");
				}
			}
			fw.close();
		}
		catch(Exception e){
			System.out.println("There was an error with writing the file.");
			e.printStackTrace();
		}
	}
}