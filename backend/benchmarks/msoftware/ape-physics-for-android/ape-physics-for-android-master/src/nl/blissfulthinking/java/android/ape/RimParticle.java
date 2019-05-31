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
	
	public class RimParticle {
		
		private int wr;
		private int av;
		private int sp;
		private int maxTorque;
		
		public final int getSpeed() {
			return sp;
		}
		
		public final void setSpeed(int s) {
			sp = s;
		}
		
		public final int getAngularVelocity() {
			return av;
		}
		
		public final void setAngularVelocity(int s) {
			av = s;
		}
		
		/**
		 * Origins of this code are from Raigan Burns, Metanet Software
		 */
		public final void update(int dt) {
			
			//MvdA TODO reinstate this
//			// USE VECTOR METHODS HERE		
//			
//			//clamp torques to valid range
//			sp = Math.max(-maxTorque, Math.min(maxTorque, sp + av));
//	
//			//apply torque
//			//this is the tangent vector at the rim particle
//			double dx = -curr.y;
//			double dy =  curr.x;
//	
//			//normalize so we can scale by the rotational speed
//			double len = Math.sqrt(dx * dx + dy * dy);
//			dx /= len;
//			dy /= len;
//	
//			curr.x += sp * dx;
//			curr.y += sp * dy;		
//	
//			double ox = prev.x;
//			double oy = prev.y;
//			double px = prev.x = curr.x;		
//			double py = prev.y = curr.y;		
//			
//			curr.x += APEngine.damping * (px - ox);
//			curr.y += APEngine.damping * (py - oy);	
//	
//			// hold the rim particle in place
//			double clen = Math.sqrt(curr.x * curr.x + curr.y * curr.y);
//			double diff = (clen - wr) / clen;
//	
//			curr.x -= curr.x * diff;
//			curr.y -= curr.y * diff;
		}
	}



