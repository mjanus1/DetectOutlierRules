package com.mariusz.janus.DetectOutlierRules.web;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.event.ValueChangeEvent;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.mariusz.janus.DetectOutlierRules.Algorithm.Cluster;
import com.mariusz.janus.DetectOutlierRules.Algorithm.HelperForCalculateSimilary;
import com.mariusz.janus.DetectOutlierRules.Algorithm.MatrixSimilaryGower;
import com.mariusz.janus.DetectOutlierRules.Algorithm.PreliminaryCalculationForAlgorythm;
import com.mariusz.janus.DetectOutlierRules.Algorithm.SingleVectorRule;
import com.mariusz.janus.DetectOutlierRules.Algorithm.VSMSimilarySmc;
import com.mariusz.janus.DetectOutlierRules.domain.Attribute;
import com.mariusz.janus.DetectOutlierRules.domain.AttributeAdditionDetail;
import com.mariusz.janus.DetectOutlierRules.domain.AttributeMostOftenRepeated;
import com.mariusz.janus.DetectOutlierRules.domain.Rule;
import com.mariusz.janus.DetectOutlierRules.service.IRestRequestService;

import lombok.Getter;
import lombok.Setter;

@ManagedBean
@ViewScoped
public class DetectOutlierController extends AbstracController {

	private final static Logger logger = LoggerFactory.getLogger(DetectOutlierController.class);
	
	@Getter@Setter private List<Attribute> attributes;
	@Getter@Setter private List<Rule> rules;
	@Getter@Setter private int parameterOutlier;
	@Getter@Setter private String dominantaAsString;
	@Getter@Setter List<HelperForCalculateSimilary<SingleVectorRule>> similaryOutlier;
	@Getter@Setter List<HelperForCalculateSimilary<Cluster>> similaryOutlierGower;	
	@Getter@Setter private boolean showProperties;
	@Getter@Setter private String selectMethod;	
	@Getter@Setter private String selectMeasure;
	@Getter@Setter private VSMSimilarySmc vsmSimilarySmc;
	@Getter@Setter private MatrixSimilaryGower matrixSimilaryGower;


	@Getter@Setter
	@ManagedProperty(value = "#{IRestRequestService}")
	private IRestRequestService service;

	public DetectOutlierController() {		
		selectMethod = "";
		selectMeasure = "";
		similaryOutlierGower = new ArrayList<>();
		similaryOutlier = new ArrayList<>();
	}

	@PostConstruct
	public void init() {
		int idKnowledgeBase = getParametr("baseID");
		attributes = service.listAllAttributes(tokenForRequest(), idKnowledgeBase);
		rules = service.listAllRule(tokenForRequest(), idKnowledgeBase);
	}

	public void generateOutliers() {
		if(checkCanContinueDetectOutlier()) {
			showProperties = true;
			
			PreliminaryCalculationForAlgorythm calculate = new PreliminaryCalculationForAlgorythm(rules, attributes);
			
			if(selectMeasure.equals("SMC")) {
				dominantaAsString = calculate.getDominanta().getRule().saveRuleAsString();
				calculateSimilarySMC(calculate.getVectorRuleLists(), calculate.getAttributeAdditionDetails(), calculate.getDominanta(), calculate.getAttributeAdditionDetails().size());
			}
			if(selectMeasure.equals("GOWER")) {
				calculateSimilaryGower(calculate.getVectorRuleLists(), calculate.getAttributeAdditionDetails());
			}
		}
	}
	
	private void calculateSimilarySMC(List<SingleVectorRule> vectors, List<AttributeAdditionDetail> details, SingleVectorRule dominanta, int countAttributeAdditionDetails) {
		vsmSimilarySmc = new VSMSimilarySmc(vectors, dominanta, details, countAttributeAdditionDetails);
		
	}
	
	private void calculateSimilaryGower(List<SingleVectorRule> vectors, List<AttributeAdditionDetail> details) {
		matrixSimilaryGower = new MatrixSimilaryGower(vectors, details);
	}
	
	public void selectOutlierSMC() {
		similaryOutlier = vsmSimilarySmc.getOutlierRules(parameterOutlier);
		System.out.println();
		System.out.println("Odchylenia:");
		for (HelperForCalculateSimilary<SingleVectorRule> help : similaryOutlier) {
			System.out.println("reguła = " + help.getObject().getRule().getId() + " similary: " + help.getValue());
		}
	}
	
	public void selectOutlierGower() {
		System.out.println("jestem w wyliczaniu miary gowera");
		similaryOutlierGower =  matrixSimilaryGower.getOutlierByParametr(parameterOutlier);
	}
	
	public void selectCalculateMethod(ValueChangeEvent e) {
		String method;
		method = (String) e.getNewValue();
		if (method == null){
			selectMethod = "";
			parameterOutlier = 0;
			selectMeasure = "";
			showProperties = false;
			similaryOutlier = new ArrayList<>();
		} else {
		selectMethod = method;  
		}
	}

	public void selectCalculateMeasure(ValueChangeEvent e) {
		String measure;
		measure = (String) e.getNewValue();
		if(measure == null) {
			measure = "";
		}
		selectMeasure = measure;
		if(selectMeasure.equals("SMC") || selectMeasure.equals("GOWER")) {
			showProperties = false;
			parameterOutlier = 0;
			similaryOutlierGower = new ArrayList<>();
			similaryOutlier = new ArrayList<>();
		}
	}
	
	private boolean checkCanContinueDetectOutlier() {
		return mustSelectMeasure() ? true : false;
	}
	
	private boolean mustSelectMeasure() {
		if(selectMeasure == null || selectMeasure.isEmpty()) {
			addWarningGlobal("Musisz wybrać miare podobieństwa");
			return false;
		}
		return true;
	}
	
}
