package com.mariusz.janus.DetectOutlierRules.web;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.mariusz.janus.DetectOutlierRules.Algorithm.CreateAttributeDetails;
import com.mariusz.janus.DetectOutlierRules.Algorithm.FindRuleDominant;
import com.mariusz.janus.DetectOutlierRules.Algorithm.HelperForCalculateSimilary;
import com.mariusz.janus.DetectOutlierRules.Algorithm.SaveRulesAsVector;
import com.mariusz.janus.DetectOutlierRules.Algorithm.SearchAllDominantsInAttribute;
import com.mariusz.janus.DetectOutlierRules.Algorithm.VectorSpaceModeSimilary;
import com.mariusz.janus.DetectOutlierRules.Algorithm.SingleVectorRule;
import com.mariusz.janus.DetectOutlierRules.domain.Attribute;
import com.mariusz.janus.DetectOutlierRules.domain.AttributeDetails;
import com.mariusz.janus.DetectOutlierRules.domain.DominantAttributes;
import com.mariusz.janus.DetectOutlierRules.domain.Rule;
import com.mariusz.janus.DetectOutlierRules.service.IRestRequestService;

import lombok.Getter;
import lombok.Setter;


@ManagedBean
@ViewScoped
public class DetectOutlierController extends AbstracController {

	private final static Logger logger = LoggerFactory.getLogger(DetectOutlierController.class);
	@Getter @Setter private List<Attribute> attributes;
	@Getter @Setter private List<AttributeDetails> listAttributesDetails;
	@Getter @Setter private List<SingleVectorRule> vectorRules;
	@Getter @Setter private List<Rule>rules;
	@Getter private int ruleCount;
	
	@Getter @Setter
	@ManagedProperty(value = "#{IRestRequestService}")
	private IRestRequestService service;

	public DetectOutlierController() {

	}

	@PostConstruct
	public void init() {
			int idKnowledgeBase = getParametr("baseID");
			attributes = service.listAllAttributes(tokenForRequest(), idKnowledgeBase);
			rules = service.listAllRule(tokenForRequest(), idKnowledgeBase);
			ruleCount = service.countRules(tokenForRequest(), idKnowledgeBase);
	}


	public void showModa() {
		CreateAttributeDetails attributeDetails = new CreateAttributeDetails(attributes, rules);
		SaveRulesAsVector vectorRules = new SaveRulesAsVector(rules, attributeDetails.createListAttributeDetails());
		SearchAllDominantsInAttribute allDominants = new SearchAllDominantsInAttribute(attributeDetails.createListAttributeDetails(), vectorRules.createRulesAsVector());

		for(DominantAttributes m:allDominants.searchDominantesInSymbolicAttribute()) {
			if(m!=null)
				System.out.println(m.getAttributeDetails().getAttribute().getName()+": " + m.getValue() + " = " + m.getCount());
		}
		
		FindRuleDominant moda = new FindRuleDominant(vectorRules.createRulesAsVector(), allDominants.searchDominantesInSymbolicAttribute());
		
		System.out.println("Dominanta to: " + moda.calculateDominanta().getIdRules());
		System.out.println();
		System.out.println();
		
		VectorSpaceModeSimilary outlier = new VectorSpaceModeSimilary(vectorRules.createRulesAsVector(), moda.calculateDominanta(), attributeDetails.createListAttributeDetails(), attributes.size());
		List<HelperForCalculateSimilary<SingleVectorRule, Double>> d = outlier.getOutlierRules(10);
		
		for(HelperForCalculateSimilary<SingleVectorRule, Double> help : d) {
			System.out.println("reguła = " + help.getObject().getIdRules() + " similary: "+help.getValue());
		}
	}
	
	

}
