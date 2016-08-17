package com.mariusz.janus.DetectOutlierRules.web;

import static com.mariusz.janus.DetectOutlierRules.domain.ServerProperty.AUTHORIZATION;
import static com.mariusz.janus.DetectOutlierRules.domain.ServerProperty.BEARER;
import static com.mariusz.janus.DetectOutlierRules.domain.ServerProperty.KNOWLEDGEBASE;
import static com.mariusz.janus.DetectOutlierRules.domain.ServerProperty.SERVER_URL;

import java.io.*;
import java.util.*;
import javax.annotation.PostConstruct;
import javax.faces.bean.*;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.*;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;
import com.mariusz.janus.DetectOutlierRules.domain.AttributeValues;
import com.mariusz.janus.DetectOutlierRules.domain.Rule;
import com.mariusz.janus.DetectOutlierRules.domain.ViewRulesHelper;
import lombok.*;

@ManagedBean
@ViewScoped
public class RulesController extends AbstracController{

	private static final Logger logger = LoggerFactory.getLogger(RulesController.class);
	private static final String FILE_PATH = "C:/Users/Mariusz Janus/Desktop/abc.xlsx";
	
	@Getter @Setter private RestTemplate rest;
	@Getter @Setter private List<Rule> listRules;
	@Getter @Setter private List<AttributeValues> attributeValues;
	@Getter @Setter private List<ViewRulesHelper> listRulesHelper;
	private int index=1;
	
	@ManagedProperty(value = "#{sessionUserController}")
	@Getter @Setter private SessionUserController sessionUser;
	
	
	public RulesController() {
		rest= new RestTemplate();
		listRules = new ArrayList<>();
		listRulesHelper = new ArrayList<>();
	}
	
	@PostConstruct
	public void init()
	{
		requestForRules();
		//writeRuletToFile();
	}
	
	private void requestForRules()
	{
		int idKnowledgeBase = getParametr("baseID");
		
		HttpHeaders header = new HttpHeaders();
		header.add(AUTHORIZATION, BEARER + sessionUser.getAccesToken());
		
		HttpEntity<String> request = new HttpEntity<>(header);
		ResponseEntity<List<Rule>> response = rest.exchange(SERVER_URL + KNOWLEDGEBASE+"/" + idKnowledgeBase +"/rules?all=true", HttpMethod.GET, request,new ParameterizedTypeReference<List<Rule>>() {
		});

		listRules=response.getBody();
		
		for(Rule rules:listRules)
		{
			StringBuilder query = new StringBuilder("");
			int countElement=rules.getAttributeValues().size();
			for(AttributeValues attributes:rules.getAttributeValues())
			{
				query.append(attributes.getAttribute().getName());
				--countElement;
				query.append(" ");
				query.append(attributes.getOperator());
				query.append(" ");
				if(attributes.getValue()!=null) {
					query.append(attributes.getValue().getName());
					countAtributesBakteria(attributes.getValue().getName());
					if(attributes.getAttribute().getName().equals("PROM")) {
						coutProm(attributes.getValue().getName());
					}
					else if(attributes.getAttribute().getName().equals("instrumentalna kontrola jamy macicy")) {
						coutMacica(attributes.getValue().getName());
					}
					else if(attributes.getAttribute().getName().equals("ablacja")){
						coutAblacja(attributes.getValue().getName());
					}
				}
				else {
					query.append(attributes.getContinousValue());
				}
				query.append(" ");
				if(attributes.isConclusion()) {
					query.append("JEŻELI ");
				}
				else {
					if(countElement!=0)
					query.append("ORAZ ");
				}
			}
			logger.debug("reguła ={}",query);
			listRulesHelper.add(new ViewRulesHelper(rules.getId(), rules.getDescription(), query));
		}
		
		
		
		
		logger.debug("Bakteria");
		logger.debug("coutActinetobacter ={} ",coutActinetobacter);
		logger.debug("coutSMRSA ={} ",coutSMRSA);
		logger.debug("coutAtinebacterAndMrsa ={} ",coutAtinebacterAndMrsa);
		logger.debug("coutAtinebacterAndMrsaAndEnterobacteries ={} ",coutAtinebacterAndMrsaAndEnterobacteries);
		logger.debug("coutAlfa ={} ",coutAlfa);
		logger.debug("coutEnterobacteries ={} ",coutEnterobacteries);
		
		logger.debug("Antybiotyk");
		logger.debug("amoksiklav ={} ",amoksiklav);
		logger.debug("amotaks ={} ",amotaks);
		logger.debug("augmentin ={} ",augmentin);
		
		logger.debug("Typ porodu");
		logger.debug("cc ={} ",cc);
		logger.debug("sn ={} ",sn);
		
		logger.debug("PROM");
		logger.debug("tak ={} ",tak);
		logger.debug("nie ={} ",nie);
		
		logger.debug("Ablacja");
		logger.debug("tak ={} ",takab);
		logger.debug("nie ={} ",nieab);
		
		logger.debug("Kontrola jamy macicy");
		logger.debug("tak ={} ",takja);
		logger.debug("nie ={} ",nieja);
		
	}
	
	
	int coutActinetobacter=0;
	int coutSMRSA=0;
	int coutAtinebacterAndMrsa=0;
	int coutAtinebacterAndMrsaAndEnterobacteries=0;
	int coutAlfa=0;
	int coutEnterobacteries=0;
	
