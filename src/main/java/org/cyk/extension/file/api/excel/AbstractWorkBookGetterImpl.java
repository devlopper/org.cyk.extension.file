package org.cyk.extension.file.api.excel;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.Serializable;

public abstract class AbstractWorkBookGetterImpl implements WorkBookGetter,Serializable {
	private static final long serialVersionUID = 1L;

	@Override
	public WorkBook get(Arguments arguments) {
		if(arguments == null)
			return null;
		Boolean batchable = arguments.getBatchable();
		if(arguments.getFile() != null)
			return __get__(arguments.getFile(),batchable);
		InputStream inputStream = arguments.getInputStream();
		if(inputStream == null && arguments.getBytes() != null && arguments.getBytes().length > 0)
			inputStream = new ByteArrayInputStream(arguments.getBytes());
		if(inputStream == null && arguments.getFileName() != null && !arguments.getFileName().isBlank())
			try {
				inputStream = new FileInputStream(arguments.getFileName());
			} catch (Exception exception) {
				throw new RuntimeException(exception);
			}
		if(inputStream == null)
			throw new RuntimeException(NO_INPUT_STREAM_MESSAGE);
		return __get__(inputStream,batchable);
	}
	
	@Override
	public WorkBook get(InputStream inputStream) {
		return get(new Arguments().setInputStream(inputStream));
	}
	
	protected abstract WorkBook __get__(InputStream inputStream,Boolean batchable);
	protected abstract WorkBook __get__(File file,Boolean batchable);
	
	@Override
	public WorkBook get(byte[] bytes) {
		return get(new Arguments().setBytes(bytes));
	}
	
	@Override
	public WorkBook get(File file) {
		return get(new Arguments().setFile(file));
	}
	
	public WorkBook get(String fileName) {
		return get(new Arguments().setFileName(fileName));
	}
	
	public static final String NO_INPUT_STREAM_MESSAGE = "Inputstream cannot be got";
}