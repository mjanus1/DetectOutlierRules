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

import com.mariusz.janus.DetectOutlierRules.domain.AttributeValues;
import com.mariusz.janus.DetectOutlierRules.domain.Rule;
import com.mariusz.janus.DetectOutlierRules.domain.ViewRulesHelper;
import com.mariusz.janus.DetectOutlierRules.service.IRestRequestService;

import lombok.Getter;
import lombok.Setter;

@ManagedBean
@ViewScoped
public class RulesController extends AbstracController {

	private static final Logger logger = LoggerFactory.getLogger(RulesController.class);
	private static final String FILE_PATH = "C:/Users/Mariusz Janus/Desktop/abc.xlsx";

	@Getter
	@Setter
	private List<Rule> listRules;
	@Getter
	@Setter
	private List<AttributeValues> attributeValues;
	@Getter
	@Setter
	private List<ViewRulesHelper> listRulesHelper;

	private int index = 1;

	@Getter
	@Setter
	@ManagedProperty(value = "#{IRestRequestService}")
	private IRestRequestService service;

	public RulesController() {
		listRulesHelper = new ArrayList<>();
	}

	@PostConstruct
	public void init() {
		requestForRules();
		// writeRuletToFile();
	}

	private void requestForRules() {
		listRules = service.listAllRule(tokenForRequest(), getParametr("baseID"));
		logger.debug("Pobrana liczba reguł ={}",listRules.size());
		formatRules();
	}

	private void formatRules() {
		for (Rule rules : listRules) {
			StringBuilder query = new StringBuilder("");
			int countElement = rules.getAttributeValues().size();
			for (AttributeValues attributes : rules.getAttributeValues()) {
				query.append(attributes.getAttribute().getName());
				--countElement;
				query.append(" ");
				query.append(attributes.getOperator());
				query.append(" ");
				if (attributes.getValue() != null) {
					query.append(attributes.getValue().getName());
				} else {
					query.append(attributes.getContinousValue());
				}
				query.append(" ");

				if (attributes.isConclusion()) {
					query.append("JEŻELI ");
				} else {
					if (countElement != 0)
						query.append("ORAZ ");
				}
			}
			//logger.debug("reguła ={}", query);
			listRulesHelper.add(new ViewRulesHelper(rules.getId(), rules.getDescription(), query));
		}
	}

	@SuppressWarnings("unused")
	private void writeRuletToFile() {
		Workbook woorkBook = new XSSFWorkbook();

		Sheet sheet = woorkBook.createSheet("Rules");
		int indexRow = 0;
		for (ViewRulesHelper rul : listRulesHelper) {
			Row row = sheet.createRow(indexRow++);

			int cellIndex = 0;
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

	public int getIndex() {
		return index++;
	}
}
