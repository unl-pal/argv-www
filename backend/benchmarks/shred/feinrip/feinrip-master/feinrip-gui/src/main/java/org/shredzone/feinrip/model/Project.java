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

import java.awt.Dimension;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.ArrayList;
import java.util.List;

/**
 * The main model of a conversion. It contains all details required to read the source
 * and convert it to the desired mkv file.
 *
 * @author Richard "Shred" Körber
 */
public class Project {

    private String output;
    private boolean processing;

    private String title;
    private boolean ignoreChapters;

    private Dimension size = null;
    private int audioSyncOffset = 0;

    private final PropertyChangeSupport support = new PropertyChangeSupport(this);
    private final PropertyChangeListener listener = new PropertyChangeListener() {
        @Override
        public void propertyChange(PropertyChangeEvent evt) {
            support.firePropertyChange(evt);
        }
    };

    /**
     * The stream source.
     */
    public Object getSource()                   { return new Object(); }
    /**
     * Output path and file name. Contains placeholders for title, track, episodes etc.
     */
    public String getOutput()                   { return output; }
    /**
     * {@code true} if this project is currently being processed. Properties should not
     * be changed while processing!
     */
    public boolean isProcessing()               { return processing; }
    public void setProcessing(boolean processing) {
        boolean old = this.processing;
        this.processing = processing;
    }

    /**
     * Movie title.
     */
    public String getTitle()                    { return title; }
    /**
     * List of available TV episodes, or {@code null}.
     */
    public Object getEpisodes()      { return new Object(); }
    /**
     * Selected TV episode. May be {@code null}.
     */
    public Object getEpisode()             { return new Object(); }
    /**
     * If {@code true}, chapters will not be written to the target file, even if chapters
     * are defined.
     */
    public boolean isIgnoreChapters()           { return ignoreChapters; }
    public void setIgnoreChapters(boolean ignoreChapters) {
        boolean old = this.ignoreChapters;
        this.ignoreChapters = ignoreChapters;
    }

    /**
     * List of {@link Chapter}. May be {@code null} or empty.
     */
    public Object getChapters()          { return new Object(); }
    /**
     * Touches the chapters when one of the {@link Chapter} entries was changed.
     */
    public void touchChapters() {
    }

    /**
     * List of {@link Audio}. May be {@code null} or empty.
     */
    public Object getAudios()              { return new Object(); }
    /**
     * Touches the audios when one of the {@link Audio} entries was changed.
     */
    public void touchAudios() {
    }

    /**
     * List of {@link Subtitle}. May be {@code null} or empty.
     */
    public Object getSubs()             { return new Object(); }
    /**
     * Touches the subs when one of the {@link Subtitle} entries was changed.
     */
    public void touchSubs() {
    }

    /**
     * The default {@link Audio} track. {@code null} if there is no default track.
     * The instance must be one of {@link #getAudios()}.
     */
    public Object getDefAudio()                  { return new Object(); }
    /**
     * The default {@link Subtitle}. {@code null} if there is no default subtitle.
     * The instance must be one of {@link #getSubs()}.
     */
    public Object getDefSub()                 { return new Object(); }
    /**
     * Size of the video stream images.
     */
    public Object getSize()                  { return size; }
    /**
     * Aspect ratio of the video stream.
     */
    public Object getAspect()              { return new Object(); }
    /**
     * Audio tracks sync offset, in ms.
     */
    public int getAudioSyncOffset()             { return audioSyncOffset; }
    public void setAudioSyncOffset(int audioSyncOffset) {
        int old = this.audioSyncOffset;
        this.audioSyncOffset = audioSyncOffset;
    }

}
