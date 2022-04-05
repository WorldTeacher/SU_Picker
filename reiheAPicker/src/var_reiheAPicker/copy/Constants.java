package var_reiheAPicker.copy;

import javafx.scene.control.Label;
import javafx.scene.text.Text;

public class Constants {	

	public static int titleIndex = 0;
	public static int hintIndex = 1;
	public static int holdingsIndex = 2;
	public static String emptyField = "-";
	
	public static String ProgramName = "Sisyphos unchained";
	public static String ChooserTabName = "Titelauswahl";
	public static String HistoryTabName = "Historie";
	public static String ImportSettingsTabName = "Einstellungen";
	public static String isbnCheckerTabName = "ISBN Checker";
	public static String ExportTabName = "Export";
	public static String ImportButtonName = "Import";
	public static String CatalogueUrlTitleSearch = "https://rds.ibs-bw.de/phfreiburg/opac/RDSIndex/Search?type0%5B%5D=allfields&lookfor0%5B%5D=&join=AND&bool0%5B%5D=AND&type0%5B%5D=au&lookfor0%5B%5D=&join=AND&bool0%5B%5D=AND&type0%5B%5D=ti&lookfor0%5B%5D=&join=AND&bool0%5B%5D=AND&type0%5B%5D=ct&lookfor0%5B%5D=&join=AND&bool0%5B%5D=AND&type0%5B%5D=isn&lookfor0%5B%5D=&join=AND&bool0%5B%5D=AND&type0%5B%5D=ta&lookfor0%5B%5D=[{[title]}]&join=AND&bool0%5B%5D=AND&type0%5B%5D=co&lookfor0%5B%5D=&join=AND&bool0%5B%5D=AND&type0%5B%5D=py&lookfor0%5B%5D=&join=AND&bool0%5B%5D=AND&type0%5B%5D=pp&lookfor0%5B%5D=&join=AND&bool0%5B%5D=AND&type0%5B%5D=pu&lookfor0%5B%5D=&join=AND&bool0%5B%5D=AND&type0%5B%5D=si&lookfor0%5B%5D=&join=AND&bool0%5B%5D=AND&type0%5B%5D=zr&lookfor0%5B%5D=&join=AND&bool0%5B%5D=AND&type0%5B%5D=cc&lookfor0%5B%5D=&join=AND&bool0%5B%5D=AND";
	public static String CatalogueUrlTitleAuthorSearch = "https://rds.ibs-bw.de/phfreiburg/opac/RDSIndex/Search?type0%5B%5D=allfields&lookfor0%5B%5D=&join=AND&bool0%5B%5D=AND&type0%5B%5D=au&lookfor0%5B%5D=[{[author]}]&join=AND&bool0%5B%5D=AND&type0%5B%5D=ti&lookfor0%5B%5D=&join=AND&bool0%5B%5D=AND&type0%5B%5D=ct&lookfor0%5B%5D=&join=AND&bool0%5B%5D=AND&type0%5B%5D=isn&lookfor0%5B%5D=&join=AND&bool0%5B%5D=AND&type0%5B%5D=ta&lookfor0%5B%5D=[{[title]}]&join=AND&bool0%5B%5D=AND&type0%5B%5D=co&lookfor0%5B%5D=&join=AND&bool0%5B%5D=AND&type0%5B%5D=py&lookfor0%5B%5D=&join=AND&bool0%5B%5D=AND&type0%5B%5D=pp&lookfor0%5B%5D=&join=AND&bool0%5B%5D=AND&type0%5B%5D=pu&lookfor0%5B%5D=&join=AND&bool0%5B%5D=AND&type0%5B%5D=si&lookfor0%5B%5D=&join=AND&bool0%5B%5D=AND&type0%5B%5D=zr&lookfor0%5B%5D=&join=AND&bool0%5B%5D=AND&type0%5B%5D=cc&lookfor0%5B%5D=&join=AND&bool0%5B%5D=AND";
	public static String CatalogueUrlpublisherSearch = "https://rds.ibs-bw.de/phfreiburg/opac/RDSIndex/Search?type0%5B%5D=allfields&lookfor0%5B%5D=&join=AND&bool0%5B%5D=AND&type0%5B%5D=au&lookfor0%5B%5D=&join=AND&bool0%5B%5D=AND&type0%5B%5D=ti&lookfor0%5B%5D=&join=AND&bool0%5B%5D=AND&type0%5B%5D=ct&lookfor0%5B%5D=&join=AND&bool0%5B%5D=AND&type0%5B%5D=isn&lookfor0%5B%5D=&join=AND&bool0%5B%5D=AND&type0%5B%5D=ta&lookfor0%5B%5D=&join=AND&bool0%5B%5D=AND&type0%5B%5D=co&lookfor0%5B%5D=&join=AND&bool0%5B%5D=AND&type0%5B%5D=py&lookfor0%5B%5D=&join=AND&bool0%5B%5D=AND&type0%5B%5D=pp&lookfor0%5B%5D=&join=AND&bool0%5B%5D=AND&type0%5B%5D=pu&lookfor0%5B%5D=[{[publisher]}]&join=AND&bool0%5B%5D=AND&type0%5B%5D=si&lookfor0%5B%5D=&join=AND&bool0%5B%5D=AND&type0%5B%5D=zr&lookfor0%5B%5D=&join=AND&bool0%5B%5D=AND&type0%5B%5D=cc&lookfor0%5B%5D=&join=AND&bool0%5B%5D=AND";
	public static String CatalogueUrlIsbnfSearch = "https://rds.ibs-bw.de/phfreiburg/opac/RDSIndex/Search?type0%5B%5D=allfields&lookfor0%5B%5D=&join=AND&bool0%5B%5D=AND&type0%5B%5D=au&lookfor0%5B%5D=&join=AND&bool0%5B%5D=AND&type0%5B%5D=ti&lookfor0%5B%5D=&join=AND&bool0%5B%5D=AND&type0%5B%5D=ct&lookfor0%5B%5D=&join=AND&bool0%5B%5D=AND&type0%5B%5D=isn&lookfor0%5B%5D=[{[isbn]}]&join=AND&bool0%5B%5D=AND&type0%5B%5D=ta&lookfor0%5B%5D=&join=AND&bool0%5B%5D=AND&type0%5B%5D=co&lookfor0%5B%5D=&join=AND&bool0%5B%5D=AND&type0%5B%5D=py&lookfor0%5B%5D=&join=AND&bool0%5B%5D=AND&type0%5B%5D=pp&lookfor0%5B%5D=&join=AND&bool0%5B%5D=AND&type0%5B%5D=pu&lookfor0%5B%5D=&join=AND&bool0%5B%5D=AND&type0%5B%5D=si&lookfor0%5B%5D=&join=AND&bool0%5B%5D=AND&type0%5B%5D=zr&lookfor0%5B%5D=&join=AND&bool0%5B%5D=AND&type0%5B%5D=cc&lookfor0%5B%5D=&join=AND&bool0%5B%5D=AND";	
	public static String isbnWildcard = "[{[isbn]}]";
	public static String authorWildcard = "[{[author]}]";
	public static String titleWildcard = "[{[title]}]";
	public static String publisherWildcard = "[{[publisher]}]";
	public static String isbnWildcardLabel = "ISBN";
	public static String authorWildcardLabel = "Autor";
	public static String titleWildcardLabel = "Titel";
	public static String publisherWildcardLabel = "Verlag";
	public static String bestandLabel = "Bestand";
	public static String bestandvorhanden = "ja";
	public static String bestandnichtvorhanden = "nein";
	public static String oaSearchTerm = "<subfield code=\"z\">kostenfrei</subfield>";
	public static String oaMarker = "OA";
	public static String oaKeyWordForDisplay = "Open Access";
	public static String apiCouldNotBeAccessed = "Prüfung nicht möglich";

	
	public static String KVKUrlTitleSearch = "https://kvk.bibliothek.kit.edu/hylib-bin/kvk/nph-kvk2.cgi?maske=kvk-redesign&lang=de&title=KIT-Bibliothek%3A+Karlsruher+Virtueller+Katalog+KVK+%3A+Ergebnisanzeige&head=%2F%2Fkvk.bibliothek.kit.edu%2Fasset%2Fhtml%2Fhead.html&header=%2F%2Fkvk.bibliothek.kit.edu%2Fasset%2Fhtml%2Fheader.html&spacer=%2F%2Fkvk.bibliothek.kit.edu%2Fasset%2Fhtml%2Fspacer.html&footer=%2F%2Fkvk.bibliothek.kit.edu%2Fasset%2Fhtml%2Ffooter.html&css=none&input-charset=utf-8&ALL=&TI=[{[title]}]&AU=&CI=&ST=&PY=&SB=&SS=&PU=&kataloge=K10PLUS&kataloge=BVB&kataloge=NRW&kataloge=HEBIS&kataloge=HEBIS_RETRO&kataloge=KOBV_SOLR&kataloge=DDB&kataloge=STABI_BERLIN&ref=direct&client-js=yes";
	public static String KVKUrlTitleAuthorSearch = "https://kvk.bibliothek.kit.edu/hylib-bin/kvk/nph-kvk2.cgi?maske=kvk-redesign&lang=de&title=KIT-Bibliothek%3A+Karlsruher+Virtueller+Katalog+KVK+%3A+Ergebnisanzeige&head=%2F%2Fkvk.bibliothek.kit.edu%2Fasset%2Fhtml%2Fhead.html&header=%2F%2Fkvk.bibliothek.kit.edu%2Fasset%2Fhtml%2Fheader.html&spacer=%2F%2Fkvk.bibliothek.kit.edu%2Fasset%2Fhtml%2Fspacer.html&footer=%2F%2Fkvk.bibliothek.kit.edu%2Fasset%2Fhtml%2Ffooter.html&css=none&input-charset=utf-8&ALL=&TI=[{[title]}]&AU=[{[author]}]&CI=&ST=&PY=&SB=&SS=&PU=&kataloge=K10PLUS&kataloge=BVB&kataloge=NRW&kataloge=HEBIS&kataloge=HEBIS_RETRO&kataloge=KOBV_SOLR&kataloge=DDB&kataloge=STABI_BERLIN&ref=direct&client-js=yes";
	public static String KVKUrlpublisherSearch = "https://kvk.bibliothek.kit.edu/hylib-bin/kvk/nph-kvk2.cgi?maske=kvk-redesign&lang=de&title=KIT-Bibliothek%3A+Karlsruher+Virtueller+Katalog+KVK+%3A+Ergebnisanzeige&head=%2F%2Fkvk.bibliothek.kit.edu%2Fasset%2Fhtml%2Fhead.html&header=%2F%2Fkvk.bibliothek.kit.edu%2Fasset%2Fhtml%2Fheader.html&spacer=%2F%2Fkvk.bibliothek.kit.edu%2Fasset%2Fhtml%2Fspacer.html&footer=%2F%2Fkvk.bibliothek.kit.edu%2Fasset%2Fhtml%2Ffooter.html&css=none&input-charset=utf-8&ALL=&TI=&AU=&CI=&ST=&PY=&SB=&SS=&PU=[{[publisher]}]&kataloge=K10PLUS&kataloge=BVB&kataloge=NRW&kataloge=HEBIS&kataloge=HEBIS_RETRO&kataloge=KOBV_SOLR&kataloge=DDB&kataloge=STABI_BERLIN&ref=direct&client-js=yes";
	public static String KVKUrlIsbnfSearch = "https://kvk.bibliothek.kit.edu/hylib-bin/kvk/nph-kvk2.cgi?maske=kvk-redesign&lang=de&title=KIT-Bibliothek%3A+Karlsruher+Virtueller+Katalog+KVK+%3A+Ergebnisanzeige&head=%2F%2Fkvk.bibliothek.kit.edu%2Fasset%2Fhtml%2Fhead.html&header=%2F%2Fkvk.bibliothek.kit.edu%2Fasset%2Fhtml%2Fheader.html&spacer=%2F%2Fkvk.bibliothek.kit.edu%2Fasset%2Fhtml%2Fspacer.html&footer=%2F%2Fkvk.bibliothek.kit.edu%2Fasset%2Fhtml%2Ffooter.html&css=none&input-charset=utf-8&ALL=&TI=&AU=&CI=&ST=&PY=&SB=[{[isbn]}]&SS=&PU=&kataloge=K10PLUS&kataloge=BVB&kataloge=NRW&kataloge=HEBIS&kataloge=HEBIS_RETRO&kataloge=KOBV_SOLR&kataloge=DDB&kataloge=STABI_BERLIN&ref=direct&client-js=yes";
	public static int TooltipDefaultHeight = 25;
	
