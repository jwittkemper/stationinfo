package biz.wittkemper.gui;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Frame;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.Serializable;
import java.util.Date;
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

import biz.wittkemper.utils.FrameUtils;
import biz.wittkemper.utils.KonvertMessage;
import biz.wittkemper.utils.Utils;
import biz.wittkemper.utils.Utils.STATIONPROP;
import java.awt.Dimension;
import java.awt.Color;

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
		ActionListener, Observer, Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JMenuBar mbMain;
	private JMenu mFile;
	private JDesktopPane MainDesktop;
	private JMenuItem mnCalendar;
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
	FrameFactory frameFactory = new FrameFactory();
	private boolean AlarmAktiv = false;

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
				statusText.setBackground(Color.YELLOW);
				statusBar.add(statusText);
				statusText.setText("");
				statusText.setPreferredSize(new java.awt.Dimension(638, 11));
			}
			{
				statusDate = new JLabel();
				statusDate.setBackground(Color.ORANGE);
				statusBar.add(statusDate);
				statusDate.setText("Date");
				statusDate.setPreferredSize(new java.awt.Dimension(228, 11));
			}
			dateTime.schedule(new DateTimeTimer(statusDate), 1000, 1000);
			meldungsMelder.addObserver(this);
			startMeldungTimer();
		}
		{
			MainDesktop = new JDesktopPane();
			getContentPane().add(MainDesktop, BorderLayout.CENTER);
			MainDesktop.setPreferredSize(new java.awt.Dimension(703, 257));
		}

		this.setIconImage(frameUtils.getApplicationIcon());
		setExtendedState(Frame.MAXIMIZED_BOTH);
		setVisible(true);
		

	}

	private void startMeldungTimer() {
		checkMeldungen.schedule(new KonvertMessage(statusText, meldungsMelder),
				2000, 10000);
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
						mnCalendar = new JMenuItem();
						Edit.add(mnCalendar);
						mnCalendar.setText("Kalender");
						mnCalendar.setActionCommand("eCalendar");
						mnCalendar.addActionListener(this);
						if (Utils.getPropertie(STATIONPROP.MASTER).equals("false")){
							mnCalendar.setEnabled(true);
						}else{
							mnCalendar.setEnabled(false);
						}
					}
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
			setSize(new Dimension(746, 360));
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
		} else if (e.getActionCommand().endsWith("eMeldung")) {
			zeigMeldungen();
		} else if (e.getActionCommand().endsWith("eCalendar")) {
			zeigKalender();
		}

	}

	private void zeigKalender() {
		FKalender kalender = new FKalender();
		MainDesktop.add(kalender);
		kalender.setSize(MainDesktop.getSize());
		kalender.run();
	}

	private void zeigMeldungen() {
		AlarmAktiv = true;
		try {
			for (Component com : MainDesktop.getComponents()) {
				if (com instanceof FMeldungsDisplay) {
					return;
				}
			}
			FMeldungsDisplay display = new FMeldungsDisplay(this,
					meldungsMelder);
			display.setSize(MainDesktop.getSize());
			MainDesktop.add(display);
			} 
		catch (Exception ex) {
			System.out.println(ex.getMessage());
		}
	}

	@Override
	public void update(Observable o, Object arg) {
		String meldung = arg.toString();
		JInternalFrame infoFrame;
		try {
			if (meldung.equals("ALARM") && AlarmAktiv == false) {
				closeInfFrame();
				zeigMeldungen();

			} else if (meldung.equals("ALARMENDE")) {
				AlarmAktiv = false;
			} else if (meldung.equals("NEXTFRAME") && AlarmAktiv == false) {
				closeInfFrame();
				infoFrame = null;
				infoFrame = frameFactory.getNextFrame();
				if (infoFrame != null) {
					MainDesktop.add(infoFrame);
					if (infoFrame instanceof IGUI) {
						IGUI iframe = (IGUI) infoFrame;
						iframe.run();
					}
				}
			}
		} catch (Exception ex) {
			System.out.println(ex.getMessage());
		}

	}

	private void closeInfFrame() {
		try {
			for (Component com : MainDesktop.getComponents()) {

				if (com instanceof JInternalFrame) {
					JInternalFrame igui = (JInternalFrame) com;
					igui.setVisible(false);
					igui.dispose();
				}
			}
		} catch (Exception ex) {
			System.out.println(ex.getMessage());
		}
	}

}