package biz.wittkemper.utils;

import java.awt.Dimension;
import java.awt.Image;
import java.awt.Toolkit;
import java.net.URL;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JDialog;

public class FrameUtils {

	public void CenterDialog(JDialog dialog){
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		int top = (screenSize.height - dialog.getHeight()) / 2;
	    int left = (screenSize.width - dialog.getWidth()) / 2;
	    
	    dialog.setLocation(left, top); 
	}
	
	public Image getApplicationIcon(){
		URL url = this.getClass().getResource("Feuerwehr_Symbol.jpg");
		Image image = Toolkit.getDefaultToolkit().getImage(url);
		return image;
	}

	public Icon getBlaulichtIcon() {
		URL url = this.getClass().getResource("alarm.gif");
		ImageIcon icon = new ImageIcon(url);
		return icon;
	}
}