	public static String LabelNameGeneralSettings = "Allgemeine Einstellungen:";
	public static String LabelNameSearchCatalogueSettings = "Katalogsuche:";
	public static String LabelNameMarkingsSettings = "Markierungseinstellungen:";
	public static String LabelNameApiSettings = "API-Einstellungen:";
	public static String LabelName1 = "Export Zielordner: ";
	public static String LabelName2 = "";
	public static String LabelName3 = "DDC Whitelist: ";
	public static String LabelName4 = "Publisher Blacklist: ";
	public static String LabelName5 = "Duplikate entfernen? (Anhand ISBN)";
	public static String LabelName6 = "Belletristik entfernen? (DDC: B)";
	public static String LabelName7 = "";
	public static String LabelName8  = "Suche (F1)";
	public static String LabelName9  = "Suche (F2)";
	public static String LabelName10 = "Suche (F3)";
	public static String LabelName11 = "Suche (F4)";
	public static String LabelName12 = "Suche (Shift + F1)";
	public static String LabelName13 = "Suche (Shift + F2)";
	public static String LabelName14 = "Suche (Shift + F3)";
	public static String LabelName15 = "Suche (Shift + F4)";
	public static String LabelName16 = "Suche (CTRL + F1)";
	public static String LabelName17 = "Suche (CTRL + F2)";
	public static String LabelName18 = "Suche (CTRL + F3)";
	public static String LabelName19 = "Suche (CTRL + F4)";
	
