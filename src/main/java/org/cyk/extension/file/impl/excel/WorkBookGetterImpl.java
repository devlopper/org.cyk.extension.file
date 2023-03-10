package org.cyk.extension.file.impl.excel;

import java.io.File;
import java.io.InputStream;
import java.io.Serializable;

import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.cyk.extension.file.api.excel.AbstractWorkBookGetterImpl;
import org.cyk.extension.file.api.excel.WorkBook;

import com.monitorjbl.xlsx.StreamingReader;

import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class WorkBookGetterImpl extends AbstractWorkBookGetterImpl implements Serializable {
	private static final long serialVersionUID = 1L;

	@Override
	protected WorkBook __get__(InputStream inputStream,Boolean batchable) {
		try {
			Workbook workbook = Boolean.TRUE.equals(batchable) ?StreamingReader.builder().rowCacheSize(100).bufferSize(4096).open(inputStream) : WorkbookFactory.create(inputStream);
			return new WorkBook(workbook);
		} catch (Exception exception) {
			throw new RuntimeException(exception);
		}
	}

	@Override
	protected WorkBook __get__(File file,Boolean batchable) {
		try {
			return new WorkBook(Boolean.TRUE.equals(batchable) ? StreamingReader.builder().rowCacheSize(100).bufferSize(4096).open(file) : WorkbookFactory.create(file));
		} catch (Exception exception) {
			throw new RuntimeException(exception);
		}
	}

}
