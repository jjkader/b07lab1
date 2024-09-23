import java.lang.Math;
public class Polynomial{
	double[] coefficients = new double[1];

	public Polynomial(){
		coefficients[0] = 0;
	}
	public Polynomial(double[] c){
		coefficients = c;
	}
	public Polynomial add(Polynomial p){
		int i = 0;
		if (p.coefficients.length >= coefficients.length){
			for (double d : p.coefficients){
				if (i == coefficients.length){
					return p;
				}
				p.coefficients[i] += coefficients[i];
				i++;
			}
		}
		if (coefficients.length > p.coefficients.length){
			for (double d : coefficients){
				if (i == p.coefficients.length){
					p.coefficients = coefficients;
					return p;
				}	
				coefficients[i] += p.coefficients[i];
				i++;
			}
		}
		return p;
	}
	public double evaluate(double d){
		int i = 0;
		double eval = 0;
		for (double p : coefficients){
			eval += p * Math.pow(d, i);
			i++;
		}
		return eval;
	}
	public boolean hasRoot(double d){
		return evaluate(d) == 0;
	}
}