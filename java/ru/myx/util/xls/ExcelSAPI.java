package ru.myx.util.xls;

import java.io.File;

import jxl.Workbook;
import ru.myx.ae3.base.BaseMessage;
import ru.myx.ae3.binary.TransferBuffer;
import ru.myx.ae3.binary.TransferCopier;
import ru.myx.io.DataInputByteArrayFast;

/**
 * @author myx
 * 
 *         Window - Preferences - Java - Code Style - Code Templates
 */
public class ExcelSAPI {
	/**
	 * @return change
	 * @throws Exception
	 */
	public static final WorkbookChange createWorkbook() throws Exception {
		return new WorkbookChange();
	}
	
	/**
	 * @param workbook
	 * @return change
	 * @throws Exception
	 */
	public static final WorkbookChange createWorkbookChange(final Workbook workbook) throws Exception {
		return new WorkbookChange( workbook );
	}
	
	/**
	 * @param message
	 * @return workbook
	 * @throws Exception
	 */
	public static final Workbook getWorkbook(final BaseMessage message) throws Exception {
		return Workbook.getWorkbook( message.toBinary().getBinary().nextInputStream() );
	}
	
	/**
	 * @param bytes
	 * @return workbook
	 * @throws Exception
	 */
	public static final Workbook getWorkbook(final byte[] bytes) throws Exception {
		return Workbook.getWorkbook( new DataInputByteArrayFast( bytes ) );
	}
	
	/**
	 * @param file
	 * @return workbook
	 * @throws Exception
	 */
	public static final Workbook getWorkbook(final File file) throws Exception {
		return Workbook.getWorkbook( file );
	}
	
	/**
	 * @param buffer
	 * @return workbook
	 * @throws Exception
	 */
	public static final Workbook getWorkbook(final TransferBuffer buffer) throws Exception {
		return Workbook.getWorkbook( buffer.toInputStream() );
	}
	
	/**
	 * @param copier
	 * @return workbook
	 * @throws Exception
	 */
	public static final Workbook getWorkbook(final TransferCopier copier) throws Exception {
		return Workbook.getWorkbook( copier.nextInputStream() );
	}
}
