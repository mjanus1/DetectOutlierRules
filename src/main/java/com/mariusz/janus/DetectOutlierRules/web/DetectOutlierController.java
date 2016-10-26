package com.mariusz.janus.DetectOutlierRules.web;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.event.ActionEvent;
import javax.faces.event.ValueChangeEvent;

import org.primefaces.context.RequestContext;
import org.primefaces.event.SelectEvent;

import com.mariusz.janus.DetectOutlierRules.Algorithm.Cluster;
import com.mariusz.janus.DetectOutlierRules.Algorithm.HelperForCalculateSimilary;
import com.mariusz.janus.DetectOutlierRules.Algorithm.MatrixSimilaryGower;
import com.mariusz.janus.DetectOutlierRules.Algorithm.PreliminaryCalculationForAlgorythm;
import com.mariusz.janus.DetectOutlierRules.Algorithm.SingleVectorRule;
import com.mariusz.janus.DetectOutlierRules.Algorithm.VSMSimilaryGowerDominanta;
import com.mariusz.janus.DetectOutlierRules.Algorithm.VSMSimilarySmc;
import com.mariusz.janus.DetectOutlierRules.domain.Attribute;
import com.mariusz.janus.DetectOutlierRules.domain.AttributeAdditionDetail;
import com.mariusz.janus.DetectOutlierRules.domain.Rule;
import com.mariusz.janus.DetectOutlierRules.domain.SelectedRule;
import com.mariusz.janus.DetectOutlierRules.service.IRestRequestService;

import lombok.Getter;
import lombok.Setter;

@ManagedBean
@ViewScoped
public class DetectOutlierController extends AbstracController {
	
	@Getter@Setter private List<Attribute> attributes;
	@Getter@Setter private List<Rule> rules;
	@Getter@Setter private String parameterOutlier;
	@Getter@Setter private String parametr;
	@Getter@Setter private String dominantaAsString;
	@Getter@Setter List<HelperForCalculateSimilary<SingleVectorRule>> similaryOutlier;
	@Getter@Setter List<HelperForCalculateSimilary<Cluster>> similaryMatrixGower;	
	@Getter@Setter private boolean showProperties;
	@Getter@Setter private boolean showMatriksSimilary;
	@Getter@Setter private String selectMethod;	
	@Getter@Setter private String selectMeasure;
	@Getter@Setter private VSMSimilarySmc vsmSimilarySmc;
	@Getter@Setter private VSMSimilaryGowerDominanta vsmSimilaryGowerDominanta;
	@Getter@Setter private MatrixSimilaryGower matrixSimilaryGower;
	@Getter@Setter private PreliminaryCalculationForAlgorythm calculate;
	
	// list for selected by expert
	@Getter@Setter List<SelectedRule> selectedRuleList;
	@Getter@Setter List<Rule> choosenRuleList;


	@Getter@Setter
	@ManagedProperty(value = "#{IRestRequestService}")
	private IRestRequestService service;

	public DetectOutlierController() {	
		selectedRuleList = new ArrayList<>();
		selectMethod = "";
		selectMeasure = "";
		parametr = "";
		parameterOutlier = "";
	}

	@PostConstruct
	public void init() {
		int idKnowledgeBase = getParametr("baseID");
		attributes = service.listAllAttributes(tokenForRequest(), idKnowledgeBase);
		rules = service.listAllRule(tokenForRequest(), idKnowledgeBase);
		calculate = new PreliminaryCalculationForAlgorythm(rules, attributes);	
		
	}

	public void generateOutliers() {
		initial();
		similaryOutlier = new ArrayList<>();
		similaryMatrixGower = new ArrayList<>();
		showMatriksSimilary = false;
		if(checkCanContinueDetectOutlier()) {
			showProperties = true;
			
			dominantaAsString = calculate.getDominanta().getRule().saveRuleAsString();
			if(selectMeasure.equals("SMC")) {
				calculateSimilarySMC(calculate.getVectorRuleLists(), calculate.getAttributeAdditionDetails(), calculate.getDominanta(), calculate.getAttributeAdditionDetails().size());
			}
			if(selectMeasure.equals("GOWER")) {
				calculateSimilaryGowerDominanta(calculate.getVectorRuleLists(), calculate.getAttributeAdditionDetails(), calculate.getDominanta());
				
			}
		}
	}
	
