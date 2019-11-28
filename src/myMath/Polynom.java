package myMath;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.function.Predicate;

import myMath.Monom;
/**
 * This class represents a Polynom with add, multiply functionality, it also should support the following:
 * 1. Riemann's Integral: https://en.wikipedia.org/wiki/Riemann_integral
 * 2. Finding a numerical value between two values (currently support root only f(x)=0).
 * 3. Derivative
 * @author Ilana
 *
 */
public class Polynom implements Polynom_able{

	private List<Monom> poly=new ArrayList<Monom>();
	private List<Integer> power=new ArrayList<Integer>();

	/**
	 * Zero (empty polynom)
	 */
	public Polynom() {
		poly.add(new Monom("0"));
		power.add(0);
	}
	/**
	 * init a Polynom from a String such as:
	 *  {"x", "3+1.4X^3-34*x", 3x^4+2x^3+4x^4+6};
	 * @param s: is a string represents a Polynom
	 */
	public Polynom(String s) {	
		Monom m;
		int i=0;
		if(s.startsWith("-"))
			i++;
		s=s.replaceAll(" ","");
		s=s.replaceAll("-", "+-");
		String[] arr=s.split("\\+");
		for(;i<arr.length;i++) {
			m=new Monom(arr[i]);
			if(power.contains(m.get_power()))
				this.add(m);
			else {
				poly.add(m);
				power.add(m.get_power());
			}
		}
		Collections.sort(power);
		Collections.sort(poly, new Monom_Comperator());
	}

	/**
	 * this method calculates f(x) of a given x.
	 * @return ans=f(x)
	 */
	public double f(double x) {
		double ans=0;
		Monom m;
		Iterator<Monom> itr=this.iteretor();
		while(itr.hasNext()) 
		{
			m=(Monom) itr.next();
			ans+=m.f(x);
		}
		return ans;
	}

	/**
	 * This method adds a Polynom_able p1 to a Polynom.
	 */
	public void add(Polynom_able p1) {
		Iterator<Monom> itr=p1.iteretor();
		Monom m;
		while(itr.hasNext()) 
		{
			m=itr.next();
			this.add(m);	
		}
	}

	/**
	 * This method adds a Monom m1 to a Polynom. 
	 */
	public void add(Monom m1) {
		if(power.contains(m1.get_power())) 
		{
			int i=power.indexOf(m1.get_power());
			Monom m=poly.get(i);
			m.add(m1);
			poly.set(i,m);		
		}
		else
		{
			poly.add(m1);
			power.add(m1.get_power());
			Collections.sort(power);
			Collections.sort(poly, new Monom_Comperator());

		}
	}
	/**
	 * This method substracts Polynom_able p1 from a Polynom.
	 */
	public void substract(Polynom_able p1) 
	{
		Polynom_able p2=toNegetive(p1);
		this.add(p2);
	}
	/**
	 * A private function which gets Polynom_able p1 and returns -p1
	 * @param p1 is a Polynom_able
	 * @return -p1
	 */
	private Polynom_able toNegetive(Polynom_able p1) 
	{
		Polynom_able p2=new Polynom(p1.toString());
		p2.multiply(new Monom(-1,0));
		return p2;
	}

	/**
	 * This method multiply polynom by a monom.
	 * For example: this= 3x^2+x, m1=x
	 * returns 3x^3+x^2
	 */
	public void multiply(Monom m1) {
		Monom m;
		for(int i=0;i<this.poly.size();i++)
		{
			m=poly.get(i);
			m.multipy(m1);
			this.poly.set(i, m);
			this.power.set(i, m.get_power());
		}

	}


	/**This method multiply polynom by a Polynom_able p1.
	 * For example: this= 3x^2+x, p1=x+1
	 * returns 3x^3+4x^2+x
	 */
	public void multiply(Polynom_able p1) 
	{
		Monom m;
		Polynom_able p2=new Polynom(p1.toString());
		Polynom_able[] polys=new Polynom[this.poly.size()];
		for(int i=0;i<this.poly.size();i++)
		{
			m=this.poly.get(i);
			p2.multiply(m);
			polys[i]=p2;
			p2=p1.copy();
		}

		this.poly.clear();
		this.power.clear();
		this.poly.add(new Monom ("0"));
		this.power.add(0);
		for(int i=0;i<polys.length;i++)
		{
			this.add(polys[i]);	
		}	

	}



	/**
	 * This method returns true if two given Polynoms are the same.
	 */
	public boolean equals(Polynom_able p1) {
		String s1=this.toString();
		String s2=p1.toString();
		if(s1.equals(s2))
			return true;
		return false;
	}

	/**
	 * This method returns true if the given Polynom is equal to zero.
	 */
	public boolean isZero() {
		Monom m;
		for(int i=0;i<this.poly.size();i++)
		{
			m=this.poly.get(i);
			if(!m.equals(new Monom(0,0)))
				return false;
		}
		return true;
	}

	/**
	 * Compute a value x' (x0<=x'<=x1) for with |f(x')| < eps
	 * assuming (f(x0)*f(x1)<=0, else should throws runtimeException 
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
			throw new RuntimeException("One of the values must be negetive while the other positive");
		}
		else{
			ans=(x0+x1)/2;
			if(Math.abs(ans)<eps)
				return ans;
			else if(f(ans)>eps)
				return root(x0,ans,eps);
			else
				return root(ans,x1,eps);
		}
	}

	/**
	 * This method returns a copy of a given Polynom.
	 */
	public Polynom_able copy() {
		String s=this.toString();
		Polynom_able p1=new Polynom(s);	
		return p1;
	}



	/** 
	 * This method returns the derivative Polynom of this.
	 * @return Polynom f'(x)
	 */
	public Polynom_able derivative() {
		Polynom_able p1=new Polynom();
		Monom m;
		for(int i=0;i<this.poly.size();i++)
		{
			m=this.poly.get(i);
			p1.add(m.derivative());
		}
		return p1;
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
			if(f(x0+i+eps)>=0)
				S+=f(x0+i*eps)*eps;
		}
		return S;
	}


	public Iterator<Monom> iteretor() {
		Iterator<Monom> itr=poly.iterator();
		return itr;
	}

	/**
	 * This method returns a given Polynom as a string.
	 */
	public String toString() {
		String result="";
		if (this.isZero())
			return "0";
		for (int i=0; i<poly.size() ; i++ ) {
			if(poly.get(i).get_coefficient()==0)
				result+="";
			else if(poly.get(i).get_power()==0)
				result+=(poly.get(i).get_coefficient() + " ");
			else if(poly.get(i).get_power()==1)
				result+=(poly.get(i).get_coefficient() + "x ");
			else
				result += (poly.get(i).get_coefficient() + "x^" + poly.get(i).get_power() + " ") ;
		}
		result=result.replace(" ", "+");
		result=result.replace("+-","-");
		result=result.substring(0,result.length()-1);
		return result;
	}
}
