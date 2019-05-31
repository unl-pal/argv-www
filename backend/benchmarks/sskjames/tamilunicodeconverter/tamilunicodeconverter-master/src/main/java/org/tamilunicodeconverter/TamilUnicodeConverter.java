/*
 * Licensed to the http://code.google.com/p/tamilunicodeconverter under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.tamilunicodeconverter;

import java.io.File;
import java.io.IOException;
import java.util.StringTokenizer;

/**
 * 
 * @author James Selvakumar
 *
 */
public class TamilUnicodeConverter
{
	public static final int MAX_FILE_NAME_LENGTH = 100;
	public static final int MAX_FILE_NAME_TOKENS = 4;
	private int maxFileNameLength = MAX_FILE_NAME_LENGTH;
	private int maxFileNameTokens = MAX_FILE_NAME_TOKENS;
	private boolean createOutputFileNameFromContent;
	private int convertedFilesCount;

	public TamilUnicodeConverter()
	{
	}

	public TamilUnicodeConverter(boolean createOutputFileNameFromContent)
	{
		this.createOutputFileNameFromContent = createOutputFileNameFromContent;
	}

	public boolean isCreateOutputFileNameFromContent()
	{
		return createOutputFileNameFromContent;
	}

	public void setCreateOutputFileNameFromContent(
			boolean createOutputFileNameFromContent)
	{
		this.createOutputFileNameFromContent = createOutputFileNameFromContent;
	}

	public int getMaxFileNameLength()
	{
		return maxFileNameLength;
	}

	public void setMaxFileNameLength(int maxFileNameLength)
	{
		this.maxFileNameLength = maxFileNameLength;
	}

	public int getMaxFileNameTokens()
	{
		return maxFileNameTokens;
	}

	public void setMaxFileNameTokens(int maxFileNameTokens)
	{
		this.maxFileNameTokens = maxFileNameTokens;
	}
	
	public int getConvertedFilesCount()
	{
		return convertedFilesCount;
	}
	
	protected void resetConvertedFilesCount()
	{
		convertedFilesCount = 0;
	}
}
