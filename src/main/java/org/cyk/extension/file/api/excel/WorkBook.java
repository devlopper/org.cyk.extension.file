package org.cyk.extension.file.api.excel;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter @Setter @Accessors(chain=true)
public class WorkBook implements Serializable {

	private Object value;

	public WorkBook(Object value) {
		this.value = value;
	}
}