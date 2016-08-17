package com.mariusz.janus.DetectOutlierRules.service;

import java.util.List;

import com.mariusz.janus.DetectOutlierRules.domain.Attribute;
import com.mariusz.janus.DetectOutlierRules.domain.Fact;
import com.mariusz.janus.DetectOutlierRules.domain.KnowledgeBase;
import com.mariusz.janus.DetectOutlierRules.domain.Rule;
import com.mariusz.janus.DetectOutlierRules.domain.User;

public interface IRestRequestService{

	List<Attribute> listAllAttributes(String token, int baseId);
	int countAttributes(String token, int baseId);
	
	List<Rule> listAllRule(String token, int baseId);
	int countRules(String token, int baseId);
	
	List<Fact> listAllFact(String token, int baseId);
	int countFact(String token, int baseId);
	
	List<KnowledgeBase> listAllKnowledgeBase(String token);
	KnowledgeBase knowledgeBase(String token, int baseId);
	
	User user(String token, int baseId);
}
