package com.github.gm.hotconf.test.web.service;

import java.util.List;

public interface TestingService {

	public abstract int getIntPrimProp();

	public abstract void setIntPrimProp(int intPrimProp);

	public abstract Integer getIntegerProp();

	public abstract void setIntegerProp(Integer integerProp);

	public abstract long getLongPrimProp();

	public abstract void setLongPrimProp(long longPrimProp);

	public abstract Long getLongProp();

	public abstract void setLongProp(Long longProp);

	public abstract double getDoublePrimProp();

	public abstract void setDoublePrimProp(double doublePrimProp);

	public abstract Double getDoublePrim();

	public abstract void setDoublePrim(Double doublePrim);

	public abstract float getFloatPrimProp();

	public abstract void setFloatPrimProp(float floatPrimProp);

	public abstract Float getFloatProp();

	public abstract void setFloatProp(Float floatProp);

	public abstract String getStringProp();

	public abstract void setStringProp(String stringProp);
	
	public abstract List<String> getHooksProof();

}