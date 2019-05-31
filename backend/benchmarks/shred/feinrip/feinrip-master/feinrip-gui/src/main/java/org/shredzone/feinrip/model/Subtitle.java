/*
 * feinrip
 *
 * Copyright (C) 2014 Richard "Shred" Körber
 *   https://github.com/shred/feinrip
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.
 */
package org.shredzone.feinrip.model;

/**
 * A subtitle.
 *
 * @author Richard "Shred" Körber
 */
public class Subtitle {

    private int index;
    private String streamId;
    private int vts;
    private boolean enabled;

    /**
     * Subtitle index, counted from 1.
     */
    public int getIndex()                       { return index; }
    public void setIndex(int index)             { this.index = index; }

    /**
     * Subtitle stream id, as hex number.
     */
    public String getStreamId()                 { return streamId; }
    /**
     * Subtitle format.
     */
    public Object getFormat()           { return new Object(); }
    /**
     * Subtitle stream type.
     */
    public Object getType()               { return new Object(); }
    /**
     * Subtitle language.
     */
    public Object getLanguage()               { return new Object(); }
    /**
     * For DVD style sources: number of the corresponding VTS file.
     */
    public int getVts()                         { return vts; }
    public void setVts(int vts)                 { this.vts = vts; }

    /**
     * {@code true}: subtitle will be multiplexed to the target mkv.
     */
    public boolean isEnabled()                  { return enabled; }
    public void setEnabled(boolean enabled)     { this.enabled = enabled; }

}
