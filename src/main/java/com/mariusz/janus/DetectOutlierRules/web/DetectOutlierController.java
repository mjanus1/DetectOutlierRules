package com.mariusz.janus.DetectOutlierRules.web;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.event.ActionEvent;
import javax.faces.event.ValueChangeEvent;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.mariusz.janus.DetectOutlierRules.Algorithm.CreateAttributeDetails;
import com.mariusz.janus.DetectOutlierRules.Algorithm.FindRuleDominant;
import com.mariusz.janus.DetectOutlierRules.Algorithm.HelperForCalculateSimilary;
import com.mariusz.janus.DetectOutlierRules.Algorithm.MatrixSimilaryGower;
import com.mariusz.janus.DetectOutlierRules.Algorithm.SaveRulesAsVector;
import com.mariusz.janus.DetectOutlierRules.Algorithm.SearchAllDominantsInAttribute;
import com.mariusz.janus.DetectOutlierRules.Algorithm.SingleVectorRule;
import com.mariusz.janus.DetectOutlierRules.Algorithm.VSMSimilaryGower;
import com.mariusz.janus.DetectOutlierRules.Algorithm.VSMSimilaryXXX;
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
	
	@Getter@Setter private List<Attribute> attributes;
	@Getter@Setter private List<AttributeDetails> listAttributesDetails;
	@Getter@Setter private List<SingleVectorRule> vectorsRules;
	@Getter@Setter private List<DominantAttributes> dominantAttributes;
	@Getter@Setter private List<Rule> rules;
	@Getter@Setter private int ruleCount;
	@Getter@Setter private int parameterOutlier;
	@Getter@Setter private SingleVectorRule dominanta;
	@Getter@Setter private String dominantaAsString;
	@Getter@Setter List<HelperForCalculateSimilary<SingleVectorRule>> similaryOutlier;
	@Getter@Setter private boolean showProperties;
	@Getter@Setter private String selectMethod;	
	@Getter@Setter private String selectMeasure;
	@Getter@Setter private VSMSimilaryXXX vsmSimilaryXXX;
	@Getter@Setter private VSMSimilaryGower vsmSimilaryGower;
	@Getter@Setter private boolean rulesNotRelate;
	@Getter@Setter private boolean rulesGrouped;

	@Getter@Setter
	@ManagedProperty(value = "#{IRestRequestService}")
	private IRestRequestService service;

	public DetectOutlierController() {		
		selectMethod = "";
		selectMeasure = "";
		similaryOutlier = new ArrayList<>();
		dominantAttributes = new ArrayList<>();
		listAttributesDetails = new ArrayList<>();
		vectorsRules = new ArrayList<>();
		dominanta = new SingleVectorRule();
	}

	@PostConstruct
	public void init() {
		int idKnowledgeBase = getParametr("baseID");
		attributes = service.listAllAttributes(tokenForRequest(), idKnowledgeBase);
		rules = service.listAllRule(tokenForRequest(), idKnowledgeBase);
		ruleCount = service.countRules(tokenForRequest(), idKnowledgeBase);
	}

	public void vectorSpaceModel() {
		if(checkCanContinueDetectOutlier()) {
			showProperties = true;
			
			CreateAttributeDetails attributeDetails = new CreateAttributeDetails(attributes, rules);
			listAttributesDetails = attributeDetails.createListAttributeDetails();
	
			SaveRulesAsVector vectorRule = new SaveRulesAsVector(rules, listAttributesDetails);
			vectorsRules = vectorRule.createRulesAsVector();
	
			SearchAllDominantsInAttribute allDominants = new SearchAllDominantsInAttribute(listAttributesDetails, vectorsRules);
			dominantAttributes = allDominants.searchDominantesInSymbolicAttribute();
	
			System.out.println();
			System.out.println("Wyznaczenie dominant w atrybutach:");
			for (DominantAttributes m : dominantAttributes) {
				if (m != null)
					System.out.println(
							m.getAttributeDetails().getAttribute().getName() + ": " + m.getValue() + " = " + m.getCount());
			}
			System.out.println();
	
			FindRuleDominant ruleModa = new FindRuleDominant(vectorsRules, dominantAttributes);
			dominanta = ruleModa.calculateDominanta();
			dominantaAsString = dominanta.getRule().saveRuleAsString();
	
			System.out.println("Wybrana dominanta : " + dominanta.getRule().getId());
			System.out.println();
			System.out.println();
		
			calculateSimilaryXXX();
			//calculateSimilaryGower();
		}
	}
	
	private void calculateSimilaryXXX() {
		vsmSimilaryXXX = new VSMSimilaryXXX(vectorsRules, dominanta, listAttributesDetails, attributes.size());
		List<HelperForCalculateSimilary<SingleVectorRule>> d = vsmSimilaryXXX.getSimilaryBetweenRules();

		System.out.println();
		System.out.println("Wyliczone podobienstwa dla XXX");
		for (HelperForCalculateSimilary<SingleVectorRule> help : d) {
			System.out.println("reguła = " + help.getObject().getRule().getId() + " similary: " + help.getValue());
		}
	}
	
	private void calculateSimilaryGower() {
		MatrixSimilaryGower msg = new MatrixSimilaryGower(vectorsRules, listAttributesDetails);
		msg.getOutlierByParametr(3);
	}
	
	public void selectOutlier() {
		similaryOutlier = vsmSimilaryXXX.getOutlierRules(parameterOutlier);
		System.out.println();
		System.out.println("Odchylenia:");
		for (HelperForCalculateSimilary<SingleVectorRule> help : similaryOutlier) {
			System.out.println("reguła = " + help.getObject().getRule().getId() + " similary: " + help.getValue());
		}
	}
	
	public void selectCalculateMethod(ValueChangeEvent e) {
		String method;
		method = (String) e.getNewValue();
		if (method == null){
			selectMethod = "";
			selectMeasure = "";
			rulesGrouped = false;
			rulesNotRelate = false;
			showProperties = false;
			similaryOutlier = new ArrayList<>();
		} else {
		selectMethod = method;  
		}
	}

	public void selectCalculateMeasure(ValueChangeEvent e) {
		
	}
	
	public void showProperties(ActionEvent e) {
		showProperties = true;
	}
	
	private boolean checkCanContinueDetectOutlier() {
		if(mustSelectMeasure() & mustSelectWayCalculateOutlier())
			return true;
		return false;
	}
	
	private boolean mustSelectMeasure() {
		if(selectMeasure == null || selectMeasure.isEmpty()) {
			addWarningGlobal("Musisz wybrać miare podobieństwa");
			return false;
		}
		return true;
	}
	
	private boolean mustSelectWayCalculateOutlier() {
		if(!rulesNotRelate & !rulesGrouped) {
			addWarningGlobal("Musisz zaznaczyć jedną z opcji");
			return false;
		}
		if(rulesGrouped & rulesNotRelate) {
			addErrorGlobal("Nie możesz wybrać dwóch opcji jednocześnie");
			return false;
		}
		return true;
	}
}
