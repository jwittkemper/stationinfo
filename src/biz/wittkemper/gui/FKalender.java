package biz.wittkemper.gui;

import java.awt.Dimension;
import java.awt.Rectangle;
import java.beans.PropertyVetoException;
import javax.swing.GroupLayout;
import javax.swing.JComponent;

import javax.swing.JDesktopPane;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.LayoutStyle;
import javax.swing.WindowConstants;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import org.jdesktop.swingx.JXTable;
import org.jdesktop.swingx.decorator.HighlighterFactory;
import org.jdesktop.swingx.table.TableColumnExt;

import biz.wittkemper.database.dao.DAOFactory;
import biz.wittkemper.gui.tabmodels.KalenderTabModel;

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
	private JXTable tabkalender;
	private JScrollPane jScrollPane1;
	private JLabel jLabel1;
	private KalenderTabModel kaltab = new KalenderTabModel();
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
			this.setPreferredSize(new java.awt.Dimension(764, 520));
			this.setBounds(0, 0, 764, 520);
			GroupLayout thisLayout = new GroupLayout((JComponent)getContentPane());
			getContentPane().setLayout(thisLayout);
			getContentPane().setBackground(new java.awt.Color(173,216,230));
			{
				jScrollPane1 = new JScrollPane();
				jScrollPane1.setOpaque(true);
				{
					tabkalender = new JXTable();
					jScrollPane1.setViewportView(tabkalender);
					tabkalender.setModel(kaltab);
					tabkalender.setFont(new java.awt.Font("Arial", 1, 32));
					tabkalender.setHighlighters(HighlighterFactory.createAlternateStriping());
					tabkalender.setRowHeight(140);
				}
			}
			{
				jLabel1 = new JLabel();
				jLabel1.setText("Kalender");
				jLabel1.setBackground(new java.awt.Color(255,165,0));
				jLabel1.setOpaque(true);
				jLabel1.setFont(new java.awt.Font("Arial",1,28));
				jLabel1.setHorizontalAlignment(JLabel.CENTER);
			}
			thisLayout.setVerticalGroup(thisLayout.createSequentialGroup()
				.addContainerGap(28, 28)
				.addComponent(jLabel1, GroupLayout.PREFERRED_SIZE, 29, GroupLayout.PREFERRED_SIZE)
				.addGap(17)
				.addComponent(jScrollPane1, 0, 392, Short.MAX_VALUE)
				.addContainerGap(31, 31));
			thisLayout.setHorizontalGroup(thisLayout.createSequentialGroup()
				.addContainerGap()
				.addGroup(thisLayout.createParallelGroup()
				    .addComponent(jLabel1, GroupLayout.Alignment.LEADING, 0, 739, Short.MAX_VALUE)
				    .addComponent(jScrollPane1, GroupLayout.Alignment.LEADING, 0, 739, Short.MAX_VALUE))
				.addContainerGap());
			setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void run() {
		try {
			loadData();
			setVisible(true);
			setMaximum(true);
		} catch (PropertyVetoException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	private void loadData() {
		kaltab.setData(DAOFactory.getInstance().getKalenderDAO().getKalender());
		tabkalender.setModel(kaltab);
		setColumns();		
	}

	private void setColumns() {
		try {
			TableColumnExt tc1 = tabkalender.getColumnExt("Datum");
			tc1.setMaxWidth(200);
			tc1.setMinWidth(5);
			tc1.setWidth(200);
			tc1.setPreferredWidth(200);

			TableColumnExt tc3 = tabkalender.getColumnExt("Art");
			tc3.setMaxWidth(180);
			tc3.setMinWidth(5);
			tc3.setWidth(170);
			tc3.setPreferredWidth(170);
			
//			TableColumnExt tc4 = tabkalender.getColumnExt("aktiv");
//			tc4.setMaxWidth(180);
//			tc4.setMinWidth(5);
//			tc4.setWidth(170);
//			tc4.setPreferredWidth(170);
			

		} catch (Exception ex) {
			System.out.println("Hier ist es ....");
		}
	}
	@Override
	public void stop() {
		// TODO Auto-generated method stub

	}

}