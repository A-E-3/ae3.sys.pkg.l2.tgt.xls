/*
 * Created on 17.06.2005
 */
package ru.myx.util.xls;

import java.net.URL;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;
import java.util.regex.Pattern;

import jxl.Cell;
import jxl.CellView;
import jxl.Hyperlink;
import jxl.Image;
import jxl.LabelCell;
import jxl.Range;
import jxl.SheetSettings;
import jxl.format.CellFormat;
import jxl.format.PageOrientation;
import jxl.format.PaperSize;
import jxl.write.DateFormat;
import jxl.write.DateTime;
import jxl.write.Label;
import jxl.write.NumberFormats;
import jxl.write.WritableCell;
import jxl.write.WritableCellFormat;
import jxl.write.WritableHyperlink;
import jxl.write.WritableImage;
import jxl.write.WritableSheet;
import jxl.write.WriteException;
import jxl.write.biff.RowsExceededException;

/**
 * @author myx
 * 
 */
public class WorkbookSheetChange implements WritableSheet {
	private final WritableCellFormat	CP_FORMAT	= new WritableCellFormat( NumberFormats.FRACTION_TWO_DIGITS );
	
	private final WritableCellFormat	CF_FORMAT	= new WritableCellFormat( NumberFormats.FLOAT );
	
	private final WritableCellFormat	CI_FORMAT	= new WritableCellFormat( NumberFormats.INTEGER );
	
	private final WritableCellFormat	CT_FORMAT	= new WritableCellFormat( NumberFormats.TEXT );
	
	private final WritableCellFormat	CD_FORMAT	= new WritableCellFormat( new DateFormat( "yyyy/MM/dd HH:mm" ) );
	
	private final WritableSheet			sheet;
	
	/**
	 * @param sheet
	 */
	WorkbookSheetChange(final WritableSheet sheet) {
		this.sheet = sheet;
	}
	
	@Override
	public void addCell(final WritableCell cell) throws WriteException, RowsExceededException {
		this.sheet.addCell( cell );
	}
	
	@Override
	public void addColumnPageBreak(final int col) {
		this.sheet.addColumnPageBreak( col );
	}
	
	@Override
	public void addHyperlink(final WritableHyperlink h) throws WriteException, RowsExceededException {
		this.sheet.addHyperlink( h );
	}
	
	@Override
	public void addImage(final WritableImage image) {
		this.sheet.addImage( image );
	}
	
	@Override
	public void addRowPageBreak(final int row) {
		this.sheet.addRowPageBreak( row );
	}
	
	@Override
	public void applySharedDataValidation(final WritableCell cell, final int col, final int row) throws WriteException {
		this.sheet.applySharedDataValidation( cell, col, row );
	}
	
	@Override
	public Cell findCell(
			final Pattern pattern,
			final int firstCol,
			final int firstRow,
			final int lastCol,
			final int lastRow,
			final boolean reverse) {
		return this.sheet.findCell( pattern, firstCol, firstRow, lastCol, lastRow, reverse );
	}
	
	@Override
	public Cell findCell(final String contents) {
		return this.sheet.findCell( contents );
	}
	
	@Override
	public Cell findCell(
			final String contents,
			final int firstCol,
			final int firstRow,
			final int lastCol,
			final int lastRow,
			final boolean reverse) {
		return this.sheet.findCell( contents, firstCol, firstRow, lastCol, lastRow, reverse );
	}
	
	@Override
	public LabelCell findLabelCell(final String contents) {
		return this.sheet.findLabelCell( contents );
	}
	
	@Override
	public Cell getCell(final int arg0, final int arg1) {
		return this.sheet.getCell( arg0, arg1 );
	}
	
	@Override
	public Cell getCell(final String arg0) {
		return this.sheet.getCell( arg0 );
	}
	
	@Override
	public Cell[] getColumn(final int arg0) {
		return this.sheet.getColumn( arg0 );
	}
	
