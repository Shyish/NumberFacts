package com.zdvdev.numberfacts.datamodel.model;

import com.google.gson.annotations.Expose;

/**
 * Created with Android Studio.
 *
 * @author Shyish
 *         Date: 28/03/14
 */
public class ResponseFact {

	@Expose
	private String text;
	@Expose
	private Boolean found;

	public ResponseFact(String text) {
		this.text = text;
		this.found = true;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public Boolean getFound() {
		return found;
	}

	public void setFound(Boolean found) {
		this.found = found;
	}

}
