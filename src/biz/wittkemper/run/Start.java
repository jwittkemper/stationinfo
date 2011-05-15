package biz.wittkemper.run;

import javax.swing.JOptionPane;
import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;

import biz.wittkemper.gui.MainFrame;
import biz.wittkemper.utils.Utils;
import biz.wittkemper.utils.Utils.STATIONPROP;

public class Start {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		if (Utils.PropertieFileExist()== false){
			String db = JOptionPane.showInputDialog("Bitte den Server der DB angeben!");
			Utils.storePropertie(STATIONPROP.DATABASE, db);
			
			int master = JOptionPane.showConfirmDialog(null, "Ist diese Station der Master?", "Frage", JOptionPane.YES_NO_OPTION);
			if (master== JOptionPane.YES_OPTION){
				Utils.storePropertie(STATIONPROP.MASTER, "true");
			}else{
				Utils.storePropertie(STATIONPROP.MASTER, "false");
			}
			
		}
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
