package com.showka;

import java.util.ArrayList;
import java.util.HashMap;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class DomainConfig {
	public String domain;
	public String builderBaseClass;
	public ArrayList<HashMap<String, String>> members;
}
