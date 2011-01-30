package biz.wittkemper.gui;

import java.awt.Dimension;
import java.awt.Rectangle;
import java.beans.PropertyVetoException;

import javax.swing.JDesktopPane;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.WindowConstants;

/**
 * This code was edited or generated using CloudGarden's Jigloo SWT/Swing GUI
 * Builder, which is free for non-commercial use. If Jigloo is being used
 * commercially (ie, by a corporation, company or business for any purpose
 * whatever) then you should purchase a license for each developer using Jigloo.
 * Please visit www.cloudgarden.com for details. Use of Jigloo implies
 * acceptance of these licensing terms. A COMMERCIAL LICENSE HAS NOT BEEN
 * PURCHASED FOR THIS MACHINE, SO JIGLOO OR THIS CODE CANNOT BE USED LEGALLY FOR
 * ANY CORPORATE OR COMMERCIAL PURPOSE.
 */
public class FKalender extends javax.swing.JInternalFrame implements IGUI {

	/**
	 * Auto-generated main method to display this JInternalFrame inside a new
	 * JFrame.
	 */
	public static void main(String[] args) {
		JFrame frame = new JFrame();
		FKalender inst = new FKalender();
		JDesktopPane jdp = new JDesktopPane();
		jdp.add(inst);
		jdp.setPreferredSize(inst.getPreferredSize());
		frame.setContentPane(jdp);
		frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		frame.pack();
		frame.setVisible(true);
	}

	public FKalender() {
		super();
		initGUI();
	}

	private void initGUI() {
		try {
			this.setPreferredSize(new java.awt.Dimension(687, 401));
			this.setBounds(0, 0, 687, 401);
			setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void run() {
		try {
			setVisible(true);
			setMaximum(true);
		} catch (PropertyVetoException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	@Override
	public void stop() {
		// TODO Auto-generated method stub

	}

}
