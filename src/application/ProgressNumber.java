package application;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;

public class ProgressNumber {

	private DoubleProperty number;

	public Double getNumber() {
		return number.get();
	}

	public void setNumber(Double number) {
		this.numberProperty().set(number);
	}
	
	public DoubleProperty numberProperty() {
		if(number == null) {
			number = new SimpleDoubleProperty(0.0);
		}
		
		return number;
	}
	
}
