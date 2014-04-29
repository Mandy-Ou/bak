package com.txr.swing;

import java.awt.BorderLayout;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

public class BaseTable {
	public static void main(String[] args) {
		JFrame jf=new JFrame("标题");
		jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		String[] column={"时间","表"};
		Object[][] data={{"1","2"},{"33","44"}};
		JLabel label=new JLabel("title",SwingConstants.CENTER);
		jf.getContentPane().add(label,BorderLayout.NORTH);
		jf.setBounds(200, 200, 400,120);
		jf.setVisible(true);
		
	}

}
