package com.mariusz.janus.DetectOutlierRules.web;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import com.mariusz.janus.DetectOutlierRules.domain.AttributeValues;
import com.mariusz.janus.DetectOutlierRules.domain.Rule;
import com.mariusz.janus.DetectOutlierRules.domain.ServerProperty;
import com.mariusz.janus.DetectOutlierRules.domain.ViewRulesHelper;

@ManagedBean
@ViewScoped
public class RulesController extends AbstracUtility{

	private static final Logger logger = LoggerFactory.getLogger(RulesController.class);
	
	private static final String FILE_PATH = "C:/Users/Mariusz Janus/Desktop/abc.xlsx";
	private RestTemplate rest;
	private List<Rule> listRules;
	private List<AttributeValues> attributeValues;
	private List<ViewRulesHelper> listRulesHelper;
	
	@ManagedProperty(value = "#{sessionUserController}")
	private SessionUserController sessionUser;
	
	
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
		RestTemplate rest = new RestTemplate();
		HttpHeaders header = new HttpHeaders();
		header.add("Authorization","Bearer "+sessionUser.getToken().getAccess_token());
		
		HttpEntity<String> request = new HttpEntity<>(header);
		ResponseEntity<List<Rule>> response = rest.exchange(ServerProperty.SERVER_URL+ServerProperty.KNOWLEDGEBASE+"/"+Integer.parseInt(getParametr("baseID"))+"/rules?all=true", HttpMethod.GET, request,new ParameterizedTypeReference<List<Rule>>() {
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
				if(attributes.getValue()!=null)
				{
					query.append(attributes.getValue().getName());
				}
				else
				{
					query.append(attributes.getContinousValue());
				}
				query.append(" ");
				if(attributes.isConclusion())
				{
					query.append("JEŻELI ");
				}
				else
				{
					if(countElement!=0)
					query.append("ORAZ ");
				}
			}
			logger.debug("reguła ={}",query);
			listRulesHelper.add(new ViewRulesHelper(rules.getId(), rules.getDescription(), query));
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
	
	
	public RestTemplate getRest() {
		return rest;
	}
	public void setRest(RestTemplate rest) {
		this.rest = rest;
	}
	public List<Rule> getListRules() {
		return listRules;
	}
	public void setListRules(List<Rule> listRules) {
		this.listRules = listRules;
	}
	public SessionUserController getSessionUser() {
		return sessionUser;
	}
	public void setSessionUser(SessionUserController sessionUser) {
		this.sessionUser = sessionUser;
	}
	public List<AttributeValues> getAttributeValues() {
		return attributeValues;
	}
	public void setAttributeValues(List<AttributeValues> attributeValues) {
		this.attributeValues = attributeValues;
	}

	public List<ViewRulesHelper> getListRulesHelper() {
		return listRulesHelper;
	}

	public void setListRulesHelper(List<ViewRulesHelper> listRulesHelper) {
		this.listRulesHelper = listRulesHelper;
	}
	
	
	
	
}
