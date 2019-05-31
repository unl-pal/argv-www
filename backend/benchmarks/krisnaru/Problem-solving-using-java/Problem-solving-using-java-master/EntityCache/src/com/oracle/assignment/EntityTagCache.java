package com.oracle.assignment;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

public final class EntityTagCache  {
	
	//private variables
	private static final Logger LOGGER = Logger.getLogger(EntityTagCache.class.getName());
	private String[][] cacheObj = null;
	
	/*
	 * Constructor takes CSV file and no of entities
	 */
	public EntityTagCache(String csvFile, int noOfEntities){
		LOGGER.log(Level.INFO, String.format("EntityTagCache is getting initialized for cacheFile:%s and noOfEntities:%d", csvFile, noOfEntities));
		long used = Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory();
		System.out.println("Used memory="+used);
		
		//Initiaze the cache array for storing the tags
		this.cacheObj = new String[noOfEntities][];
		this.initializeTagsFromFile(csvFile);
		
		used = Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory();
		System.out.println("Used memory="+used);
		
		//Clear the unwanted memory from Heap
		System.gc();
		
		used = Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory();
		System.out.println("Used memory="+used);
		
		LOGGER.log(Level.INFO, "EntityTagCache is initialized successfully");
	}
	
	/*
	 * Initializing the tags from CSV file.
	 * Assumption: Entity Id starts from 0 to max entityId
	 * There will be 100M entities can exists in CSV file.
	 */
	private void initializeTagsFromFile(String csvFile){
		BufferedReader br = null;
		String line = "";
		String cvsSplitBy = ",";
		
		try {
			br = new BufferedReader(new FileReader(csvFile));
			
			while ((line = br.readLine()) != null) {
			     // use comma as separator
				String[] entitytags = line.split(cvsSplitBy);
				
				if(entitytags.length <= 1){
					//No tags found, so continue to next entity
					continue;
				}
				
				int entityId = 0;
				try {
					entityId = Integer.parseInt(entitytags[0]);
				} catch(NumberFormatException ex) {
					//Entity is not an integer, so error case
					LOGGER.warning(String.format("Unable to parse CSV line:%s, exception:%s", line, ex.getMessage()));
					continue;
				}
				if(entityId >= this.cacheObj.length){
					LOGGER.warning(String.format("Ignoring entityId:%s as it exceeds cache size:%d", entityId, this.cacheObj.length));
					//Filtering out extra tags
					continue;
				}
				
				String[] tags = new String[entitytags.length-1];
				for(int i = 0; i<entitytags.length-1; ++i){
					//Pushing strings to JVM StringTable
					tags[i] = entitytags[i+1].intern();
				}
				
				//Setting the tags to cache object
				this.cacheObj[entityId] = tags;
				if(entityId %100000 == 0){
					System.gc();
				}
			}
	 
		} catch (FileNotFoundException ex) {	
			LOGGER.severe(String.format("FileNotFoundException: Unable to parse CSV file:%s, exception:%s", csvFile, ex.getMessage()));
		} catch (IOException ex) {
			LOGGER.severe(String.format("IOException: Unable to parse CSV file:%s, exception:%s", csvFile, ex.getMessage()));
		} finally {
			if (br != null) {
				try {
					br.close();
				} catch (IOException ex) {
					LOGGER.severe(String.format("IOException: Unable to close CSV file:%s, exception:%s", csvFile, ex.getMessage()));
				}
			}
		}
	}
	
	/*
	 * Gettting the Tags for specified Entity. 
	 * There can be 1000s of requests from multiple threads.
	 * This API should complete few 100 nano seconds
	 */
	public String[] getTags(int entityId){
		if(entityId < 0 || entityId >= this.cacheObj.length){
			LOGGER.warning(String.format("getTags:Invalid entity Id:%d is passed", entityId));
			throw new IllegalArgumentException("Invalid entity Id is passed:"+entityId);
		}
		return this.cacheObj[entityId];
	}
	
	/*
	 * Updating tags for specified entity.
	 * This operation is rare.
	 */
	public void updateTags(int entityId, String[] updateTags){
		if(entityId < 0 || entityId >= this.cacheObj.length){
			LOGGER.warning(String.format("updateTags:Invalid entity Id:%d is passed", entityId));
			throw new IllegalArgumentException("Invalid entity Id is passed:"+entityId);
		}
		
		String[] tags = new String[updateTags.length];
		for(int i=0; i<updateTags.length; ++i){
			//Pushing strings to JVM StringTable
			tags[i] = updateTags[i].intern();
		}
		
		//Threadsafe code
		//synchronized(this.cacheObj)
		{
			this.cacheObj[entityId] = tags;
		}
	}
}
