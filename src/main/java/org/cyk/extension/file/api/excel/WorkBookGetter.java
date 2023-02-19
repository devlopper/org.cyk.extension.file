package org.cyk.extension.file.api.excel;

import java.io.File;
import java.io.InputStream;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

public interface WorkBookGetter {

	WorkBook get(Arguments arguments);
	
	WorkBook get(InputStream inputStream);
	WorkBook get(byte[] bytes);
	WorkBook get(File file);
	WorkBook get(String fileName);
	
	/**/
	
	@Getter @Setter @Accessors(chain=true)
	public static class Arguments {
		private InputStream inputStream;
		private byte[] bytes;
		private File file;
		private String fileName;
	}	
}