	public static String LabelName20 = "Stichwortmarkierung (Grün)";
	public static String LabelName21 = "Stichwortmarkierung (Rot)";
	
	public static String LabelName22 = "API-Link (Bestandsprüfung):";
	public static String LabelName23 = "Bestand nach Import prüfen?";
	public static String LabelName24 = "Bestandsliteratur entfernen?";	

	public static String LabelName22b = "ISBN-Trenner:";
	public static String LabelName22c = "Sigel:";
	
	public static String LabelName25 = "OA-Titel als Stichwort markieren?";
	public static String LabelName26 = "OA-Titel entfernen?";
	
	public static String LabelName27 = "Duplikate entfernen? (Anhand Titel)";
	public static String LabelName28 = "Schulbücher entfernen? (DDC: S)";
	public static String LabelName29 = "Kinder- und Jugendliteratur entfernen? (DDC: K)";
		
	public static String ResFolder = "res\\";	
	public static boolean ShowPicsInsteadOfButton = false;
	public static String ResImageNamectrlPic = "remote-control.png";
	public static String ResImageNamectrlButton = "ctrl-control-button.png";
	public static String ResImageNameshiftPic = "transmission.png";
	public static String ResImageNameshiftButton = "shift-button.png";
	public static String ResImageNamespacePic = "planet-saturn-space.png";
	public static String ResImageNamespaceButton = "space-button.png";
	public static String ResImageNameenterPic = "enter.png";
	public static String ResImageNameenterButton = "enter-button.png";	
	public static String ResImageNamearrowDown = "down-arrow-button.png";
	public static String ResImageNamearrowUp = "up-arrow-button.png";
	public static String ResImageNamearrowLeft = "left-arrow-button.png";
	public static String ResImageNamearrowRight = "right-arrow-button.png";	
	public static String ResImageNameimport = "import.png";
	public static String ResImageNamef1 = "f1-button.png";
	public static String ResImageNamef2 = "f2-button.png";
	public static String ResImageNamef3 = "f3-button.png";
	public static String ResImageNamef4 = "f4-button.png";
	public static String ResImageNameBackspace = "backspace.png";
	public static String ResImageVerticalLine = "vertical-line.png";
	public static String ResImageclosedEye = "closed-eye.png";
	public static String ResImageTooltipIcon = "rocks-falling-sign.png";
	public static String ResImageQuestionMark = "question-mark-round-line.png";
	public static String ResImageAlt = "alt-button.png";
	public static String ResImageViviButton = "logo_square.png";
	public static String ResImageSnakeButton = "snake.png";
	public static String ResImageIsbnChecker = "interesting-facts.png";
	public static String ResImageReiheAPicker = "rocks-falling-sign.png";
	
	
	public static Text ShortCutDescription_inhaltsverzeichnis = new Text(" Inhaltsverzeichnis ");
	public static Text ShortCutDescription_inhaltstext = new Text(" Inhaltstext ");
	public static Text ShortCutDescription_zurAnsicht = new Text(" zur Ansicht ");
	public static Text ShortCutDescription_auflagentausch = new Text(" Auflage tauschen ");
	public static Text ShortCutDescription_sonderHinweis = new Text(" Eigener Hinweis ");
	public static Text ShortCutDescription_export = new Text(" Titel Exportieren ");
	public static Text ShortCutDescription_titelsuche = new Text(" Siehe Einstellungen ");
	public static Text ShortCutDescription_titelautorsuche = new Text(" Titel/Autor suchen ");
	public static Text ShortCutDescription_verlagsuche = new Text(" Verlag suchen ");
	public static Text ShortCutDescription_isbnsuche = new Text(" ISBN suchen ");
	public static Text ShortCutDescription_rueckgaengig = new Text(" Entscheidung zurück ");
	
	public static String Kurzeinfuehrung = 
			"- Markieren Sie eine der drei Textboxen\r\n"
			+ "- Verschieben/navigieren Sie mithilfe der Pfeiltasten\r\n"
			+ "- Linke Spalte: Aussortierte/ungeeignete Titel\r\n"
			+ "- Rechte Spalte: Gewählte Titel\r\n"
			+ "- Exportiert werden die Titel auf der rechten Seite\r\n"
			+ "- Mit SHIFT + Mausrad können Sie durch die Listen scrollen"
			;
	
	public static String DefaultFont= "TimesRoman";
	public static int DefaultFontSize = 14;
}
