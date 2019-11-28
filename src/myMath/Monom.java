package myMath;

import java.util.Comparator;

/**
 * This class represents a simple "Monom" of shape a*x^b, where a is a real number and a is an integer (summed a none negative), 
 * see: https://en.wikipedia.org/wiki/Monomial 
 * The class implements function and support simple operations as: construction, value at x, derivative, add, multiply, area and root. 
 * @author Ilana
 *
 */
public class Monom implements cont_function{
	private double _coefficient; 
	private int _power;
	public static final Monom ZERO = new Monom(0,0);
	public static final Monom MINUS1 = new Monom(-1,0);
	public static final double EPSILON = 0.0000001;
	public static final Comparator<Monom> _Comp = new Monom_Comperator();
	public static Comparator<Monom> getComp() {return _Comp;}

	/**
	 * Constructor. Gets coefficient a and power b, returns monom ax^b.
	 * @param a
	 * @param b
	 */
	public Monom(double a, int b){
		if(b<0)
			throw new RuntimeException("The power cannot be less than 0");
		this.set_coefficient(a);
		this.set_power(b);
	}

	/**
	 * Copy constructor. Gets monom m, returns its copy.
	 * @param m
	 */
	public Monom(Monom m) {
		this(m.get_coefficient(), m.get_power());
	}

	/**
	 * Constructor. Gets string str, returns monom ax^b.
	 * @param str
	 */
	public Monom(String str)
	{
		str=str.toLowerCase();
		str=str.replace(" ", "");
		if(str==null)
			throw new RuntimeException("Cannot be null");
		//If the form is ax^b	
		else if(str.contains("^")){
			int index=str.indexOf("^");
			String power=str.substring(index+1);
			String coefficient=str.substring(0, index);
			try{
				this.set_power(Integer.parseInt(power));
			}
			catch(Exception e){
				throw new RuntimeException("Was expecting an integer for power. Got:" +power);
			}
			ax(coefficient);	
		}
		//If the form is ax
		else if(str.contains("x")){
			this.set_power(1);
			ax(str);		
		}
		//If it is a number a
		else{
			this.set_power(0);
			try {
				this.set_coefficient(Double.parseDouble(str));
			}
			catch(Exception e){
				throw new RuntimeException("Was expecting double. Got:"+str);
			}
		}
	}
/**
 * private method for turning string in the form of a*x to a monom
 * @param str is a string in the form of a*x
 */
	private void ax(String str)
	{
		if(!str.endsWith("x"))
			throw new RuntimeException("Not according the standard shape of monom. Got:"+str);
		else{
			str=str.substring(0, str.length()-1);
			if(str.equals(""))
				this.set_coefficient(1);
			else if(str.equals("-"))
				this.set_coefficient(-1);
			else if(str.endsWith("*"))
				str=str.substring(0, str.length()-1);
			else {
				try{
					this.set_coefficient(Double.parseDouble(str));
				}
				catch(Exception e) {
					throw new RuntimeException("Was expecting an double for coefficient. Got:" + str);
				}
			}
		}
	}


	//getters
	public double get_coefficient() {
		return this._coefficient;
	}
	public int get_power() {
		return this._power;
	}


	/** 
	 * This method returns the derivative monom of this.
	 * @return monom f'(x)
	 */
	public Monom derivative() {
		if(this.get_power()==0)
		{
			return getNewZeroMonom();
		}
		return new Monom(this.get_coefficient()*this.get_power(), this.get_power()-1);
	}

	/**
	 * This method calculates f(x) of a given x.
	 * @return ans=f(x)
	 */
	public double f(double x) {
		double ans=0;
		double p = this.get_power();
		ans = this.get_coefficient()*Math.pow(x, p);
		return ans;
	} 

	/**
	 * This method returns true if the monom is 0. Else, returns false.
	 * @return true if this monom is equal to 0.
	 */
	public boolean isZero() {
		return this.get_coefficient() == 0;
	}

	/**
	 * This method returns true if two Monoms are the same. Else, false.
	 * @param m
	 * @return true if two monoms are equals.
	 */
	public boolean equals(Monom m) {
		if(this._coefficient==0 && m._coefficient==0)
			return true;

		else if (this._coefficient==m._coefficient && this._power==m._power)
			return true;

		return false;
	}


	/**
	 * This method adds Monom m to this Monom.
	 * @param m
	 */
	public void add(Monom m) {
		if(this._coefficient==0)
		{
			this._coefficient=m._coefficient;
			this._power=m._power;
		}
		else if(m._coefficient==0)
			return;
		else if(this.get_power()!=m.get_power() && m._coefficient!=0)
			throw new RuntimeException("The powers are not the same");
		else
			this._coefficient += m._coefficient;		
	}

	/**
	 * This method multiplies this and d, and saves the answer in this. 
	 * @param d
	 */
	public void multipy(Monom d) {

		this._coefficient=this._coefficient*d._coefficient;
		this._power+=d._power;
	}

	/**
	 * This method returns a given Monom as a string.
	 */
	public String toString() {
		String ans="";
		if(this._coefficient==0)
			ans="0";
		else if(this._power==0) 
			ans=this._coefficient+"";
		else if(this._power==1)
			ans=this._coefficient + "x";
		else
			ans = this._coefficient + "x^" + this._power;
		return ans;
	}

	//setters
	private void set_coefficient(double a){
		this._coefficient = a;
	}
	private void set_power(int p) {
		if(p<0) {throw new RuntimeException("ERR the power of Monom should not be negative, got: "+p);}
		this._power = p;
	}

	private static Monom getNewZeroMonom() {
		return new Monom(ZERO);
	}

	/**
	 * Compute a Riman's integral from x0 to x1 in eps steps. 
	 * @param x0 starting point
	 * @param x1 end point
	 * @param eps positive step value
	 * @return the approximated area above X-axis below this function bounded in the range of [x0,x1]
	 */
	public double area(double x0, double x1, double eps) {
		double S=0;
		double num=(x1-x0)/eps;
		for(int i=0;i<=num;i++) {
			if(f(x0+i*eps)>=0)
				S+=f(x0+i*eps)*eps;
		}
		return S;
	}

	/**
	 * This method compute a value x' (x0<=x'<=x1) for with |f(x')| < eps
	 * assuming (f(x0)*f(x1))<=0, else should throws runtimeException 
	 * computes f(x') such that:
	 * 	(i) x0<=x'<=x1 && 
	 * 	(ii) |f(x')|<eps
	 * @param x0 starting point
	 * @param x1 end point
	 * @param eps>0 (positive) representing the epsilon range the solution should be within.
	 * @return an approximated value (root) for this (cont.) function 
	 */
	public double root(double x0, double x1, double eps) {
		double ans;
		if(f(x0)*f(x1)>0) {
			throw new RuntimeException("One of the values must be negetive");
		}
		else {
			ans=(x0+x1)/2;
			if(Math.abs(ans)<eps)
				return ans;
			else if(f(ans)>0)
				return root(x0,ans,eps);
			else
				return root(ans,x1,eps);
		}
	}



}
