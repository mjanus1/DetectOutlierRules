package com.mariusz.janus.DetectOutlierRules.service;

import java.util.List;

import com.mariusz.janus.DetectOutlierRules.domain.Attribute;

public interface IRestRequest{

	List<Attribute> requestForAllAttributes(String token, int baseId);
	int countAttributes(String token, int baseId);
}
