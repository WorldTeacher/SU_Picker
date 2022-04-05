package logic_reiheAPicker;

import java.io.File;
import java.io.FileInputStream;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import var_reiheAPicker.copy.Constants;

public class GetButtonPics {
	
	private static Image getImage(String resName)
	{
		File f = new File(resName);
		if(f.exists() && !f.isDirectory()) { 
			
			FileInputStream inputStream;
			Image image = null;		
			try
			{
				inputStream = new FileInputStream(resName);
				image = new Image(inputStream);			
			}
			catch (Exception e)
			{
				return null;	
			}			
		    return image;
		}		
		return null;
	}
	
	public static Image getButtonImage_ctrl()
	{
		if (Constants.ShowPicsInsteadOfButton)
		{
			return getImage(Constants.ResFolder+Constants.ResImageNamectrlPic);
		} else
		{
			return getImage(Constants.ResFolder+Constants.ResImageNamectrlButton);
		}		
	}
	
	public static Image getButtonImage_arrowDown()
	{		
			return getImage(Constants.ResFolder+Constants.ResImageNamearrowDown);
	}
	
	public static Image getButtonImage_arrowUp()
	{		
			return getImage(Constants.ResFolder+Constants.ResImageNamearrowUp);
	}
	
	public static Image getButtonImage_arrowLeft()
	{		
			return getImage(Constants.ResFolder+Constants.ResImageNamearrowLeft);
	}
	
	public static Image getButtonImage_arrowRight()
	{		
			return getImage(Constants.ResFolder+Constants.ResImageNamearrowRight);
	}
	
	public static Image getButtonImage_F1()
	{		
			return getImage(Constants.ResFolder+Constants.ResImageNamef1);
	}
	
	public static Image getButtonImage_F2()
	{		
			return getImage(Constants.ResFolder+Constants.ResImageNamef2);
	}
	
	public static Image getButtonImage_F3()
	{		
			return getImage(Constants.ResFolder+Constants.ResImageNamef3);
	}
	
	public static Image getButtonImage_F4()
	{		
			return getImage(Constants.ResFolder+Constants.ResImageNamef4);
	}
	
	public static Image getButtonImage_importButton()
	{		
			return getImage(Constants.ResFolder+Constants.ResImageNameimport);
	}
	
	public static Image getButtonImage_backspace()
	{		
			return getImage(Constants.ResFolder+Constants.ResImageNameBackspace);
	}
	
	public static Image getButtonImage_verticalLine()
	{		
			return getImage(Constants.ResFolder+Constants.ResImageVerticalLine);
	}
	
	public static Image getButtonImage_closedEye()
	{		
			return getImage(Constants.ResFolder+Constants.ResImageclosedEye);
	} 
	
	public static Image getButtonImage_TooltipIcon()
	{		
			return getImage(Constants.ResFolder+Constants.ResImageTooltipIcon);
	}
	
	public static Image getButtonImage_QuestionMark()
	{		
			return getImage(Constants.ResFolder+Constants.ResImageQuestionMark);
	}
	public static Image getButtonImage_Alt()
	{		
			return getImage(Constants.ResFolder+Constants.ResImageAlt);
	}
	
	public static Image getButtonImage_reiheAPicker()
	{
		return getImage(Constants.ResFolder+Constants.ResImageReiheAPicker);
	}
	
	public static Image getButtonImage_isbnChecker()
	{
		return getImage(Constants.ResFolder+Constants.ResImageIsbnChecker);
	}
	public static Image getButtonImage_viviButton()
	{
		return getImage(Constants.ResFolder+Constants.ResImageViviButton);
	}
	public static Image getButtonImage_snakeButton()
	{
		return getImage(Constants.ResFolder+Constants.ResImageSnakeButton);
	}
		
	public static Image getButtonImage_enter()
	{
		if (Constants.ShowPicsInsteadOfButton)
		{
			return getImage(Constants.ResFolder+Constants.ResImageNameenterPic);
		} else
		{
			return getImage(Constants.ResFolder+Constants.ResImageNameenterButton);
		}		
	}
	
	public static Image getButtonImage_space()
	{
		if (Constants.ShowPicsInsteadOfButton)
		{
			return getImage(Constants.ResFolder+Constants.ResImageNamespacePic);
		} else
		{
			return getImage(Constants.ResFolder+Constants.ResImageNamespaceButton);
		}		
	}
	
	public static Image getButtonImage_shift()
	{
		if (Constants.ShowPicsInsteadOfButton)
		{
			return getImage(Constants.ResFolder+Constants.ResImageNameshiftPic);
		} else
		{
			return getImage(Constants.ResFolder+Constants.ResImageNameshiftButton);
		}		
	}
	

	
	
	
	
	public static ImageView turnPicIntoImageView(Image image, int height)
	{
		    if (image != null)
	        {  ImageView view = new ImageView(image);
			view.setFitHeight(height);
		    view.setPreserveRatio(true);
		    return view;
	        }
		    return null;
	}	
	
	public static ImageView turnPicIntoImageView(Image image)
	{
		return turnPicIntoImageView(image, Constants.TooltipDefaultHeight);
	}	
	
}
