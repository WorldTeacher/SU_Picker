module reiheAPicker {
	requires javafx.graphics;
	requires javafx.controls;
	requires org.apache.commons.io;
	requires javafx.base;
	opens userInterface to javafx.graphics;
}