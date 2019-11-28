package myMath;

import static org.junit.Assert.fail;
import static org.junit.Assert.*;
import org.junit.Test;

/**This class represents a simple tester for the Polynom class, 
* with a test for each function.
* On the screen will be printed the result from the test, and what the answer really is. 
* Good result: true, should be true.
* Bad result: false, should be true.
* 
* @author Ilana
*/

public class PolynomTest {
	public static void main(String[] args) {
		testGoodExamples();
		testF();
		testAddPolynom_able();
		testAddMonom();
		testSubstract();
		testMultiplyMonom();
		testMultiplyPolynom_able();
		testIsZero();
		testRoot();
		testCopy();
		testDerivative();
		testArea();
		testToString();
		
		
	}

	public static void testGoodExamples() {
		System.out.println("*****  TestGoodExamples:  *****");
		String[] polynoms = {"x^6", "4x+5", "3x^4+2x^3+4x^4+6"};
		for (int i = 0; i < polynoms.length; i++) {
			Polynom p=new Polynom(polynoms[i]);
			System.out.println(p);
		}
	}

	public static void testF() {
		System.out.println("*****  TestF:  *****");
		Polynom p1 =new Polynom("3x^3+x^2+1");
		if(p1.f(0)!=1 || p1.f(1)!=5 || p1.f(2)!=29)
			System.out.println("Failed");
		else
			System.out.println("Success");
	}

	public static void testAddPolynom_able() {
		System.out.println("*****  TestAddPolynom:  *****");
		Polynom p1=new Polynom();
		p1.add(new Polynom("3x^4+2.5x-x+7x^5"));
		p1.add(new Polynom("6x^3-9"));
		Polynom p2=new Polynom("-9.0+1.5x+6.0x^3+3.0x^4+7.0x^5");
		System.out.println(p1.equals(p2) +", should be true");
	}


	public static void testAddMonom() {
		System.out.println("*****  TestAddMonom:  *****");
		Polynom p1=new Polynom();
		p1.add(new Monom(3,2));
		p1.add(new Monom(1,0));
		p1.add(new Monom(2,4));
		p1.add(new Monom(1,2));
		String str="1.0+4.0x^2+2.0x^4";
		System.out.println(str.equals(p1.toString()) +", should be true");
	}


	public static void testSubstract() {
		System.out.println("*****  TestSub:  *****");
		Polynom p1=new Polynom("4x+2");
		p1.substract(p1);
		System.out.println(p1.isZero() +", should be true");
		Polynom p2=new Polynom("9x^3+2x^6");
		Polynom_able p3=new Polynom("9x^3+2x^6-5");
		p2.substract(p3);
		System.out.println(p2.equals(new Polynom("5")) +", should be true");
	}


	public static void testMultiplyMonom() {
		System.out.println("*****  TestMultMonom:  *****");
		Polynom p1=new Polynom("9x^2+7x^5");
		Monom m1=new Monom("-2x");
		p1.multiply(m1);
		Polynom p2=new Polynom("-18x^3-14x^6");
		System.out.println(p1.equals(p2) +", should be true");
		p1.multiply(new Monom(1,0));
		System.out.println(p1.equals(p2) +", should be true");

	}


	public static void testMultiplyPolynom_able() {
		System.out.println("*****  TestMultPolynom:  *****");
		Polynom p1=new Polynom("9x^2+7x^5");
		Polynom p2=new Polynom("-2x+6x^3");
		p1.multiply(p2);
		Polynom p3=new Polynom("-18x^3-14x^6+54x^5+42x^8");
		System.out.println(p1.equals(p3)+" should be true");
	}


	public static void testIsZero() {
		System.out.println("*****  TestIsZero:  *****");
		
		Polynom p1=new Polynom();
		p1.add(new Polynom("0+0"));
		System.out.println(p1.isZero()+ ", should be true");
		
		Polynom p2=new Polynom();
		System.out.println(p2.isZero()+ ", should be true");
		
		Polynom p3=new Polynom("5");
		System.out.println(p3.isZero()+ ", should be false");
	}

	public static void testRoot() {
		System.out.println("*****  TestRoot:  *****");
		Polynom p1=new Polynom ("2x^2+4x");
		double eps=0.0001;
		double root=p1.root(-1,1,eps);
		System.out.println(root +", should be around 0");
	}

	public static void testCopy() {
		System.out.println("*****  TestCopy:  *****");
		Polynom_able p1=new Polynom("3x^5+8x^2");
		Polynom_able p2=p1.copy();
		System.out.println(p1.equals(p2) +", should be true");
		p1.add(p2);
		System.out.println(p1.equals(p2) +", should be false");

	}


	public static void testDerivative() {
		System.out.println("*****  TestDerivative:  *****");
		Polynom_able p1=new Polynom("10x^8-4.5x^7+4x^6-x^3+2x^2+1");
		while(!p1.isZero()) {
			p1=p1.derivative();
			System.out.println(p1);
		}
	}

	public static void testArea() {
		System.out.println("*****  TestArea:  *****");
		Polynom p1= new Polynom("x^4-2x^2+1");
		double eps=0.0001;
		double area=p1.area(0, 1, eps);
		System.out.println(area +", should be around 0.533333");

	}


	public static void testToString() {
		System.out.println("*****  TestToString:  *****");
		Polynom p1=new Polynom("0.65");
		Polynom p2=new Polynom("4x^5+0x^1+1x^3");
		Polynom p3=new Polynom("5x^4 + 1");
		System.out.println(p1 +", should be 0.65");
		System.out.println(p2 +", should be 1.0x^3+4.0x^5");
		System.out.println(p3 +", should be 1.0+5.0x^4");
	}


}
