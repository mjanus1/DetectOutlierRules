package com.mariusz.janus.DetectOutlierRules.Validator;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

import com.mariusz.janus.DetectOutlierRules.web.AbstractMessageHandling;

@FacesValidator("inputOutlier")
public class InputForCountOutlier extends AbstractMessageHandling implements Validator {

	@Override
	public void validate(FacesContext context, UIComponent component, Object value) throws ValidatorException {
		String val = value.toString();
		System.out.println("Sprawdzenie wartośći "+ val);

		if (!checkIsNumberOfCountNotString(val)) {
			if (checkRangeCount(Integer.parseInt(val)) == false) {
				throw new ValidatorException(addErrorGlobalForInput("Podaj liczbe całkowitą z zakresu [1 - 100]"));
			}
		} else {
			throw new ValidatorException(addErrorGlobalForInput("ee Podaj liczbe całkowitą z zakresu [1 - 100]"));
		}
	}

	private boolean checkRangeCount(int count) {
		return count > 0 || count < 101 ? true : false;
	}

	public boolean checkIsNumberOfCountNotString(String input) {
		String patern = "^[1-9][0-9]{2}";
		return input.matches(patern);
	}

}