	@SuppressWarnings("deprecation")
	@Override
	public CellFormat getColumnFormat(final int arg0) {
		return this.sheet.getColumnFormat( arg0 );
	}
	
	@Override
	public int[] getColumnPageBreaks() {
		return this.sheet.getColumnPageBreaks();
	}
	
	@Override
	public int getColumns() {
		return this.sheet.getColumns();
	}
	
	@Override
	public CellView getColumnView(final int arg0) {
		return this.sheet.getColumnView( arg0 );
	}
	
	@SuppressWarnings("deprecation")
	@Override
	public int getColumnWidth(final int arg0) {
		return this.sheet.getColumnWidth( arg0 );
	}
	
	@Override
	public Image getDrawing(final int arg0) {
		return this.sheet.getDrawing( arg0 );
	}
	
	@Override
	public Hyperlink[] getHyperlinks() {
		return this.sheet.getHyperlinks();
	}
	
	@Override
	public WritableImage getImage(final int arg0) {
		return this.sheet.getImage( arg0 );
	}
	
	@Override
	public Range[] getMergedCells() {
		return this.sheet.getMergedCells();
	}
	
	@Override
	public String getName() {
		return this.sheet.getName();
	}
	
	@Override
	public int getNumberOfImages() {
		return this.sheet.getNumberOfImages();
	}
	
	@Override
	public Cell[] getRow(final int arg0) {
		return this.sheet.getRow( arg0 );
	}
	
	@SuppressWarnings("deprecation")
	@Override
	public int getRowHeight(final int arg0) {
		return this.sheet.getRowHeight( arg0 );
	}
	
	@Override
	public int[] getRowPageBreaks() {
		return this.sheet.getRowPageBreaks();
	}
	
	@Override
	public int getRows() {
		return this.sheet.getRows();
	}
	
	@Override
	public CellView getRowView(final int arg0) {
		return this.sheet.getRowView( arg0 );
	}
	
	@Override
	public SheetSettings getSettings() {
		return this.sheet.getSettings();
	}
	
	@Override
	public WritableCell getWritableCell(final int arg0, final int arg1) {
		return this.sheet.getWritableCell( arg0, arg1 );
	}
	
	@Override
	public WritableCell getWritableCell(final String arg0) {
		return this.sheet.getWritableCell( arg0 );
	}
	
	@Override
	public WritableHyperlink[] getWritableHyperlinks() {
		return this.sheet.getWritableHyperlinks();
	}
	
	@Override
	public void insertColumn(final int arg0) {
		this.sheet.insertColumn( arg0 );
	}
	
	@Override
	public void insertRow(final int arg0) {
		this.sheet.insertRow( arg0 );
	}
	
	@SuppressWarnings("deprecation")
	@Override
	public boolean isHidden() {
		return this.sheet.isHidden();
	}
	
	@SuppressWarnings("deprecation")
	@Override
	public boolean isProtected() {
		return this.sheet.isProtected();
	}
	
	/**
	 * @param x1
	 * @param y1
	 * @param x2
	 * @param y2
	 * @return Range
	 */
	@Override
	public Range mergeCells(final int x1, final int y1, final int x2, final int y2) throws WriteException,
			RowsExceededException {
		return this.sheet.mergeCells( x1, y1, x2, y2 );
	}
	
	@Override
	public void removeColumn(final int arg0) {
		this.sheet.removeColumn( arg0 );
	}
	
	@Override
	public void removeHyperlink(final WritableHyperlink arg0) {
		this.sheet.removeHyperlink( arg0 );
	}
	
	@Override
	public void removeHyperlink(final WritableHyperlink arg0, final boolean arg1) {
		this.sheet.removeHyperlink( arg0, arg1 );
	}
	
	@Override
	public void removeImage(final WritableImage arg0) {
		this.sheet.removeImage( arg0 );
	}
	
	@Override
	public void removeRow(final int arg0) {
		this.sheet.removeRow( arg0 );
	}
	
	@Override
	public void removeSharedDataValidation(final WritableCell arg0) throws WriteException {
		this.sheet.removeSharedDataValidation( arg0 );
	}
	
