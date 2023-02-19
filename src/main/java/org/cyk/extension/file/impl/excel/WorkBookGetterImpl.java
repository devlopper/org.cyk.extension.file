package org.cyk.extension.file.impl.excel;

import java.io.File;
import java.io.InputStream;
import java.io.Serializable;

import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.cyk.extension.file.api.excel.AbstractWorkBookGetterImpl;
import org.cyk.extension.file.api.excel.WorkBook;

import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class WorkBookGetterImpl extends AbstractWorkBookGetterImpl implements Serializable {
	private static final long serialVersionUID = 1L;

	@Override
	protected WorkBook __get__(InputStream inputStream) {
		try {
			return new WorkBook((WorkbookFactory.create(inputStream)));
		} catch (Exception exception) {
			throw new RuntimeException(exception);
		}
	}

	@Override
	protected WorkBook __get__(File file) {
		try {
			return new WorkBook((WorkbookFactory.create(file)));
		} catch (Exception exception) {
			throw new RuntimeException(exception);
		}
	}

}
