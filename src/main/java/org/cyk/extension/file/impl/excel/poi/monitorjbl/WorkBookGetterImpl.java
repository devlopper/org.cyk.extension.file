package org.cyk.extension.file.impl.excel.poi.monitorjbl;

import java.io.File;
import java.io.InputStream;
import java.io.Serializable;

import org.apache.poi.ss.usermodel.Workbook;
import org.cyk.extension.file.api.excel.AbstractWorkBookGetterImpl;
import org.cyk.extension.file.api.excel.WorkBook;

import com.monitorjbl.xlsx.StreamingReader;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.inject.Alternative;

@ApplicationScoped @Alternative
public class WorkBookGetterImpl extends AbstractWorkBookGetterImpl implements Serializable {
	private static final long serialVersionUID = 1L;

	@Override
	protected WorkBook __get__(InputStream inputStream) {
		try {
			Workbook workbook = StreamingReader.builder().rowCacheSize(100).bufferSize(4096).open(inputStream);
			return new WorkBook(workbook);
		} catch (Exception exception) {
			throw new RuntimeException(exception);
		}
	}

	@Override
	protected WorkBook __get__(File file) {
		try {
			return new WorkBook(StreamingReader.builder().rowCacheSize(100).bufferSize(4096).open(file));
		} catch (Exception exception) {
			throw new RuntimeException(exception);
		}
	}

}