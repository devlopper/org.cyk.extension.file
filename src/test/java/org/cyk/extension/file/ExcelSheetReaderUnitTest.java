package org.cyk.extension.file;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.File;
import java.io.FileInputStream;

import org.cyk.extension.file.api.excel.AbstractWorkBookGetterImpl;
import org.cyk.extension.file.api.excel.SheetGetter;
import org.cyk.extension.file.api.excel.SheetReader;
import org.cyk.extension.file.api.excel.WorkBookGetter;
import org.jboss.weld.junit5.WeldInitiator;
import org.jboss.weld.junit5.WeldJunit5Extension;
import org.jboss.weld.junit5.WeldSetup;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import jakarta.inject.Inject;

//@EnableWeld
@ExtendWith(WeldJunit5Extension.class)
public class ExcelSheetReaderUnitTest {
	
	@WeldSetup
	  public WeldInitiator weld = WeldInitiator.of(WeldInitiator.createWeld()
	                                           .addPackage(Boolean.TRUE,Configuration.class)
	                                           );
	
	@Inject private SheetReader sheetReader;
	
	@Test
	public void read_noInputStream() throws Exception {
		RuntimeException exception = Assertions.assertThrows(RuntimeException.class, () -> {
			sheetReader.read(new SheetReader.Arguments());
	  });

	  Assertions.assertEquals(AbstractWorkBookGetterImpl.NO_INPUT_STREAM_MESSAGE, exception.getMessage());
	}
	
	@Test
	public void read() throws Exception {
		String[][] array = sheetReader.read(new SheetReader.Arguments().setSheetGetterArguments(new SheetGetter.Arguments().setWorkBookGetterArguments(new WorkBookGetter.Arguments().setInputStream(new FileInputStream(
				"src/test/resources/org/cyk/extension/file/api/excel/01.xlsx")))));
		assertThat(array[0][0]).isEqualTo("Column01");
		assertThat(array[0][1]).isEqualTo("Column02");
		assertThat(array[0][2]).isEqualTo("Column03");
		assertThat(array[0][3]).isBlank();
		assertThat(array[0][4]).isEqualTo("Column04");
	}
	
	@Test
	public void read_mergedCells() throws Exception {
		String[][] array = sheetReader.read(new SheetReader.Arguments().setSheetGetterArguments(new SheetGetter.Arguments().setWorkBookGetterArguments(new WorkBookGetter.Arguments().setInputStream(new FileInputStream(
				"src/test/resources/org/cyk/extension/file/api/excel/merged_01.xlsx")))));
		assertThat(array[0][0]).isEqualTo("Column01");
		assertThat(array[0][1]).isEqualTo("Column02");
		assertThat(array[0][2]).isEqualTo("Column03");
		
		assertThat(array[1][0]).isEqualTo("A");
		assertThat(array[1][1]).isEqualTo("A1");
		assertThat(array[1][2]).isEqualTo("A11");
		
		assertThat(array[2][0]).isBlank();
		assertThat(array[2][1]).isBlank();
		assertThat(array[2][2]).isEqualTo("A12");
	}
	
	@Test
	public void read_section_105_cesec_ok() throws Exception {
		String[][] array = sheetReader.read(new SheetReader.Arguments().setNumberOfColumns(5).setSheetGetterArguments(new SheetGetter.Arguments()
				.setWorkBookGetterArguments(new WorkBookGetter.Arguments().setInputStream(new FileInputStream("src/test/resources/org/cyk/extension/file/api/excel/Section 105 CESEC OK.xls")))));
		assertThat(array[3][0]).isEqualTo("BUDGET 2020 - RATTACHEMENT DES ACTIVITES AUX UNITES ADMINISTRATIVES");
	}
	
	@Test
	public void all_250000() throws Exception {
		String[][] arrays = sheetReader.read(new SheetReader.Arguments().setNumberOfColumns(2).setSheetGetterArguments(new SheetGetter.Arguments()
				.setWorkBookGetterArguments(new WorkBookGetter.Arguments()
						.setFile(new File("src/test/resources/org/cyk/extension/file/api/excel/chargements_all01.xlsx"))
						)));
		assertThat(arrays.length).isEqualTo(257699);
	}
}