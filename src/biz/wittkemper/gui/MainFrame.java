package biz.wittkemper.gui;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.Observable;
import java.util.Observer;
import java.util.Timer;

import javax.swing.JDesktopPane;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

import org.jdesktop.swingx.JXStatusBar;

import biz.wittkemper.database.entity.Alarmierung;
import biz.wittkemper.utils.FrameUtils;
import biz.wittkemper.utils.InfoTimer;
import biz.wittkemper.utils.KonvertMessage;

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
public class MainFrame extends javax.swing.JFrame implements WindowListener,
		ActionListener, Observer {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JMenuBar mbMain;
	private JMenu mFile;
	private JDesktopPane MainDesktop;
	private JMenuItem mRIC;
	private JMenuItem mnMeldung;
	private JMenu Edit;
	private JLabel statusDate;
	private JLabel statusText;
	private JXStatusBar statusBar;
	private JMenuItem mExit;
	private Timer dateTime = new Timer();
	private Timer checkMeldungen = new Timer();
	private FrameUtils frameUtils = new FrameUtils();
	MeldungsMelder meldungsMelder = new MeldungsMelder();
	private Timer nextFrame = new Timer();

	FrameFactory frameFactory = new FrameFactory();

	/**
	 * Auto-generated main method to display this JFrame
	 */

	public MainFrame() {
		super();
		initGUI();
		addWindowListener(this);
		this.setTitle("Info-System");
		{
			statusBar = new JXStatusBar();
			getContentPane().add(statusBar, BorderLayout.SOUTH);
			statusBar.setPreferredSize(new java.awt.Dimension(390, 19));
			{
				statusText = new JLabel();
				statusBar.add(statusText);
				statusText.setText("");
				statusText.setPreferredSize(new java.awt.Dimension(638, 11));
			}
			{
				statusDate = new JLabel();
				statusBar.add(statusDate);
				statusDate.setText("Date");
				statusDate.setPreferredSize(new java.awt.Dimension(228, 11));
			}
			dateTime.schedule(new DateTimeTimer(statusDate), 1000, 1000);
			meldungsMelder.addObserver(this);
			checkMeldungen.schedule(new KonvertMessage(statusText,
					meldungsMelder), 2000, 25000);

			nextFrame.schedule(new InfoTimer(meldungsMelder), 5000,
					(1000 * 60 * 2));
		}
		{
			MainDesktop = new JDesktopPane();
			getContentPane().add(MainDesktop, BorderLayout.CENTER);
			MainDesktop.setPreferredSize(new java.awt.Dimension(703, 257));
		}

		this.setIconImage(frameUtils.getApplicationIcon());
		setVisible(true);

	}

	public void windowClosing(WindowEvent e) {
		dateTime.cancel();
		dateTime.purge();
		checkMeldungen.cancel();
		checkMeldungen.purge();
		dispose();
		System.exit(0);
	}

	public void windowOpened(WindowEvent e) {
	}

	public void windowActivated(WindowEvent e) {
	}

	public void windowIconified(WindowEvent e) {
	}

	public void windowDeiconified(WindowEvent e) {
	}

	public void windowDeactivated(WindowEvent e) {
	}

	public void windowClosed(WindowEvent e) {
	}

	private void initGUI() {
		try {
			{
				mbMain = new JMenuBar();
				setJMenuBar(mbMain);
				{
					mFile = new JMenu();
					mbMain.add(mFile);
					mFile.setText("Datei");
					{
						mExit = new JMenuItem();
						mFile.add(mExit);
						mExit.setText("beenden");
						mExit.addActionListener(new ActionListener() {
							public void actionPerformed(ActionEvent evt) {
								mExitActionPerformed(evt);
							}
						});
					}
				}
				{
					Edit = new JMenu();
					mbMain.add(Edit);
					Edit.setText("Bearbeiten");
					{
						mRIC = new JMenuItem();
						Edit.add(mRIC);
						mRIC.setText("RIC");
						mRIC.setActionCommand("eRIC");
						mRIC.addActionListener(this);
					}
					{
						mnMeldung = new JMenuItem();
						Edit.add(mnMeldung);
						mnMeldung.setText("Meldungen");
						mnMeldung.setActionCommand("eMeldung");
						mnMeldung.addActionListener(this);
					}
				}
			}
			pack();
			this.setSize(713, 331);
			setSize(Toolkit.getDefaultToolkit().getScreenSize());
			repaint();
		} catch (Exception e) {
			// add your error handling code here
			e.printStackTrace();
		}
	}

	private void mExitActionPerformed(ActionEvent evt) {
		windowClosing(null);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getActionCommand().endsWith("eRIC")) {
			FEditRIC editRIC = new FEditRIC(this);
			editRIC.setVisible(true);
			System.out.println("Fenster zu ...");
		} else if (e.getActionCommand().endsWith("eMeldung")) {
			zeigMeldungen();
		}

	}

	private void zeigMeldungen() {
		try {
			for (Component com : MainDesktop.getComponents()) {
				if (com instanceof FMeldungsDisplay) {
					return;
				} else if (com instanceof JInternalFrame) {
					JInternalFrame iframe = (JInternalFrame) com;
					iframe.setVisible(false);
					iframe.dispose();
				}
			}

			FMeldungsDisplay display = new FMeldungsDisplay(this,
					meldungsMelder);
			display.setSize(MainDesktop.getSize());
			MainDesktop.add(display);
		} catch (Exception ex) {
			System.out.println("Maincall....");
			System.out.println(ex.getMessage());
		}
	}

	@Override
	public void update(Observable o, Object arg) {
		if (arg instanceof Alarmierung) {
			nextFrame.cancel();
			zeigMeldungen();
			
		} else if (arg instanceof String) {
			String meldung = arg.toString();
			if (meldung.equals("ALARMENDE")) {
				
				nextFrame = new Timer();				
				nextFrame.schedule(new InfoTimer(meldungsMelder), 5000,
						(1000 * 60 * 2));
			}
			if (meldung.equals("NEXTFRAME")) {
				closeInfFrame();
				JInternalFrame newframe = frameFactory.getNextFrame();
				if (newframe != null) {
					MainDesktop.add(newframe);
					if (newframe instanceof IGUI) {
						((IGUI) newframe).run();
					}
				}
			}

		}

	}

	private void closeInfFrame() {
		for (Component com : MainDesktop.getComponents()) {
			if (com instanceof FMeldungsDisplay) {
				return;
			} else if (com instanceof JInternalFrame) {
				com.setVisible(false);
				((JInternalFrame) com).dispose();
			}
		}
	}

}