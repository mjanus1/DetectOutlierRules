package com.mariusz.janus.DetectOutlierRules.Algorithm;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.mariusz.janus.DetectOutlierRules.domain.Attribute;
import com.mariusz.janus.DetectOutlierRules.domain.AttributeAdditionDetail;
import com.mariusz.janus.DetectOutlierRules.domain.AttributeValues;
import com.mariusz.janus.DetectOutlierRules.domain.Rule;

import lombok.Getter;

public class DetailsAttribute {
	
	private static final Logger logger = LoggerFactory.getLogger(DetailsAttribute.class);
	@Getter private List<Rule> rules;
	@Getter private List<Attribute> attributes;
	@Getter private List<AttributeAdditionDetail> attributeAdditionDetails;
	
	public DetailsAttribute(List<Rule> rules, List<Attribute> attributes) {
		this.rules = rules;
		this.attributes = attributes;
		createListAttributeDetails();
	}
	
	public void createListAttributeDetails() {
		attributeAdditionDetails = new ArrayList<>();
		for (Rule rules : rules) {
			for (AttributeValues attributesValue : rules.getAttributeValues()) {

				if (attributesValue.isConclusion()) {
					if (checkIsExistAttribute(attributesValue.getAttribute().getName())) {
						attributeAdditionDetails.add(new AttributeAdditionDetail(attributesValue.getAttribute(), true));
					}
					continue;
				} else if (attributesValue.getValue() != null) {
					if (checkIsExistAttribute(attributesValue.getAttribute().getName())) {
						attributeAdditionDetails.add(new AttributeAdditionDetail(attributesValue.getAttribute(), false));
					}
					continue;
				} else {
					if (checkIsExistAttribute(attributesValue.getAttribute().getName())) {
						attributeAdditionDetails.add(new AttributeAdditionDetail(attributesValue.getAttribute(), false));
					}
					continue;
				}
			}
		}	
		logger.debug("sprawdzenie ile elementów na liscie = {}", attributeAdditionDetails.size());
		compareAttributesAndAddIfNoExistInAttributeDetails();
		logger.debug("sprawdzenie ile elementów na liscie = {}", attributeAdditionDetails.size());
		
		Collections.sort(attributeAdditionDetails);
		setPositionOnListAttributes();
		
		printAllAttributes();
	}
	
	private void setPositionOnListAttributes() {
		int position = 0;
		for(AttributeAdditionDetail attDetails : attributeAdditionDetails) {
			attDetails.setPossitionOnVector(position);
			++position;
		}
	}
	
	private void printAllAttributes() {
		System.out.println();
		System.out.println("KOLEJNOŚCI ATRUBUTÓW:");
		for(AttributeAdditionDetail attDetails : attributeAdditionDetails) {
			System.out.print(attDetails.getAttribute().getName()+":"+attDetails.getPossitionOnVector() + ", ");
		}
	}

	private void compareAttributesAndAddIfNoExistInAttributeDetails() {
		for (Attribute attribute : attributes) {
			if (checkIsExistAttribute(attribute.getName())) {
				attributeAdditionDetails.add(new AttributeAdditionDetail(attribute, false));
			}
		}
	}

	private boolean checkIsExistAttribute(String attributeName) {
		for (AttributeAdditionDetail attDetails : attributeAdditionDetails) {
			if ((attDetails.getAttribute().getName()).equals(attributeName)) {
				return false;
			}
		}
		return true;
	}
}
