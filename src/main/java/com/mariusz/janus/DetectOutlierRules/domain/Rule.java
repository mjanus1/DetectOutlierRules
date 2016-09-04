package com.mariusz.janus.DetectOutlierRules.domain;

import java.io.Serializable;
import java.util.Collections;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class Rule implements Serializable {

	private static final long serialVersionUID = 1L;

	@JsonProperty
	private Integer id;	
	@JsonProperty
	private String description;	
	@JsonProperty
	private KnowledgeBase knowledgeBase;
	@JsonProperty
	private List<AttributeValues> attributeValues;

	public String saveRuleAsString() {
		StringBuilder query = new StringBuilder("");
		int countElement = attributeValues.size();

		Collections.sort(attributeValues);
		for (AttributeValues attributes : attributeValues) {

			query.append(attributes.getAttribute().getName() + "  ");
			--countElement;
			query.append(attributes.getOperator() + "  ");
			if (attributes.getValue() != null) {
				query.append(attributes.getValue().getName() + " ");
			} else {
				query.append(attributes.getContinousValue() + " ");
			}

			if (attributes.isConclusion()) {
				query.append(" IF ");
			} else {
				if (countElement != 0)
					query.append(" AND ");
			}
		}
		return query.toString();
	}
}
