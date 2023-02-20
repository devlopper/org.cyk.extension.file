package org.cyk.extension.file.impl.excel.poi.monitorjbl;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.cyk.extension.file.api.excel.AbstractSheetReaderImpl;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.inject.Alternative;

@ApplicationScoped @Alternative
public class SheetReaderImpl extends AbstractSheetReaderImpl implements Serializable {
	private static final long serialVersionUID = 1L;

	@Override
	public String[][] __read__(org.cyk.extension.file.api.excel.Sheet sheet,Integer fromColumnIndex,Integer numberOfColumns,Integer fromRowIndex,Integer numberOfRows) {
		if(sheet == null || sheet.getWorkBook() == null || sheet.getWorkBook().getValue() == null || sheet.getValue() == null)
			return null;
		Sheet __sheet__ = (Sheet) sheet.getValue();
        
		List<List<String>> stringsList = new ArrayList<>();
		numberOfRows = 0;
		for (Row row : __sheet__) {
			Integer columnCount = 0;
			List<String> list = new ArrayList<>();
			for (Cell cell : row) {
				while(cell.getColumnIndex()>list.size())
					list.add("");
				list.add(cell.getStringCellValue());
				columnCount++;
			}
			
			if(numberOfColumns == null || list.size() > numberOfColumns)
				numberOfColumns = list.size();
			stringsList.add(list);
			numberOfRows++;
		}
		
    	String[][] array = new String[numberOfRows][numberOfColumns];
    	
        for (int i=0; i<numberOfRows; i++) {
        	List<String> row = stringsList.get(i + fromRowIndex);
        	
            if(row==null){
            	
            }else{
            	for (int j=0; j<numberOfColumns; j++) {
                    String value = j+fromColumnIndex < row.size() ? row.get(j+fromColumnIndex) : null;
                    if(value==null)
                    	array[i][j] = "";
                    else{
                    	array[i][j] = value;
                    }
                }
            }
        }    
        return array;
	}

	@Override
	protected Integer getMaximalNumberOfColumns(org.cyk.extension.file.api.excel.Sheet sheet) {
		throw new RuntimeException("Cannot be accesed");
	}
	
	@Override
	protected Integer getMaximalNumberOfRows(org.cyk.extension.file.api.excel.Sheet sheet) {
		throw new RuntimeException("Cannot be accesed");
	}
}
