package org.activecheck.plugin.reporter.graylog.api;

import java.io.IOException;

public class GraylogApiStreamObject {
	private String id = null;
	private String title = null;
	private String description = null;
	private String created = null;
	private String creator = null;
	private boolean disabled = false;
	private long numAlerts = 0;

	public String getId() {
		return id;
	}

	public String getTitle() {
		return title;
	}

	public String getDescription() {
		return description;
	}

	public String getCreated() {
		return created;
	}

	public String getCreator() {
		return creator;
	}

	public boolean isDisabled() {
		return disabled;
	}

	public void setNumAlerts(long numAlerts) {
		this.numAlerts = numAlerts;
	}

	public long getNumAlerts() {
		return numAlerts;
	}
}