	int amoksiklav=0;
	int amotaks=0;
	int augmentin=0;
	
	int cc=0;
	int sn=0;
	
	int tak=0;
	int nie=0;
	
	int takab=0;
	int nieab=0;
	
	int takja=0;
	int nieja=0;
	
	private void countAtributesBakteria(String attribute)
	{
		if(attribute.equals("Actinetobacter")){
			coutActinetobacter++;
		}
		else if(attribute.equals("S.MRSA"))
		{
			coutSMRSA++;
		}
		else if(attribute.equals("Actinetobacter&S.MRSA"))
		{
			coutAtinebacterAndMrsa++;
		}
		else if(attribute.equals("S.MRSA&Enterobacteriaceae&Actinetobacter"))
		{
			coutAtinebacterAndMrsaAndEnterobacteries++;
		}
		else if(attribute.equals("S.alfa"))
		{
			coutAlfa++;
		}
		else if(attribute.equals("Enterobacteriaceae"))
		{
			coutEnterobacteries++;
		}
		else if(attribute.equals("amoksiklav"))
		{
			amoksiklav++;
		}
		else if(attribute.equals("amotaks"))
		{
			amotaks++;
		}
		else if(attribute.equals("augmentin"))
		{
			augmentin++;
		}
		else if(attribute.equals("cc"))
		{
			cc++;
		}
		else if(attribute.equals("sn"))
		{
			sn++;
		}
	}
	
	private void coutProm(String att)
	{
		if(att.equals("tak")){
			tak++;
		}
		else if(att.equals("nie"))
		{
			nie++;
		}
	}
	private void coutAblacja(String att)
	{
		if(att.equals("tak")){
			takab++;
		}
		else if(att.equals("nie"))
		{
			nieab++;
		}
	}
	private void coutMacica(String att)
	{
		if(att.equals("tak")){
			takja++;
		}
		else if(att.equals("nie"))
		{
			nieja++;
		}
	}
	
	@SuppressWarnings("unused")
	private void writeRuletToFile()
	{
		Workbook woorkBook = new XSSFWorkbook();
		
			Sheet sheet = woorkBook.createSheet("Rules");
			int indexRow=0;
			for(ViewRulesHelper rul:listRulesHelper)
			{
				Row row = sheet.createRow(indexRow++);
				
				int cellIndex=0;
				row.createCell(cellIndex++).setCellValue(rul.getId());
				row.createCell(cellIndex++).setCellValue(rul.getQuery().toString());
			}
			 try {
		            FileOutputStream fos = new FileOutputStream(FILE_PATH);
		            woorkBook.write(fos);
		            fos.close();
		            System.out.println(" is successfully written");
		
		         } catch (FileNotFoundException e) {
		            e.printStackTrace();
		         } catch (IOException e) {
		            e.printStackTrace();
		         }
		
	}
	
	public int getIndex()
	{
		return index++;
	}
	
}
