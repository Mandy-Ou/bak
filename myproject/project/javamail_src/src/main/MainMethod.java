package main;

import javax.swing.UIManager;

import frame.LoginFrame;

public class MainMethod {

	/**
	 * @param args
	 *            ����������
	 */
	public static void main(String[] args) {
		// ���ý���Ϊ����ģʽ
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception e) {
			e.printStackTrace();
		}
		javax.swing.SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				new LoginFrame().setVisible(true);
				// new MainFrame().setVisible(true);
			}
		});

	}

}
