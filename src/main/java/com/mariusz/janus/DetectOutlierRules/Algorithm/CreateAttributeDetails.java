package com.mariusz.janus.DetectOutlierRules.Algorithm;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.mariusz.janus.DetectOutlierRules.domain.Attribute;
import com.mariusz.janus.DetectOutlierRules.domain.AttributeDetails;
import com.mariusz.janus.DetectOutlierRules.domain.AttributeValues;
import com.mariusz.janus.DetectOutlierRules.domain.Rule;

import lombok.Getter;
import lombok.Setter;

public class CreateAttributeDetails {
	private static final Logger logger = LoggerFactory.getLogger(CreateAttributeDetails.class);
	@Getter @Setter private List<AttributeDetails> attributesDetails;
	@Getter @Setter private List<Attribute> attributes;
	@Getter @Setter private List<Rule> rules;
	
	public CreateAttributeDetails(List<Attribute> attributes, List<Rule> rules) {
		this.attributes = attributes;
		this.rules = rules;
	}
	
	public List<AttributeDetails> createListAttributeDetails() {
		attributesDetails = new ArrayList<>();
		for (Rule rules : rules) {
			for (AttributeValues attributesValue : rules.getAttributeValues()) {
				int index = attributes.indexOf(attributesValue.getAttribute());

				if (attributesValue.isConclusion()) {
					if (checkIsExistAttribute(attributesValue.getAttribute().getName())) {
						attributesDetails.add(new AttributeDetails(attributesValue.getAttribute(), true, index));
						logger.debug("dodano:: {} nr pozycji: {}", attributesValue.getAttribute().getName(), index);
					}
					continue;
				} else if (attributesValue.getValue() != null) {
					if (checkIsExistAttribute(attributesValue.getAttribute().getName())) {
						attributesDetails.add(new AttributeDetails(attributesValue.getAttribute(), false, index));
						logger.debug("dodano:: {} nr pozycji: {}", attributesValue.getAttribute().getName(), index);
					}
					continue;
				} else {
					if (checkIsExistAttribute(attributesValue.getAttribute().getName())) {
						attributesDetails.add(new AttributeDetails(attributesValue.getAttribute(), false, index));
						logger.debug("dodano:: {} nr pozycji: {}", attributesValue.getAttribute().getName(), index);
					}
					continue;
				}
			}
		}
		System.out.println("sprawdzenie ile elementów na liscie =" + attributesDetails.size());
		compareAttributesAndAddIfNoExistInAttributeDetails();
		System.out.println("sprawdzenie ile elementów na liscie =" + attributesDetails.size());
		
		return attributesDetails;
	}

	private void compareAttributesAndAddIfNoExistInAttributeDetails() {
		for (Attribute attribute : attributes) {
			if (checkIsExistAttribute(attribute.getName())) {
				attributesDetails.add(new AttributeDetails(attribute, false, attributes.indexOf(attribute)));
			}
		}
	}

	private boolean checkIsExistAttribute(String attributeName) {
		for (AttributeDetails attDetails : attributesDetails) {
			if ((attDetails.getAttribute().getName()).equals(attributeName)) {
				return false;
			}
		}
		return true;
	}

}