	/**
	 * @param x
	 * @param y
	 * @param date
	 * @param format
	 * @param cellFormat
	 * @throws Exception
	 */
	public void setCellDate(
			final int x,
			final int y,
			final long date,
			final String format,
			final WorkbookCellFormat cellFormat) throws Exception {
		final TimeZone timeZone = Calendar.getInstance().getTimeZone();
		final DateTime cell = new DateTime( x, y, new Date( date + timeZone.getOffset( date ) ) );
		if (cellFormat != null) {
			cell.setCellFormat( cellFormat );
		} else {
			if (format == null) {
				cell.setCellFormat( this.CD_FORMAT );
			} else {
				cell.setCellFormat( new WritableCellFormat( new DateFormat( format ) ) );
			}
		}
		this.sheet.addCell( cell );
	}
	
	/**
	 * @param x
	 * @param y
	 * @param value
	 * @param cellFormat
	 * @throws Exception
	 */
	public void setCellFloating(final int x, final int y, final double value, final WorkbookCellFormat cellFormat)
			throws Exception {
		final jxl.write.Number label = new jxl.write.Number( x, y, value );
		if (cellFormat != null) {
			label.setCellFormat( cellFormat );
		} else {
			label.setCellFormat( this.CF_FORMAT );
		}
		this.sheet.addCell( label );
	}
	
	/**
	 * @param x
	 * @param y
	 * @param base
	 * @param location
	 * @param text
	 * @param cellFormat
	 * @throws Exception
	 */
	public void setCellHyperlink(
			final int x,
			final int y,
			final String base,
			final String location,
			final String text,
			final WorkbookCellFormat cellFormat) throws Exception {
		final URL url = new URL( new URL( base ), location );
		final WritableHyperlink hyperlink = new WritableHyperlink( x, y, url );
		if (text != null) {
			hyperlink.setDescription( text );
		}
		this.sheet.addHyperlink( hyperlink );
		if (cellFormat != null) {
			this.sheet.getWritableCell( x, y ).setCellFormat( cellFormat );
		}
	}
	
	/**
	 * @param x
	 * @param y
	 * @param location
	 * @param text
	 * @param cellFormat
	 * @throws Exception
	 */
	public void setCellHyperlink(
			final int x,
			final int y,
			final String location,
			final String text,
			final WorkbookCellFormat cellFormat) throws Exception {
		final URL url = new URL( location );
		final WritableHyperlink hyperlink = new WritableHyperlink( x, y, url );
		if (text != null) {
			hyperlink.setDescription( text );
		}
		this.sheet.addHyperlink( hyperlink );
		if (cellFormat != null) {
			this.sheet.getWritableCell( x, y ).setCellFormat( cellFormat );
		}
	}
	
	/**
	 * @param x
	 * @param y
	 * @param value
	 * @param cellFormat
	 * @throws Exception
	 */
	public void setCellInteger(final int x, final int y, final long value, final WorkbookCellFormat cellFormat)
			throws Exception {
		final jxl.write.Number label = new jxl.write.Number( x, y, value, this.CI_FORMAT );
		label.setCellFormat( cellFormat != null
				? cellFormat
				: this.CI_FORMAT );
		this.sheet.addCell( label );
	}
	
	/**
	 * @param x
	 * @param y
	 * @param text
	 * @param cellFormat
	 * @throws Exception
	 */
	public void setCellLabel(final int x, final int y, final String text, final WorkbookCellFormat cellFormat)
			throws Exception {
		final Label label = new Label( x, y, text );
		label.setCellFormat( cellFormat != null
				? cellFormat
				: this.CT_FORMAT );
		this.sheet.addCell( label );
	}
	
	/**
	 * @param x
	 * @param y
	 * @param value
	 * @param cellFormat
	 * @throws Exception
	 */
	public void setCellPrice(final int x, final int y, final double value, final WorkbookCellFormat cellFormat)
			throws Exception {
		final jxl.write.Number label = new jxl.write.Number( x, y, value );
		label.setCellFormat( cellFormat != null
				? cellFormat
				: this.CP_FORMAT );
		this.sheet.addCell( label );
	}
	
