/*
 * Created on 18.06.2005
 */
package ru.myx.util.xls;

import jxl.biff.DisplayFormat;
import jxl.format.Alignment;
import jxl.format.Border;
import jxl.format.BorderLineStyle;
import jxl.format.Colour;
import jxl.format.UnderlineStyle;
import jxl.format.VerticalAlignment;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WriteException;

/**
 * @author myx
 * 
 */
public class WorkbookCellFormat extends WritableCellFormat {
	WorkbookCellFormat() {
		super();
	}
	
	WorkbookCellFormat(final DisplayFormat format) {
		super( format );
	}
	
	/**
	 * @throws Exception
	 */
	public void setAlignmentBottom() throws Exception {
		super.setVerticalAlignment( VerticalAlignment.BOTTOM );
	}
	
	/**
	 * @throws Exception
	 */
	public void setAlignmentCenter() throws Exception {
		super.setAlignment( Alignment.CENTRE );
	}
	
	/**
	 * @throws Exception
	 */
	public void setAlignmentFill() throws Exception {
		super.setAlignment( Alignment.FILL );
	}
	
	/**
	 * @throws Exception
	 */
	public void setAlignmentGeneral() throws Exception {
		super.setAlignment( Alignment.GENERAL );
	}
	
	/**
	 * @throws Exception
	 */
	public void setAlignmentJustify() throws Exception {
		super.setAlignment( Alignment.JUSTIFY );
	}
	
	/**
	 * @throws Exception
	 */
	public void setAlignmentLeft() throws Exception {
		super.setAlignment( Alignment.LEFT );
	}
	
	/**
	 * @throws Exception
	 */
	public void setAlignmentMiddle() throws Exception {
		super.setVerticalAlignment( VerticalAlignment.CENTRE );
	}
	
	/**
	 * @throws Exception
	 */
	public void setAlignmentRight() throws Exception {
		super.setAlignment( Alignment.RIGHT );
	}
	
	/**
	 * @throws Exception
	 */
	public void setAlignmentTop() throws Exception {
		super.setVerticalAlignment( VerticalAlignment.TOP );
	}
	
	/**
	 * @throws WriteException
	 */
	public void setBackgroundColor() throws WriteException {
		super.setBackground( Colour.CORAL );
	}
	
	/**
	 * @throws WriteException
	 */
	public void setBorderBottom() throws WriteException {
		super.setBorder( Border.BOTTOM, BorderLineStyle.THIN, Colour.GREY_50_PERCENT );
	}
	
	/**
	 * @throws WriteException
	 */
	public void setBorderBottomDotted() throws WriteException {
		super.setBorder( Border.BOTTOM, BorderLineStyle.DOTTED, Colour.GREY_50_PERCENT );
	}
	
	/**
	 * @throws WriteException
	 */
	public void setBorderLeft() throws WriteException {
		super.setBorder( Border.LEFT, BorderLineStyle.THIN, Colour.GREY_50_PERCENT );
	}
	
	/**
	 * @throws WriteException
	 */
	public void setBorderLeftDotted() throws WriteException {
		super.setBorder( Border.LEFT, BorderLineStyle.DOTTED, Colour.GREY_50_PERCENT );
	}
	
	/**
	 * @throws WriteException
	 */
	public void setBorderNone() throws WriteException {
		super.setBorder( Border.ALL, BorderLineStyle.NONE );
	}
	
	/**
	 * @throws WriteException
	 */
	public void setBorderRight() throws WriteException {
		super.setBorder( Border.RIGHT, BorderLineStyle.THIN, Colour.GREY_50_PERCENT );
	}
	
	/**
	 * @throws WriteException
	 */
	public void setBorderRightDotted() throws WriteException {
		super.setBorder( Border.RIGHT, BorderLineStyle.DOTTED, Colour.GREY_50_PERCENT );
	}
	
	/**
	 * @throws WriteException
	 */
	public void setBorderTop() throws WriteException {
		super.setBorder( Border.TOP, BorderLineStyle.THIN, Colour.GREY_50_PERCENT );
	}
	
	/**
	 * @throws WriteException
	 */
	public void setBorderTopDotted() throws WriteException {
		super.setBorder( Border.TOP, BorderLineStyle.DOTTED, Colour.GREY_50_PERCENT );
	}
	
	/**
	 * @param family
	 * @param size
	 * @param bold
	 * @param italic
	 * @param underline
	 */
	public void setFont(
			final String family,
			final int size,
			final boolean bold,
			final boolean italic,
			final int underline) {
		final UnderlineStyle underlineStyle;
		switch (underline) {
		case 1:
			underlineStyle = UnderlineStyle.SINGLE;
			break;
		case 2:
			underlineStyle = UnderlineStyle.DOUBLE;
			break;
		case -1:
			underlineStyle = UnderlineStyle.SINGLE_ACCOUNTING;
			break;
		case -2:
			underlineStyle = UnderlineStyle.DOUBLE_ACCOUNTING;
			break;
		default:
			underlineStyle = UnderlineStyle.NO_UNDERLINE;
		}
		super.setFont( new WritableFont( WritableFont.createFont( family ), size, bold
				? WritableFont.BOLD
				: WritableFont.NO_BOLD, italic, underlineStyle ) );
	}
	
	@Override
	public void setWrap(final boolean wrap) throws WriteException {
		super.setWrap( wrap );
	}
}
