package com.mariusz.janus.DetectOutlierRules.service;

import java.util.List;

import com.mariusz.janus.DetectOutlierRules.domain.Attribute;
import com.mariusz.janus.DetectOutlierRules.domain.Fact;
import com.mariusz.janus.DetectOutlierRules.domain.KnowledgeBase;
import com.mariusz.janus.DetectOutlierRules.domain.Rule;
import com.mariusz.janus.DetectOutlierRules.domain.Token;
import com.mariusz.janus.DetectOutlierRules.domain.User;

public interface IRestRequestService{

	List<Attribute> listAllAttributes(Token token, int baseId);
	int countAttributes(Token token, int baseId);
	
	List<Rule> listAllRule(Token token, int baseId);
	int countRules(Token token, int baseId);
	
	List<Fact> listAllFact(Token token, int baseId);
	int countFact(Token Token, int baseId);
	
	List<KnowledgeBase> listAllKnowledgeBase(Token token);
	KnowledgeBase knowledgeBase(Token Token, int baseId);
	
	User user(Token token, int baseId);
}
