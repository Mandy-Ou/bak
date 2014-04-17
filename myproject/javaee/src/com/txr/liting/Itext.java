package com.txr.liting;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;

import org.junit.Test;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;

public class Itext {
	
	public static void main(String[] args) {

		
	}
	@Test
	public void itextOne() throws FileNotFoundException, DocumentException{
		Document doc=new Document();
		PdfWriter.getInstance(doc, new FileOutputStream("/home/liting/study/itext/"));
		doc.open();doc.add(new Paragraph("这是文本——itext输出"));
		doc.close();
	}
}
