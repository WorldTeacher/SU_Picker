module reiheAPicker {
	requires javafx.graphics;
	requires javafx.controls;
	requires org.apache.commons.io;
	requires javafx.base;
	requires java.desktop;
	requires java.xml;
	opens userInterface to javafx.graphics;
}