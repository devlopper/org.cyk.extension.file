package org.cyk.extension.file.api.excel;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

public interface SheetGetter {

	Sheet get(Arguments arguments);
	
	Sheet get(WorkBook workBook,String name);
	
	Sheet get(WorkBook workBook,Integer index);
	
	/**/
	
	@Getter @Setter @Accessors(chain=true)
	public static class Arguments {
		private WorkBookGetter.Arguments workBookGetterArguments = new WorkBookGetter.Arguments();
		private WorkBook workBook;
		private String name;
		private Integer index = 0;
	}	
}