package org.cyk.extension.file.api.excel;

import java.io.Serializable;

public abstract class AbstractSheetReaderImpl implements SheetReader,Serializable {
	private static final long serialVersionUID = 1L;

	@jakarta.inject.Inject private SheetGetter sheetGetter;
	
	@Override
	public String[][] read(Arguments arguments) {
		if(arguments == null)
			return null;
		Sheet sheet = arguments.getSheet();
		if(sheet == null && arguments.getSheetGetterArguments() != null)
			sheet = sheetGetter.get(arguments.getSheetGetterArguments());
		if(sheet == null || sheet.getWorkBook() == null || sheet.getWorkBook().getValue() == null || sheet.getValue() == null)
			return null;
		Integer fromColumnIndex = arguments.getFromColumnIndex() == null ? 0 : arguments.getFromColumnIndex();
		Integer numberOfColumns = arguments.getNumberOfColumns();
		Integer fromRowIndex = arguments.getFromRowIndex() == null ? 0 : arguments.getFromRowIndex();
		Integer numberOfRows = arguments.getNumberOfRows();
		Boolean isDynamic = Boolean.TRUE.equals(arguments.getSheetGetterArguments().getWorkBookGetterArguments().getIsDynamic());
		if(!isDynamic) {
			if(numberOfColumns == null) {
	    		Integer maximalNumberOfColumns = getMaximalNumberOfColumns(sheet);
	    		numberOfColumns = maximalNumberOfColumns == null ? null : maximalNumberOfColumns - fromColumnIndex;
	    		if(numberOfColumns == null || numberOfColumns < 1)
	    			return null;
	    	}
			
			if(numberOfRows == null) {
	    		Integer maximalNumberOfRows = getMaximalNumberOfRows(sheet);
	    		numberOfRows = maximalNumberOfRows == null ? null : maximalNumberOfRows - fromRowIndex;
	    		if(numberOfRows == null || numberOfRows < 1)
	    			return null;	
	    	}
		}
		return __read__(sheet, fromColumnIndex, numberOfColumns, fromRowIndex, numberOfRows);
	}
	
	@Override
	public String[][] read(Sheet sheet, Integer fromColumnIndex, Integer numberOfColumns, Integer fromRowIndex,Integer numberOfRows) {
		if(sheet == null || sheet.getWorkBook() == null || sheet.getWorkBook().getValue() == null || sheet.getValue() == null)
			return null;
		if(fromColumnIndex == null)
			fromColumnIndex = 0;
		if(numberOfColumns == null) {
    		Integer maximalNumberOfColumns = getMaximalNumberOfColumns(sheet);
    		numberOfColumns = maximalNumberOfColumns == null ? null : maximalNumberOfColumns - fromColumnIndex;
    		if(numberOfColumns == null || numberOfColumns < 1)
    			return null;
    	}
		
		if(fromRowIndex == null)
			fromRowIndex = 0;
		if(numberOfRows == null) {
    		Integer maximalNumberOfRows = getMaximalNumberOfRows(sheet);
    		numberOfRows = maximalNumberOfRows == null ? null : maximalNumberOfRows - fromRowIndex;
    		if(numberOfRows == null || numberOfRows < 1)
    			return null;	
    	}
		return read(new Arguments().setSheet(sheet).setFromColumnIndex(fromColumnIndex).setNumberOfColumns(numberOfColumns).setFromRowIndex(fromRowIndex).setNumberOfRows(numberOfRows));
	}
	
	protected abstract Integer getMaximalNumberOfColumns(Sheet sheet);
	protected abstract Integer getMaximalNumberOfRows(Sheet sheet);
	
	protected abstract String[][] __read__(Sheet sheet, Integer fromColumnIndex, Integer numberOfColumns, Integer fromRowIndex,Integer numberOfRows);
	
	@Override
	public String[][] read(Sheet sheet) {
		return read(sheet,null,null,null,null);
	}
	
}