package biz.wittkemper.run;

import java.util.Timer;

import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;

import biz.wittkemper.database.dao.DAOFactory;
import biz.wittkemper.gui.MainFrame;
import biz.wittkemper.utils.Task;

public class Start {

	/**
	 * @param args
	 */
	public static void main(String[] args) {

		try {
		    for (LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
		        if ("Nimbus".equals(info.getName())) {
		            UIManager.setLookAndFeel(info.getClassName());
		            break;
		        }
		    }
		} catch (Exception e) {
		    // If Nimbus is not available, you can set the GUI to another look and feel.
		}

		new MainFrame();
	}

}
