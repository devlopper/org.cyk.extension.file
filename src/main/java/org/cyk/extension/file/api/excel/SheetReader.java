package org.cyk.extension.file.api.excel;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

public interface SheetReader {

	String[][] read(Arguments arguments);
	
	String[][] read(Sheet sheet,Integer fromColumnIndex,Integer numberOfColumns,Integer fromRowIndex,Integer numberOfRows);
	String[][] read(Sheet sheet);

	/**/
	
	@Getter @Setter @Accessors(chain=true)
	public static class Arguments {
		private SheetGetter.Arguments sheetGetterArguments = new SheetGetter.Arguments();
		private Sheet sheet;
		private Integer fromColumnIndex,numberOfColumns,fromRowIndex,numberOfRows;
	}	
}