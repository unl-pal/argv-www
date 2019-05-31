/*
 * feinrip
 *
 * Copyright (C) 2014 Richard "Shred" Körber
 *   https://github.com/shred/feinrip
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.
 */
package org.shredzone.feinrip.dvb.si;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * A single Event Information from the Event Information Table (EIT).
 *
 * @author Richard "Shred" Körber
 * @see <a href="http://www.etsi.org/deliver/etsi_en/300400_300499/300468/01.12.01_40/en_300468v011201o.pdf">ETSI EN 300 468, Chapter 5.2.4</a>
 */
public class EventInformation {

    private int eventId;
    private Date startTime;
    private int duration;
    private boolean freeCAmode;
    public Object getDescriptors()    { return new Object();  }
    public int getDuration()                    { return duration; }
    public void setDuration(int duration)       { this.duration = duration; }

    public int getEventId()                     { return eventId; }
    public void setEventId(int eventId)         { this.eventId = eventId; }

    public boolean isFreeCAmode()               { return freeCAmode; }
    public void setFreeCAmode(boolean freeCAmode) { this.freeCAmode = freeCAmode; }

    public Object getRunningStatus()     { return new Object(); }
    public Object getStartTime()                  { return startTime; }
    public String toString() {
        StringBuilder sb = new StringBuilder();
        return "";
    }

}