	private void calculateSimilarySMC(List<SingleVectorRule> vectors, List<AttributeAdditionDetail> details, SingleVectorRule dominanta, int countAttributeAdditionDetails) {
		vsmSimilarySmc = new VSMSimilarySmc(vectors, dominanta, details, countAttributeAdditionDetails);
	}
	
	private void calculateSimilaryGowerDominanta(List<SingleVectorRule> vectors, List<AttributeAdditionDetail> details, SingleVectorRule dominanta) {
		vsmSimilaryGowerDominanta = new VSMSimilaryGowerDominanta(vectors, details, dominanta);
	}
	
	public void selectOutlierSMC() {	
		if(validInputParametr(parameterOutlier)) {
			similaryOutlier = vsmSimilarySmc.getOutlierRules(Integer.parseInt(parameterOutlier));
//			System.out.println();
//			System.out.println("Odchylenia:");
//			for (HelperForCalculateSimilary<SingleVectorRule> help : similaryOutlier) {
//				System.out.println("reguła = " + help.getObject().getRule().getId() + " similary: " + help.getValue());
//			}
		}
	}
	
	public void selectOutlierGower() {
		if(validInputParametr(parameterOutlier)) {
			similaryOutlier = vsmSimilaryGowerDominanta.getOutlierRules(Integer.parseInt(parameterOutlier));
//			System.out.println();
//			System.out.println("Odchylenia:");
//			for (HelperForCalculateSimilary<SingleVectorRule> help : similaryOutlier) {
//				System.out.println("reguła = " + help.getObject().getRule().getId() + " similary: " + help.getValue());
//			}
		}
	}
	

	// akcja na buttona liczenie macierzy podobieństwa
	public void generateMatrixGowerSimilary() {
		System.out.println("akcja na buttona matrix");
		calculateSimilaryGower(calculate.getVectorRuleLists(), calculate.getAttributeAdditionDetails());	
	}
	private void calculateSimilaryGower(List<SingleVectorRule> vectors, List<AttributeAdditionDetail> details) {
		matrixSimilaryGower = new MatrixSimilaryGower(vectors, details);
	}
	
	
	public void selectOutlierFromMatrix() {
		if(validInputParametr(parameterOutlier)) {
			similaryMatrixGower =  matrixSimilaryGower.getOutlierByParametr(Integer.parseInt(parameterOutlier));
//			for(HelperForCalculateSimilary<Cluster> d : similaryMatrixGower) {
//				System.out.println(d.getValue());
//			}
		}
	}
	
	//lisener wykonujący sie po nacisnięciu buttona reguły podobne
	public void ruleSimilaryListener(ActionEvent event) {
		System.out.println("wywołanie listenera");
		showMatriksSimilary = true;
		selectMeasure = "";
		parameterOutlier = "";
		similaryOutlier = new ArrayList<>();
		similaryMatrixGower = new ArrayList<>();
	}
	
	
	public void selectCalculateMethod(ValueChangeEvent e) {
		String method;
		method = (String) e.getNewValue();
		if (method == null){
			selectMethod = "";
			parameterOutlier = "";
			selectMeasure = "";
			showProperties = false;
			showMatriksSimilary = false;
			similaryOutlier = new ArrayList<>();
			similaryMatrixGower = new ArrayList<>();
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
			parameterOutlier = "";
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
		
	private boolean validInputParametr(String value) {
		String patern = "^[1-9][0-9]{0,2}$";
		
		boolean isNumber = value.matches(patern);
		if(!isNumber) {
			addErrorGlobal("Podaj liczbę całkowitą z zakresu [1 - 100]");
			return false;
		} else {
			int count = Integer.parseInt(value);
			if(count > 100) {
				addErrorGlobal("Podaj liczbę całkowitą z zakresu [1 - 100]");
			}
		}
		return true;
	}
	
	
	private void initial() {
		 for(Rule r: rules) {
	    	   selectedRuleList.add(new SelectedRule(false, r));
	       }
	}
	// logic for calculate precission and recall 

	
	  public void chooseRule() {
		  System.out.println("Lisener ");
	    
	    }
	     
	   
	
}
