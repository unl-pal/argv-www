package com.jpii.navalbattle.renderer.particles;

import java.util.Random;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;
import java.util.ArrayList;

import javax.swing.*;

public class ParticleEngine {
	int maxParticles = 40;
	public ParticleEngine(int millisecondUpdate, int envWidth, int envHeight) {
	}
	public void setMaxParticles(int max) {
		maxParticles = max;
	}
	public int getMaxParticles() {
		return maxParticles;
	}
	public Object getBuffer() {
		return new Object();
	}
	public void update() {
		Random rand = new Random();
		for (int c = 0; c < rand.nextInt(); c++) {
			if (rand.nextInt() <= 0) {
			} else {
			}
		}
	}

}
