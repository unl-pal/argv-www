package nl.blissfulthinking.java.android.apeforandroid;

import java.util.ArrayList;

public class PhysicsWorld {

//	private long usedTime;

	public static final int KEY_LEFT = 1;
	public static final int KEY_RIGHT = 2;
	
	//	private WheelParticle wheelParticleA;
//	private WheelParticle wheelParticleB;
//	
	public final void updateWorld() {
		
		
//		Log.d("Vector Pool Data","*** Vector Pool Data ***");
//		
////		Log.d("Vector Pool Data",Vector.getPoolSize()+" Vectors in Queue");
////		
//		Log.d("Vector Pool Data",Vector.creationCount+" Vectors created through NEW");
//		Log.d("Vector Pool Data",Vector.poolUnavailableCount+" Vectors that couldn't be drawn from pool");
////		Log.d(Vector.poolUnavailableCount-Vector.creationCount+" Vectors that missed the pool creation","bleh");
//		Vector.poolUnavailableCount = 0;
//		Vector.creationCount = 0;
//		
//		Log.d("Vector Pool Data",Vector.poolCreationCount+" Vectors drawn from pool");
//		Vector.poolCreationCount = 0;
//	
//		Log.d("Vector Pool Data",Vector.poolReleaseCount+" Vectors released into pool");
//		Vector.poolReleaseCount = 0;

//		Log.d("Fixed Point Tests","int 0 = fp = "+FP.fromInt(0));
//		Log.d("Fixed Point Tests","2+7 ="+(FP.toInt(FP.fromInt(2)+FP.fromInt(7))));
//		Log.d("Fixed Point Tests","0.5+(-0.5) ="+(FP.fromFloat(0.5f)+FP.fromFloat(-0.5f)));
//		Log.d("Fixed Point Tests","int 1 = fp = "+FP.ONE);
//		Log.d("Fixed Point Tests","int(0) == 0 ? "+(FP.fromInt(0)==0));
//		int q = FP.fromFloat(-124.8f);
//		Log.d("Fixed Point Tests","intVal :"+FP.toFloat(q)+" = "+FP.toInt(q));
//		int w = FP.fromFloat(-124.2f);
//		Log.d("Fixed Point Tests","intVal :"+FP.toFloat(w)+" = "+FP.toInt(w));
//		int i = FP.fromFloat(124.8f);
//		Log.d("Fixed Point Tests","roundPos :"+FP.toFloat(i)+" = "+FP.toInt(FP.roundPosPos(i)));
//		int j = FP.fromFloat(-124.2f);
//		Log.d("Fixed Point Tests","roundPos :"+FP.toFloat(j)+" = "+FP.toInt(FP.roundPosPos(j)));
		
//		int i = FP.fromFloat(124.8f);
//		Log.d("Fixed Point Tests","FP.abs : :"+FP.toFloat(i)+" = "+FP.toInt(FP.abs(i)));
//		Log.d("Fixed Point Tests","FP.mathABS :"+FP.toFloat(i)+" = "+FP.toInt(FP.mathABS(i)));
//		int j = FP.fromFloat(124.29878967999f);
//		Log.d("Fixed Point Tests","FP.abs : :"+FP.toFloat(j)+" = "+FP.toInt(FP.abs(j)));
//		Log.d("Fixed Point Tests","FP.mathABS :"+FP.toFloat(j)+" = "+FP.toInt(FP.mathABS(j)));
//		int k = FP.fromFloat(-124.8970657667f);
//		Log.d("Fixed Point Tests","FP.abs : :"+FP.toFloat(k)+" = "+FP.toInt(FP.abs(k)));
//		Log.d("Fixed Point Tests","FP.mathABS :"+FP.toFloat(k)+" = "+FP.toInt(FP.mathABS(k)));
//		int y = FP.fromFloat(0.4679796796979f);
//		Log.d("Fixed Point Tests","FP.abs : :"+FP.toFloat(y)+" = "+FP.toInt(FP.abs(y)));
//		Log.d("Fixed Point Tests","FP.mathABS :"+FP.toFloat(y)+" = "+FP.toInt(FP.mathABS(y)));
//		int u = FP.fromFloat(0.6679676575675f);
//		Log.d("Fixed Point Tests","FP.abs : :"+FP.toFloat(u)+" = "+FP.toInt(FP.abs(u)));
//		Log.d("Fixed Point Tests","FP.mathABS :"+FP.toFloat(u)+" = "+FP.toInt(FP.mathABS(u)));
//		int ry = FP.fromFloat(-0.4587968987979f);
//		Log.d("Fixed Point Tests","FP.abs : :"+FP.toFloat(ry)+" = "+FP.toInt(FP.abs(ry)));
//		Log.d("Fixed Point Tests","FP.mathABS :"+FP.toFloat(ry)+" = "+FP.toInt(FP.mathABS(ry)));
//		int du = FP.fromFloat(-0.6575478685688f);
//		Log.d("Fixed Point Tests","FP.abs : :"+FP.toFloat(du)+" = "+FP.toInt(FP.abs(du)));
//		Log.d("Fixed Point Tests","FP.mathABS :"+FP.toFloat(du)+" = "+FP.toInt(FP.mathABS(du)));
////		
//		int duq = FP.fromDouble(327.0f);
//		int duq = FP.fromInt(32767);
//		Log.d("Fixed Point Tests","FP.abs :"+FP.toFloat(duq)+" = "+FP.toInt(FP.abs(duq)));
//		Log.d("Fixed Point Tests","FP.mathABS :"+FP.toFloat(duq)+" = "+FP.toInt(FP.mathABS(duq)));
//		
//		int duq2 = FP.fromDouble(Double.POSITIVE_INFINITY);
////		duq2--;
////		duq2--;
//		Log.d("Fixed Point Tests","FP.abs :"+FP.toInt(duq2)+" = "+FP.toInt(FP.abs(duq2)));
//		Log.d("Fixed Point Tests","FP.mathABS :"+FP.toInt(duq2)+" = "+FP.toInt(FP.mathABS(duq2)));
//		
//		rotatingRect.setRotation(rotatingRect.getRotation() + 0.03);	
		
		
//		Log.d("Screen Size","width: "+GameView.width+" height: "+GameView.height);
	}  
	
	public void initWorld() {

		
		//		float size2 = 4.0f;
//		float px2 = size2;
//		float py2 = size2;
//		// little circles
//		for (int i = 0; i < 50; i++) {
//			px2 += size2;
//			py2 += size2;
//			APEngine.addParticle(new CircleParticle(px2+size2,py2+size2,size2,false,3.0f+(i/10),0.4f,0.002f));
//		}
//		
		int posx = 50;
		int posy = 50;
		int size = 40;
		int halfsize = 20; 
		int cornersize = 10;
		boolean collidable = true;
		// surfaces
		int thikness = 200;
	}
	

    public final void keyTyped(int key) {}

//	   public void keyPressed(int key) {
//			float keySpeed = 0.2;
//			
//			if (key == KEY_LEFT) {
//				wheelParticleA.setAngularVelocity(-keySpeed);
//				wheelParticleB.setAngularVelocity(-keySpeed);
//			} else if (key == KEY_RIGHT) {
//				wheelParticleA.setAngularVelocity(keySpeed);
//				wheelParticleB.setAngularVelocity(keySpeed);
//			}
//		}
//		
//		
//		public void keyReleased(int key) {
//			wheelParticleA.setAngularVelocity(0);
//			wheelParticleB.setAngularVelocity(0);
//		}
		 
		 public final void setGravity(int x, int y) {
		}
}

