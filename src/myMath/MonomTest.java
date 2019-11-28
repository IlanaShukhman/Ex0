package myMath;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.ArrayList;

import org.junit.Test;
/**
 * This class represents a simple tester for the Monom class, 
 * with a test for each function.
 * On the screen will be printed the result from the test, and what the answer really is. 
 * Good result: true, should be true.
 * Bad result: false, should be true.
 * 
 * @author Ilana
 */
public class MonomTest {
	public static void main(String[] args) {
		testGoodExamples();
		testDerivative();
		testF();
		testIsZero();
		testEqualsMonom();
		testAdd();
		testMultipy();
		testToString();
		testArea();
		testRoot();
	}


	public static void testGoodExamples() {
		System.out.println("*****  TestGoodExamples:  *****");
		String[] monoms= {"3x^2","5.2x^3","-3","-x","0","7X","+3*x","x^6"};
		for (int i = 0; i < monoms.length; i++) {
			Monom m= new Monom(monoms[i]);
			System.out.println(m);

		}
	}
	
	public static void testDerivative() {
		System.out.println("*****  TestDerivative:  *****");
		Monom p=new Monom(10,5);
		Monom p1=p.derivative();
		Monom p2=new Monom(50,4);
		System.out.println(p1.equals(p2)+", should be true");
	}


	public static void testF() {
		System.out.println("*****  TestF:  *****");
		Monom p1 =new Monom("3x^3");
		if(p1.f(0)!=0 || p1.f(1)!=3 || p1.f(2)!=24)
			System.out.println("Failed");
		else
			System.out.println("Success");
	}


	public static void testIsZero() {
		System.out.println("*****  TestIsZero:  *****");
		Monom p1=new Monom(0,5);
		System.out.println(p1.isZero()+", should be true");
		Monom p2=new Monom(5,0);
		System.out.println(p2.isZero()+", should be false");
		Monom p3=new Monom("0");
		System.out.println(p3.isZero()+", should be true");
	}


	public static void testEqualsMonom() {
		System.out.println("*****  TestEqualsMonom:  *****");
		Monom p1=new Monom(3.6,2);
		Monom p2=new Monom("3.60x^2");
		System.out.println(p1.equals(p2)+", should be true");
		Monom p3=new Monom("x");
		Monom p4=new Monom(1,1);
		System.out.println(p3.equals(p4)+", should be true");

	}

	public static void testAdd() {
		System.out.println("*****  TestAdd:  *****");
		Monom p1=new Monom(3,2);
		Monom p2=new Monom("6x^2");
		p1.add(p1);
		System.out.println(p1.equals(p2)+", should be true");
		p1.add(new Monom("0"));
		System.out.println(p1.equals(p2)+", should be true");
	}

	public static void testMultipy() {
		System.out.println("*****  TestMultipy:  *****");
		Monom p1=new Monom(3,2);
		Monom p2=new Monom(9,4);
		Monom p3=new Monom(81,8);
		p1.multipy(p1);
		System.out.println(p1.equals(p2)+", should be true");
		p1.multipy(p1);
		System.out.println(p1.equals(p3)+", should be true");
		p1.multipy(new Monom("0"));
		System.out.println(p1.equals(new Monom("0"))+", should be true");

	}

	public static void testToString() {
		System.out.println("*****  TestToString:  *****");
		Monom p1=new Monom(3,2);
		String str="3.0x^2";
		System.out.println(str.equals(p1.toString())+", should be true");
	}
	
	public static void testArea() {
		System.out.println("*****  TestArea:  *****");
		Monom p1= new Monom("x^3");
		double eps=0.0001;
		double area=p1.area(-1, 1, eps);
		System.out.println(area+", should be around 0.25");
	}

	public static void testRoot() {
		System.out.println("*****  TestRoot:  *****");
		Monom p1=new Monom ("2x^5");
		double eps=0.0001;
		double root=p1.root(-1,1,eps);
		System.out.println(root +", should be around 0");
	}

}
