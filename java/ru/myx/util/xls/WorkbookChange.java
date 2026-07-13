/*
 * Created on 17.06.2005
 */
package ru.myx.util.xls;

import java.io.File;
import java.io.IOException;

import jxl.Range;
import jxl.Sheet;
import jxl.Workbook;
import jxl.biff.DisplayFormat;
import jxl.format.Colour;
import jxl.write.DateFormat;
import jxl.write.NumberFormats;
import jxl.write.WritableCell;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;
import ru.myx.ae3.binary.Transfer;
import ru.myx.ae3.binary.TransferCollector;
import ru.myx.ae3.binary.TransferCopier;

/**
 * @author myx
 * 
 */
public class WorkbookChange extends WritableWorkbook {
	private final TransferCollector	collector;
	
	private final WritableWorkbook	workbook;
	
	/**
	 * @throws Exception
	 */
	WorkbookChange() throws Exception {
		this.collector = Transfer.createCollector();
		this.workbook = Workbook.createWorkbook( this.collector.getOutputStream() );
	}
	
	/**
	 * @throws Exception
	 */
	WorkbookChange(final Workbook workbook) throws Exception {
		this.collector = Transfer.createCollector();
		this.workbook = Workbook.createWorkbook( this.collector.getOutputStream(), workbook );
	}
	
	@Override
	public void addNameArea(
			final String name,
			final WritableSheet sheet,
			final int firstCol,
			final int firstRow,
			final int lastCol,
			final int lastRow) {
		this.workbook.addNameArea( name, sheet, firstCol, firstRow, lastCol, lastRow );
	}
	
	/**
	 * @return copier
	 * @throws Exception
	 */
	public TransferCopier buildWorkbook() throws Exception {
		this.workbook.write();
		this.workbook.close();
		return this.collector.toBinary();
	}
	
	@Override
	public void close() throws IOException, WriteException {
		assert false : "ExcelWorkbook.WorkbookChange is not used this way!";
		this.workbook.close();
	}
	
	@Override
	public void copySheet(final int s, final String name, final int index) {
		this.workbook.copySheet( s, name, index );
	}
	
	@Override
	public void copySheet(final String s, final String name, final int index) {
		this.workbook.copySheet( s, name, index );
	}
	
	/**
	 * @return format
	 */
	@SuppressWarnings("static-method")
	public WorkbookCellFormat createCellFormat() {
		return new WorkbookCellFormat();
	}
	
	/**
	 * @param format
	 * @return format
	 * @throws Exception
	 */
	@SuppressWarnings("static-method")
	public WorkbookCellFormat createCellFormatDate(final String format) throws Exception {
		return new WorkbookCellFormat( new DateFormat( format == null
				? "yyyy/MM/dd HH:mm"
				: format ) );
	}
	
	/**
	 * @return format
	 * @throws Exception
	 */
	@SuppressWarnings("static-method")
	public WorkbookCellFormat createCellFormatFloating() throws Exception {
		return new WorkbookCellFormat( NumberFormats.FLOAT );
	}
	
	/**
	 * @return format
	 * @throws Exception
	 */
	@SuppressWarnings("static-method")
	public WorkbookCellFormat createCellFormatInteger() throws Exception {
		return new WorkbookCellFormat( NumberFormats.INTEGER );
	}
	
	/**
	 * @return format
	 * @throws Exception
	 */
	@SuppressWarnings("static-method")
	public WorkbookCellFormat createCellFormatPrice() throws Exception {
		return new WorkbookCellFormat( NumberFormats.FRACTION_TWO_DIGITS );
	}
	
	/**
	 * @return format
	 * @throws Exception
	 */
	@SuppressWarnings("static-method")
	public WorkbookCellFormat createCellFormatText() throws Exception {
		return new WorkbookCellFormat( NumberFormats.TEXT );
	}
	
	/**
	 * @param title
	 * @return sheet change
	 */
	public WorkbookSheetChange createSheet(final String title) {
		return new WorkbookSheetChange( this.workbook.createSheet( title, this.workbook.getNumberOfSheets() ) );
	}
	
	@Override
	public WritableSheet createSheet(final String name, final int index) {
		return new WorkbookSheetChange( this.workbook.createSheet( name, index ) );
	}
	
	/**
	 * @param index
	 * @return sheet change
	 */
	public WorkbookSheetChange createSheetChange(final int index) {
		return new WorkbookSheetChange( this.workbook.getSheet( index ) );
	}
	
	@Override
	public Range[] findByName(final String name) {
		return this.workbook.findByName( name );
	}
	
	@Override
	public WritableCell findCellByName(final String name) {
		return this.workbook.findCellByName( name );
	}
	
	@Override
	public int getNumberOfSheets() {
		return this.workbook.getNumberOfSheets();
	}
	
	@Override
	public String[] getRangeNames() {
		return this.workbook.getRangeNames();
	}
	
	@Override
	public WritableSheet getSheet(final int index) throws IndexOutOfBoundsException {
		return new WorkbookSheetChange( this.workbook.getSheet( index ) );
	}
	
	@Override
	public WritableSheet getSheet(final String name) {
		return new WorkbookSheetChange( this.workbook.getSheet( name ) );
	}
	
	@Override
	public String[] getSheetNames() {
		return this.workbook.getSheetNames();
	}
	
	@Override
	public WritableSheet[] getSheets() {
		final WritableSheet[] sheets = this.workbook.getSheets();
		if (sheets != null) {
			for (int i = 0; i < sheets.length; ++i) {
				sheets[i] = new WorkbookSheetChange( sheets[i] );
			}
		}
		return sheets;
	}
	
	@Override
	public WritableCell getWritableCell(final String loc) {
		return this.workbook.getWritableCell( loc );
	}
	
	/**
	 * @param format
	 * @return format
	 */
	@SuppressWarnings("static-method")
	public WorkbookCellFormat importCellFormat(final DisplayFormat format) {
		return new WorkbookCellFormat( format );
	}
	
	/**
	 * @param name
	 * @param index
	 * @param sheet
	 * @return
	 */
	@Override
	public WorkbookSheetChange importSheet(final String name, final int index, final Sheet sheet) {
		return new WorkbookSheetChange( this.workbook.importSheet( name, index, sheet ) );
	}
	
	@Override
	public WritableSheet moveSheet(final int fromIndex, final int toIndex) {
		return new WorkbookSheetChange( this.workbook.moveSheet( fromIndex, toIndex ) );
	}
	
	@Override
	public void removeRangeName(final String name) {
		this.workbook.removeRangeName( name );
	}
	
	@Override
	public void removeSheet(final int index) {
		this.workbook.removeSheet( index );
	}
	
	@Override
	public void setColourRGB(final Colour c, final int r, final int g, final int b) {
		this.workbook.setColourRGB( c, r, g, b );
	}
	
	@Override
	public void setOutputFile(final File fileName) throws IOException {
		assert false : "ExcelWorkbook.WorkbookChange is not used this way!";
		this.workbook.setOutputFile( fileName );
	}
	
	@Override
	public void setProtected(final boolean prot) {
		this.workbook.setProtected( prot );
	}
	
	@Override
	public void write() throws IOException {
		assert false : "ExcelWorkbook.WorkbookChange is not used this way!";
		this.workbook.write();
	}
	
}
