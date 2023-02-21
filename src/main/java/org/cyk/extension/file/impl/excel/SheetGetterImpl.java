package org.cyk.extension.file.impl.excel;

import java.io.Serializable;

import org.apache.poi.ss.usermodel.Workbook;
import org.cyk.extension.file.api.excel.AbstractSheetGetterImpl;
import org.cyk.extension.file.api.excel.Sheet;
import org.cyk.extension.file.api.excel.WorkBook;
import org.cyk.extension.file.api.excel.WorkBookGetter;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

@ApplicationScoped
public class SheetGetterImpl extends AbstractSheetGetterImpl implements Serializable {
	private static final long serialVersionUID = 1L;

	@Inject private WorkBookGetter workBookGetter;
	
	@Override
	public Sheet get(Arguments arguments) {
		if(arguments == null)
			return null;
		WorkBook workBook = arguments.getWorkBook();
		if(workBook == null && arguments.getWorkBookGetterArguments() != null)
			workBook = workBookGetter.get(arguments.getWorkBookGetterArguments());
		if(workBook != null && workBook.getValue() != null) {
			if(arguments.getName() != null && !arguments.getName().isBlank())
				return new Sheet(workBook,((Workbook)workBook.getValue()).getSheet(arguments.getName()));
			if(arguments.getIndex() != null)
				return new Sheet(workBook,((Workbook)workBook.getValue()).getSheetAt(arguments.getIndex()));
		}
		return null;
	}
	
	@Override
	public Sheet get(WorkBook workBook, String name) {
		return get(new Arguments().setWorkBook(workBook).setName(name));
	}
	
	@Override
	public Sheet get(WorkBook workBook, Integer index) {
		return get(new Arguments().setWorkBook(workBook).setIndex(index));
	}

}
