package com.showka;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

import org.yaml.snakeyaml.Yaml;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;

public class BuilderGenerater {

	private static String CONFIG_PATH = "domain-config.yaml";
	private static String TEMPLATE_FILE = "BuilderTemplate.ftl";
	private static String DESTINATION = "generated/";

	public static void main(String[] args) {

		// config
		DomainConfig domainConfig = loadDataYaml(DomainConfig.class, CONFIG_PATH);

		// configuration
		Configuration fileMarkerConfig = new Configuration(Configuration.VERSION_2_3_23);
		fileMarkerConfig.setClassForTemplateLoading(BuilderGenerater.class, "/");

		try {

			// template
			Template template = fileMarkerConfig.getTemplate(TEMPLATE_FILE);

			// map
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("builderBaseClass", domainConfig.getBuilderBaseClass());
			map.put("domain", domainConfig.getDomain());
			map.put("members", domainConfig.getMembers());

			StringBuilder memberListWithComma = new StringBuilder();
			for (HashMap<String, String> m : domainConfig.getMembers()) {
				memberListWithComma.append("," + m.get("name"));
			}
			map.put("memberListWithComma", memberListWithComma.toString().replaceAll("^,", ""));

			// write
			File file = new File(DESTINATION + domainConfig.getDomain() + "DomainBuilder.java");
			PrintWriter writer = new PrintWriter(new BufferedWriter(new FileWriter(file)));
			template.process(map, writer);

		} catch (IOException | TemplateException e) {
			e.printStackTrace();
		}
		System.out.println("Success!!");
	}

	private static Yaml yaml = new Yaml();

	/**
	 * yaml load
	 * 
	 * @param class1
	 * @param path
	 * @return
	 */
	public static <T> T loadDataYaml(Class<T> class1, String path) {
		return (T) yaml.loadAs(ClassLoader.getSystemResourceAsStream(path), class1);
	}
}
