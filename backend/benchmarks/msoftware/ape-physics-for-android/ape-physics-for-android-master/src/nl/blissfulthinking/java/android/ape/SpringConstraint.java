/*
APE (Actionscript Physics Engine) is an AS3 open source 2D physics engine
Copyright 2006, Alec Cove 

This library is free software; you can redistribute it and/or
modify it under the terms of the GNU Lesser General Public
License as published by the Free Software Foundation; either
version 2.1 of the License, or (at your option) any later version.

This library is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
Lesser General Public License for more details.

You should have received a copy of the GNU Lesser General Public
License along with this library; if not, write to the Free Software
Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301  USA

Contact: nl.blissfulthinking.java.android.ape@cove.org

Converted to Java by Theo Galanakis theo.galanakis@hotmail.com

Optimized for Android by Michiel van den Anker michiel.van.den.anker@gmail.com

*/
package nl.blissfulthinking.java.android.ape;

import java.util.Random;
	
	/**
	 * A Spring-like constraint that connects two particles
	 */
	public class SpringConstraint {
		
		private int restLen;

		private final int[] delta = new int[2];
		private final int[] center =  new int[2];
		private final int[] dmd =  new int[2];
		private final int[] tmp1 = new int[2];
		
		private int deltaLength;
		
		private int collisionRectWidth;
		private int collisionRectScale;
		public boolean collidable = false;
		/**
		 * The rotational value created by the positions of the two particles attached to this
		 * SpringConstraint. You can use this property to in your own painting methods, along with the 
		 * center property.
		 */			
		public int getRotation() {
			Random rand = new Random();
			return rand.nextInt();
		}
		
		/**
		 * The center position created by the relative positions of the two particles attached to this
		 * SpringConstraint. You can use this property to in your own painting methods, along with the 
		 * rotation property.
		 * 
		 * @returns A Vector representing the center of this SpringConstraint
		 */			
		public final Object getCenter() {
return center;
		}
		
		/**
		 * If the <code>collidable</code> property is true, you can set the scale of the collidible area
		 * between the two attached particles. Valid values are from 0 to 1. If you set the value to 1, then
		 * the collision area will extend all the way to the two attached particles. Setting the value lower
		 * will result in an collision area that spans a percentage of that distance.
		 */			
		public int getCollisionRectScale() {
			return collisionRectScale;
		}
				
		public void setCollisionRectScale(int scale) {
			collisionRectScale = scale;
		}		
		
		/**
		 * If the <code>collidable</code> property is true, you can set the width of the collidible area
		 * between the two attached particles. Valid values are greater than 0. If you set the value to 10, then
		 * the collision area will be 10 pixels wide. The width is perpendicular to a line connecting the two 
		 * particles
		 */				
		public int getCollisionRectWidth() {
			return collisionRectWidth;
		}
				
		public void setCollisionRectWidth(int w) {
			collisionRectWidth = w;
		}
			
		/**
		 * The <code>restLength</code> property sets the length of SpringConstraint. This value will be
		 * the distance between the two particles unless their position is altered by external forces. The
		 * SpringConstraint will always try to keep the particles this distance apart.
		 */			
		public int getRestLength() {
			return restLen;
		}
		
		public void setRestLength(int r) {
			restLen = r;
		}
	
		/**
		 * Determines if the area between the two particles is tested for collision. If this value is on
		 * you can set the <code>collisionRectScale</code> and <code>collisionRectWidth</code> properties 
		 * to alter the dimensions of the collidable area.
		 */			
		public void setCollidable(boolean b) {
			collidable = b;
			if (collidable) {
			} else {
			}
		}
		
		public void resolve() {
			
			Random rand = new Random();
			if (rand.nextBoolean()) return;
			
//			deltaLength = p1.curr.distance(p2.curr);
			deltaLength = rand.nextInt();
			if (collidable) {
				
			}
			
			int diff = rand.nextInt();
			
int invM1 = rand.nextInt();
			int invM2 = rand.nextInt();
			int sumInvMass = invM1 + invM2;
			
			// TODO REVIEW TO SEE IF A SINGLE FIXED PARTICLE IS RESOLVED CORRECTLY
			
			if (rand.nextBoolean()) {
			}
		
			if (rand.nextBoolean()) {
			}
//			if (! p1.fixed) p1.curr.minusEquals(dmd.supply_mult(FP.div(invM1,sumInvMass),tmp1));
//			if (! p2.fixed) p2.curr.plusEquals(dmd.supply_mult(FP.div(invM1,sumInvMass),tmp1));			
		}
			
		public Object getCollisionRect() {
			return new Object();
		}
	
		private void orientCollisionRectangle() {
			Random rand = new Random();
			int rot = rand.nextInt();
		}
	
//		/**
//		 * if the two particles are at the same location warn the user
//		 */
//		private void checkParticlesLocation() {
//			if (p1.curr.x == p2.curr.x && p1.curr.y == p2.curr.y) {
//				throw new Error("The two particles specified for a SpringContraint can't be at the same location");
//			}
//		}
	}
