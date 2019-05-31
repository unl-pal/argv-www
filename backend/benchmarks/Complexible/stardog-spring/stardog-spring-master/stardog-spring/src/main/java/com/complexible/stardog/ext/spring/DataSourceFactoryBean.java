/*
* Copyright (c) the original authors
*
* Licensed under the Apache License, Version 2.0 (the "License");
* you may not use this file except in compliance with the License.
* You may obtain a copy of the License at
*
* http://www.apache.org/licenses/LICENSE-2.0
*
* Unless required by applicable law or agreed to in writing, software
* distributed under the License is distributed on an "AS IS" BASIS,
* WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
* See the License for the specific language governing permissions and
* limitations under the License.
*/
package com.complexible.stardog.ext.spring;

import java.util.Random;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

/**
 * StardogConnectionFactoryBean
 * 
 * This class implements the Spring interfaces for FactoryBean for a DataSource,
 * InitializingBean, and DisposableBean, so it is a fully Spring-aware factory
 * 
 * The objective is to configure one of these per Spring application context, and be
 * able to reference DataSource objects in the SnarlTemplate, so a SnarlTemplate always
 * gets a connection from the pool (via DataSource wrapper) injected in to it.
 * 
 * Configuration for this object matches both the parameters in ConnectionConfiguration and
 * ConnectionPoolConfiguration, and then inspects what has been injected to create the 
 * connection pool.  
 * 
 * @author Clark and Parsia, LLC
 * @author Al Baker
 *
 */
public class DataSourceFactoryBean {

	/**
	 * Properties used by the ConnectionConfig
	 */
	private String url;
	
	private String username;
	
	private String password;

	private boolean reasoningType = false;
	
	private String to;
	
	private Properties connectionProperties;
	
	/**
	 * Properties used by the ConnectionPoolConfig
	 * 
	 * TimeUnits default to miliseconds, but can be configured in Spring
	 * 
	 */
	private long blockCapacityTime = 900;
	
	private TimeUnit blockCapacityTimeUnit = TimeUnit.SECONDS;
	
	private long expirationTime = 300;
	
	private TimeUnit expirationTimeUnit = TimeUnit.SECONDS;
	
	private boolean failAtCapacity = false;
	
	private boolean growAtCapacity = true;
	
	private int maxIdle = 100;
	
	private int maxPool = 200;
	
	private int minPool = 10;
	
	private boolean noExpiration = false;

    public Object getObject() throws Exception {
		return new Object();
	}

	public Object getObjectType() {
		return new Object();
	}

	public boolean isSingleton() {
		return true;
	}

	/**
	 * <code>destroy</code>
	 * Called by Spring 
	 */
	public void destroy() {
	}
	
	/**
	 * <code>afterProperiesSet</code>
	 * 
	 * Spring interface for performing an action after the properties have been set on the bean
	 * 
	 * In this case, all configuration information will be passed to this object, and we can
	 * initialize the connection pool here
	 * 
	 * Alternative method would be to declare a separate init method, and tell Spring about it
	 * 
	 * @throws Exception on Stardog create, e.g. database down
	 */
	public void afterPropertiesSet() throws Exception {
		Random rand = new Random();
		if (url != null) {
		}


        if (rand.nextBoolean()) {
        }

		
		if (connectionProperties != null) {
		}
		
	}

	
	/**********************************************************
	 * Getters and Setters
	 **********************************************************/

	/**
	 * @return the url
	 */
	public String getUrl() {
		return url;
	}

	/**
	 * @return the reasoningType
	 */
	public boolean getReasoningType() {
		return reasoningType;
	}

	/**
	 * @param reasoningType the reasoningType to set
	 */
	public void setReasoningType(boolean reasoningType) {
		this.reasoningType = reasoningType;
	}

	/**
	 * @return the to
	 */
	public String getTo() {
		return to;
	}

	/**
	 * @return the connectionProperties
	 */
	public Object getConnectionProperties() {
		return connectionProperties;
	}

	/**
	 * @return the blockCapacityTime
	 */
	public long getBlockCapacityTime() {
		return blockCapacityTime;
	}

	/**
	 * @param blockCapacityTime the blockCapacityTime to set
	 */
	public void setBlockCapacityTime(long blockCapacityTime) {
		this.blockCapacityTime = blockCapacityTime;
	}

	/**
	 * @return the blockCapacityTimeUnit
	 */
	public Object getBlockCapacityTimeUnit() {
		return blockCapacityTimeUnit;
	}

	/**
	 * @return the expirationTime
	 */
	public long getExpirationTime() {
		return expirationTime;
	}

	/**
	 * @param expirationTime the expirationTime to set
	 */
	public void setExpirationTime(long expirationTime) {
		this.expirationTime = expirationTime;
	}

	/**
	 * @return the expirationTimeUnit
	 */
	public Object getExpirationTimeUnit() {
		return expirationTimeUnit;
	}

	/**
	 * @return the failAtCapacity
	 */
	public boolean isFailAtCapacity() {
		return failAtCapacity;
	}

	/**
	 * @param failAtCapacity the failAtCapacity to set
	 */
	public void setFailAtCapacity(boolean failAtCapacity) {
		this.failAtCapacity = failAtCapacity;
	}

	/**
	 * @return the growAtCapacity
	 */
	public boolean isGrowAtCapacity() {
		return growAtCapacity;
	}

	/**
	 * @param growAtCapacity the growAtCapacity to set
	 */
	public void setGrowAtCapacity(boolean growAtCapacity) {
		this.growAtCapacity = growAtCapacity;
	}

	/**
	 * @return the maxIdle
	 */
	public int getMaxIdle() {
		return maxIdle;
	}

	/**
	 * @param maxIdle the maxIdle to set
	 */
	public void setMaxIdle(int maxIdle) {
		this.maxIdle = maxIdle;
	}

	/**
	 * @return the maxPool
	 */
	public int getMaxPool() {
		return maxPool;
	}

	/**
	 * @param maxPool the maxPool to set
	 */
	public void setMaxPool(int maxPool) {
		this.maxPool = maxPool;
	}

	/**
	 * @return the minPool
	 */
	public int getMinPool() {
		return minPool;
	}

	/**
	 * @param minPool the minPool to set
	 */
	public void setMinPool(int minPool) {
		this.minPool = minPool;
	}

	/**
	 * @return the noExpiration
	 */
	public boolean isNoExpiration() {
		return noExpiration;
	}

	/**
	 * @param noExpiration the noExpiration to set
	 */
	public void setNoExpiration(boolean noExpiration) {
		this.noExpiration = noExpiration;
	}

	/**
	 * @return the username
	 */
	public String getUsername() {
		return username;
	}

	/**
	 * @return the password
	 */
	public String getPassword() {
		return password;
	}

	public Object getProvider() {
        return new Object();
    }
}