	@Override
	public void setColumnGroup(final int arg0, final int arg1, final boolean arg2) throws WriteException,
			RowsExceededException {
		this.sheet.setColumnGroup( arg0, arg1, arg2 );
	}
	
	@Override
	public void setColumnView(final int arg0, final CellView arg1) {
		this.sheet.setColumnView( arg0, arg1 );
	}
	
	@Override
	public void setColumnView(final int arg0, final int arg1) {
		this.sheet.setColumnView( arg0, arg1 );
	}
	
	@SuppressWarnings("deprecation")
	@Override
	public void setColumnView(final int arg0, final int arg1, final CellFormat arg2) {
		this.sheet.setColumnView( arg0, arg1, arg2 );
	}
	
	/**
	 * @param x
	 * @param width
	 * @throws Exception
	 */
	public void setColumnWidth(final int x, final int width) throws Exception {
		final CellView view = new CellView();
		view.setSize( (int) Math.ceil( width * 36.6 ) );
		this.sheet.setColumnView( x, view );
	}
	
	@SuppressWarnings("deprecation")
	@Override
	public void setFooter(final String arg0, final String arg1, final String arg2) {
		this.sheet.setFooter( arg0, arg1, arg2 );
	}
	
	@SuppressWarnings("deprecation")
	@Override
	public void setHeader(final String arg0, final String arg1, final String arg2) {
		this.sheet.setHeader( arg0, arg1, arg2 );
	}
	
	@SuppressWarnings("deprecation")
	@Override
	public void setHidden(final boolean arg0) {
		this.sheet.setHidden( arg0 );
	}
	
	@Override
	public void setName(final String arg0) {
		this.sheet.setName( arg0 );
	}
	
	@Override
	public void setPageSetup(final PageOrientation arg0) {
		this.sheet.setPageSetup( arg0 );
	}
	
	@Override
	public void setPageSetup(final PageOrientation arg0, final double arg1, final double arg2) {
		this.sheet.setPageSetup( arg0, arg1, arg2 );
	}
	
	@Override
	public void setPageSetup(final PageOrientation arg0, final PaperSize arg1, final double arg2, final double arg3) {
		this.sheet.setPageSetup( arg0, arg1, arg2, arg3 );
	}
	
	@SuppressWarnings("deprecation")
	@Override
	public void setProtected(final boolean arg0) {
		this.sheet.setProtected( arg0 );
	}
	
	@Override
	public void setRowGroup(final int arg0, final int arg1, final boolean arg2) throws WriteException,
			RowsExceededException {
		this.sheet.setRowGroup( arg0, arg1, arg2 );
	}
	
	@Override
	public void setRowView(final int arg0, final boolean arg1) throws RowsExceededException {
		this.sheet.setRowView( arg0, arg1 );
	}
	
	@Override
	public void setRowView(final int arg0, final CellView arg1) throws RowsExceededException {
		this.sheet.setRowView( arg0, arg1 );
	}
	
	@Override
	public void setRowView(final int arg0, final int arg1) throws RowsExceededException {
		this.sheet.setRowView( arg0, arg1 );
		
	}
	
	@Override
	public void setRowView(final int arg0, final int arg1, final boolean arg2) throws RowsExceededException {
		this.sheet.setRowView( arg0, arg1, arg2 );
	}
	
	@Override
	public void unmergeCells(final Range arg0) {
		this.sheet.unmergeCells( arg0 );
	}
	
	@Override
	public void unsetColumnGroup(final int arg0, final int arg1) throws WriteException, RowsExceededException {
		this.sheet.unsetColumnGroup( arg0, arg1 );
	}
	
	@Override
	public void unsetRowGroup(final int arg0, final int arg1) throws WriteException, RowsExceededException {
		this.sheet.unsetRowGroup( arg0, arg1 );
	}
}
