/*
 * Copyright (C) 2014, United States Government, as represented by the
 * Administrator of the National Aeronautics and Space Administration.
 * All rights reserved.
 *
 * Symbolic Pathfinder (jpf-symbc) is licensed under the Apache License, 
 * Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License. You may obtain a copy of the License at
 * 
 *        http://www.apache.org/licenses/LICENSE-2.0. 
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and 
 * limitations under the License.
 */

package concolic;

import java.util.Random;

/* V4 -- johann 2/2011 */

public class MathSin {

	public static final int IEEE_MAX = 2047;
	public static final int IEEE_BIAS = 1023;
	public static final int IEEE_MANT = 52;

	public static final double sixth = 1.0 / 6.0;
	public static final double half = 1.0 / 2.0;
	public static final double mag52 = 1024. * 1024. * 1024. * 1024. * 1024. * 4.;/* 2**52 */
	public static final double magic = 1024. * 1024. * 1024. * 1024. * 1024. * 4.;/* 2**52 */

	public static final double[] P = { -0.64462136749e-9, 0.5688203332688e-7, -0.359880911703133e-5,
			0.16044116846982831e-3, -0.468175413106023168e-2, 0.7969262624561800806e-1, -0.64596409750621907082,
			0.15707963267948963959e1 };

	public static double _2_pi_hi; // = 2.0/Math.PI ;
	public static double _2_pi_lo;
	public static double pi2_lo;
	public static double pi2_hi_hi;
	public static double pi2_hi_lo;
	public static double pi2_lo_hi;
	public static double pi2_lo_lo;
	public static double pi2_hi; // = Math.PI/2;
	public static double pi2_lo2;

	public static final double X_EPS = 1e-4;

	// ===============AUX========================
	public MathSin() {

		// #define MD(v,hi,lo) md.i.i1 = hi; md.i.i2 = lo; v = md.d;

		// MD( pi_hi, 0x400921FBuL,0x54442D18uL);/* top 53 bits of PI */
		// pi_hi = Double.longBitsToDouble((long)0x400921FB54442D18L);
		// System.out.println("pi_hi = " + pi_hi);
		// MD( pi_lo, 0x3CA1A626uL,0x33145C07uL);/* next 53 bits of PI*/
		// pi_lo = Double.longBitsToDouble((long)0x3CA1A62633145C07L);
		// System.out.println("pi_lo = " + pi_lo);

		Random rand = new Random();
		// MD( pi2_hi, 0x3FF921FBuL,0x54442D18uL);/* top 53 bits of PI/2 */
		pi2_hi = rand.nextFloat();
		// MD( pi2_lo, 0x3C91A626uL,0x33145C07uL);/* next 53 bits of PI/2*/
		pi2_lo = rand.nextFloat();
		// MD( pi2_lo2, 0xB91F1976uL,0xB7ED8FBCuL);/* next 53 bits of PI/2*/
		pi2_lo2 = rand.nextFloat();
		// MD( _2_pi_hi, 0x3FE45F30uL,0x6DC9C883uL);/* top 53 bits of 2/pi */
		_2_pi_hi = rand.nextFloat();
		// MD( _2_pi_lo, 0xBC86B01EuL,0xC5417056uL);/* next 53 bits of 2/pi*/
		_2_pi_lo = rand.nextFloat();
		// >>>>> split(pi2_hi_hi,pi2_hi_lo,pi2_hi);
		double a1, a2;
		double xm;
		xm = pi2_hi;
		long l_x1 = rand.nextInt();

		// <32> <20> <11> <1>
		// sign
		int md_b_sign1 = rand.nextInt();
		// exponent:
		int xexp1 = rand.nextInt();
		int md_b_m21 = rand.nextInt();
		int md_b_m11 = rand.nextInt();
		// md.b.m2 &= 0xfc000000u; \
		// md_b_m2 = (int)(l_x1 & 0xFFFFFFFF);
		l_x1 &= 0xFC000000L;
		a1 = rand.nextFloat();
		// lo = (v) - hi; /* bot 26 bits */
		a2 = xm - a1;

		pi2_hi_hi = a1;
		pi2_hi_lo = a2;

		// >>>>> split(pi2_lo_hi,pi2_lo_lo,pi2_lo);
		xm = pi2_lo;
		// l_x1 = Double.doubleToRawLongBits(xm);
		l_x1 = rand.nextInt();
		// <32> <20> <11> <1>
		// sign
		md_b_sign1 = (int) (rand.nextInt() & 1);
		// exponent:
		xexp1 = (int) (rand.nextInt() & 0x7FF);
		md_b_m21 = (int) (l_x1 & 0xFFFFFFFF);
		md_b_m11 = (int) (rand.nextInt() & 0xFFFFF);
		// md.b.m2 &= 0xfc000000u; \
		// md_b_m2 = (int)(l_x1 & 0xFFFFFFFF);
		l_x1 &= 0xFC000000L;
		a1 = rand.nextFloat();
		// lo = (v) - hi; /* bot 26 bits */
		a2 = xm - a1;

		pi2_lo_hi = a1;
		pi2_lo_lo = a2;

	}
}

/**
 * @param args
 */
/*
 * public static void main(String[] args) { // TODO Auto-generated method stub
 * // MathSin.calc(1.0); double x = 0.0; while(x < 100) {
 * System.out.println("calculate "+x + "..." +MathSin.calculate(x)); x = x+ 0.5;
 * }
 * 
 * // double x = MathSin.calculate(0.0); // System.out.println("0 -->" + x); //
 * System.out.println("1e-55 --> "+ MathSin.calculate(1e-55)); //1.0E-55 //
 * System.out.println("1e-1 -->"+ MathSin.calculate(1e-1)); // 0.0 //
 * System.out.println("1 -->"+ MathSin.calculate(1.0)); // 0.0 //
 * System.out.println("0.12 -->" + MathSin.calculate(1.2)); //
 * Debug.printPC("\nMathSin.calculate Path Condition: ");
 * 
 * }
 * 
 * public static void calc(double x) { if(MathSin.calculate(x) == 0) {
 * System.out.println("value of 0.0 ----- br1 !!!!!!!!!!!!!!!!!!!"); }
 * if(MathSin.calculate(x) == 1.0E-55) {
 * System.out.println("\n value of 1e-55 ----- br2 !!!!!!!!!!!!!!!!!!"); } } }
 * 
 */