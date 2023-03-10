package org.cyk.extension.file.impl.excel;

import java.io.IOException;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellValue;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.FormulaEvaluator;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.cyk.extension.file.api.excel.AbstractSheetReaderImpl;
import org.cyk.extension.file.api.excel.SheetGetter;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import lombok.Getter;

@ApplicationScoped
public class SheetReaderImpl extends AbstractSheetReaderImpl implements Serializable {
	private static final long serialVersionUID = 1L;

	@Inject @Getter private SheetGetter sheetGetter;
	
	@SuppressWarnings("deprecation")
	@Override
	public String[][] readFull(org.cyk.extension.file.api.excel.Sheet sheet,Integer fromColumnIndex,Integer numberOfColumns,Integer fromRowIndex,Integer numberOfRows) {
		if(sheet == null || sheet.getWorkBook() == null || sheet.getWorkBook().getValue() == null || sheet.getValue() == null)
			return null;
		Workbook __workbook__ = (Workbook) sheet.getWorkBook().getValue();
        Sheet __sheet__ = (Sheet) sheet.getValue();
    	FormulaEvaluator formulaEvaluator = __workbook__.getCreationHelper().createFormulaEvaluator();
    	
    	String[][] array = new String[numberOfRows][numberOfColumns];
        for (int i=0; i<numberOfRows; i++) {
        	Row row = __sheet__.getRow(i + fromRowIndex);
            if(row==null){
            	
            }else{
            	for (int j=0; j<numberOfColumns; j++) {
                    Cell cell = row.getCell(j+fromColumnIndex);
                    if(cell==null)
                    	array[i][j] = "";
                    else{
                    	CellValue cellValue;
                    	try {
							cellValue = formulaEvaluator.evaluate(cell);
						} catch (Exception exception) {
							cellValue = null;
							sheet.setCellEvaluationException(i, j, exception);
						}
                    	String stringValue;
                    	if(cellValue==null)
                    		stringValue = "";
                    	else switch(cellValue.getCellTypeEnum()){ 
                    	case BLANK : 
                    		stringValue = "";
                    		break;
                    	case NUMERIC : 
                    		if(DateUtil.isCellDateFormatted(cell))
                        		stringValue = cell.getDateCellValue().toString();
                        	else
                        		stringValue = new BigDecimal(cellValue.getNumberValue()).toString();
    	                	break;
                    	case STRING:
                    		stringValue = cellValue.getStringValue(); 
                    		break;
                    	case FORMULA:
                    		throw new RuntimeException("Must never happen! Cannot process a formula. Please change field to result of formula.("+i+","+j+")");
                    	default:
	    	                stringValue = cellValue.getStringValue() == null ? "" : cellValue.getStringValue().trim();
	    	                break;
	    	            }
                    	array[i][j] = stringValue == null || stringValue.isBlank() ? "" : stringValue;
                    }
                }
            }
        }    
        try {
        	__workbook__.close();
		} catch (IOException exception) {
			throw new RuntimeException(exception);
		}
        return array;
	}

	@Override
	protected String[][] readByBatch(org.cyk.extension.file.api.excel.Sheet sheet,Integer fromColumnIndex,Integer numberOfColumns,Integer fromRowIndex,Integer numberOfRows) {
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
		return Integer.valueOf(((Sheet) sheet.getValue()).getRow(0).getLastCellNum());
	}
	
	@Override
	protected Integer getMaximalNumberOfRows(org.cyk.extension.file.api.excel.Sheet sheet) {
		return ((Sheet) sheet.getValue()).getPhysicalNumberOfRows();
	}
}