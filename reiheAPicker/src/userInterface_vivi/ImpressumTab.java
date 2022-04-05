package userInterface_vivi;

import java.io.IOException;

import logic_vivi.ConvenienceTools;
import javafx.geometry.Pos;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;

public class ImpressumTab {
	
	public Pane impressumPane;	
	String LogoPath = "res/logo_big.png";
	
	
	public ImpressumTab() throws IOException
	{
		this.impressumPane = new Pane();
		String version = "Version: 1.0";
		String impressumText = "ViVi" + System.lineSeparator()
				+ "Powered by Sherpa/Romeo: https://v2.sherpa.ac.uk/romeo/" + System.lineSeparator() +System.lineSeparator() + 
		version + "" + System.lineSeparator() +
		"\nIdee und initiale Umsetzung: Christian Berger" + System.lineSeparator() +
		"\nBetreuung und Weiterentwicklung:" + System.lineSeparator() +
		"Universitätsbibliothek Trier" + System.lineSeparator() +
		"Universitätsring 15" + System.lineSeparator() +
		"54295 Trier" + System.lineSeparator() + System.lineSeparator() + 
		"Repositorium:"+ System.lineSeparator() + "https://github.com/Universitatsbibliothek-Trier/ViVi" + System.lineSeparator()+ System.lineSeparator() +
		"Diese Software steht unter der MIT-Open Source Software Lizenz." + System.lineSeparator();
		
		Text javaFXtext = new Text(impressumText);
		javaFXtext.setTextAlignment(TextAlignment.CENTER);		
		javaFXtext.setY(50);
		
		
		
		VBox vbox;
		Image image = ConvenienceTools.getPictureFromResources(LogoPath);
			
		if (image != null)
		{
			ImageView imgView = new ImageView(image);
			imgView.setFitWidth(300);
			imgView.setPreserveRatio(true);
			vbox = new VBox(20, imgView, javaFXtext);	
		} else
		{
			vbox = new VBox(20, javaFXtext);
		}
		
		vbox.setLayoutX(250);
		vbox.setLayoutY(50);
		vbox.setAlignment(Pos.CENTER);
		this.impressumPane.getChildren().add(vbox);
		
		
	}

}
