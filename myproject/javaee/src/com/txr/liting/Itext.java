package com.txr.liting;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

import org.junit.Test;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Font;
import com.itextpdf.text.Image;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPRow;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

public class Itext {
	
	public static void main(String[] args) {
	
		
	}

	/**
	 * @Title: itextOne
	 * @Description: itex输出pdf第一个，发现没有导出里面的中文
	 * @throws FileNotFoundException
	 * @throws DocumentException
	 * @return: void
	 */
	@Test
	public void itextOne() throws FileNotFoundException, DocumentException{
		Document doc=new Document();
		PdfWriter.getInstance(doc, new FileOutputStream("/home/liting/study/itext/HelloWorld.pdf"));
		doc.open();doc.add(new Paragraph("这是文本——itext输出"));//注意输出之后的itext没有中文
		doc.close();
	}
	@Test
	public void itextTwo() throws DocumentException, IOException{
		Document doc=new Document(PageSize.A4);
		PdfWriter.getInstance(doc, new FileOutputStream("/home/liting/study/itext/HelloWorld2.pdf"));
		doc.open();
		//
		BaseFont basef=BaseFont.createFont("STSong-Light","UniGB-UCS2-H", BaseFont.NOT_EMBEDDED);
		Font titlFont=new Font(basef,18,Font.NORMAL);
		//
		BaseFont basec=BaseFont.createFont("STSong-Light","UniGB-UCS2-H", BaseFont.NOT_EMBEDDED);
		Font font=new Font(basef,9,Font.NORMAL);
		Paragraph titleP=new Paragraph("儿童信息 Child Information\n\n");  
		titleP.setAlignment(titleP.ALIGN_CENTER);  
		doc.add(titleP);  
		//生成4列的表格  
		PdfPTable table = new PdfPTable (4);  
		table.setWidthPercentage(100);  
		table.setWidthPercentage(100);  
		table.addCell (new Paragraph ("Children-id",font));  
		PdfPCell cell = new PdfPCell (new Paragraph ("09140800002",font));  
		cell.setColspan (3);  
		table.addCell (cell);  
		// 添加第一行  
		table.addCell (new Paragraph ("Name(CN)",font));  
		table.addCell (new Paragraph ("党宁生",font));  
		table.addCell (new Paragraph ("Name(EN)",font));  
		table.addCell (new Paragraph ("DANG NING SHENG",font));  
		  
		//添加第二行  
		table.addCell (new Paragraph ("Sex(CN)",font));  
		table.addCell (new Paragraph ("男",font));  
		table.addCell (new Paragraph ("Sex(EN)",font));  
		table.addCell (new Paragraph ("MALE",font));  
		//添加第8行  
		table.addCell (new Paragraph ("Note",font));  
		cell = new PdfPCell (new Paragraph ("儿童资料",font));  
		cell.setColspan (3);  
		table.addCell (cell);  
		  
		//添加第9行  
		table.addCell (new Paragraph ("Pictures",font));  
		Image photo=Image.getInstance("/home/liting/study/image/ai_lijun.jpg");  
		cell = new PdfPCell (photo);  
		cell.setColspan (3);  
		table.addCell (cell);  
		  
		for(PdfPRow row:(ArrayList<PdfPRow>)table.getRows()){  
		 for(PdfPCell cells:row.getCells()){  
		  if(cells!=null){  
		   cells.setPadding(10.0f);  
		  }  
		 }  
		}  
		doc.add (table);  
		doc.newPage();  
		//插入图片  
		doc.newPage();  
		Image image1 = Image.getInstance("/home/liting/study/image/ai_lijun.jpg");    
		image1.setAlignment(image1.ALIGN_CENTER);  
		image1.scaleToFit( PageSize.A4.getHeight(),PageSize.A4.getWidth());  
		doc.add (image1);  
		  
		doc.close ();  
	}
	
}
