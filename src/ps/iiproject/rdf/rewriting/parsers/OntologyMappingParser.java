package ps.iiproject.rdf.rewriting.parsers;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import ps.iiproject.rdf.rewriting.structures.OntologyMappingRule;

public final class OntologyMappingParser {

	public static void main(String argv[]) {

		try {

			SAXParserFactory factory = SAXParserFactory.newInstance();
			SAXParser saxParser = factory.newSAXParser();

			DefaultHandler handler = new DefaultHandler() {

				boolean descname = false;
				boolean entity1name = false;
				boolean entity2name = false;
				boolean mapttoname = false;
				boolean mapTo = false;
				OntologyMappingRule rule;
				List<OntologyMappingRule> rules = new ArrayList<OntologyMappingRule>();

				public void startElement(String uri, String localName,
						String qName, Attributes attributes)
						throws SAXException {

					System.out.println("Start Element :" + qName);

					if (qName.equalsIgnoreCase("rdf:Description")) {
						descname = true;
						rule = new OntologyMappingRule();

					}

					if (qName.equalsIgnoreCase("j.0:entity1")) {
						entity1name = true;
						if (!mapTo) {
							if (rule.getSourceClasses() == null) {
								List<String> owlCalss = new ArrayList<String>();
								owlCalss.add(attributes.getValue(attributes
										.getQName(0)));
								rule.setSourceClasses(owlCalss);
							} else {
								rule.getSourceClasses().add(
										attributes.getValue(attributes
												.getQName(0)));
							}
						} else {
							if (rule.getTargetClasses() == null) {
								List<String> owlCalss = new ArrayList<String>();
								owlCalss.add(attributes.getValue(attributes
										.getQName(0)));
								rule.setTargetClasses(owlCalss);
							} else {
								rule.getTargetClasses().add(
										attributes.getValue(attributes
												.getQName(0)));
							}
						}
						System.out.println(attributes.getValue(attributes
								.getQName(0)));
					}

					if (qName.equalsIgnoreCase("j.0:entity2")) {
						entity2name = true;
						if (!mapTo) {
							if (rule.getSourceClasses() == null) {
								List<String> owlCalss = new ArrayList<String>();
								owlCalss.add(attributes.getValue(attributes
										.getQName(0)));
								rule.setSourceClasses(owlCalss);
							} else {
								rule.getSourceClasses().add(
										attributes.getValue(attributes
												.getQName(0)));
							}
						} else {
							if (rule.getTargetClasses() == null) {
								List<String> owlCalss = new ArrayList<String>();
								owlCalss.add(attributes.getValue(attributes
										.getQName(0)));
								rule.setTargetClasses(owlCalss);
							} else {
								rule.getTargetClasses().add(
										attributes.getValue(attributes
												.getQName(0)));
							}
						}
					}

					if (qName.equalsIgnoreCase("j.0:mapTo")) {
						mapttoname = true;
						mapTo = true;
					}

				}

				public void endElement(String uri, String localName,
						String qName) throws SAXException {

					System.out.println("End Element :" + qName);
					if (qName.equalsIgnoreCase("rdf:Description")) {
						rules.add(rule);
						System.out.println("blabla");
					}
					if (qName.equalsIgnoreCase("j.0:mapTo")) {
						mapTo = false;
						System.out.println("blabla");
					}

				}

				public void characters(char ch[], int start, int length)
						throws SAXException {

				}
				
				public void endDocument(){
					File fout = new File("out.txt");
					FileOutputStream fos;
					try {
						fos = new FileOutputStream(fout);
						BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(fos));

						for (OntologyMappingRule ontologyMappingRule : rules) {
							bw.write(ontologyMappingRule.getSourceClasses().toString()+ "\t"+ ontologyMappingRule.getTargetClasses().toString());
							bw.newLine();
						}
						bw.close();
					} catch (FileNotFoundException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

					
				}

			};

			saxParser
					.parse("D:\\D&K\\Second Period\\InformationIntegration\\Project\\mapping_restaurant.rdf",
							handler);
			List<OntologyMappingRule> test = getOntologyMappings("out.txt");
			System.out.println("End main class ...");

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
 
	public static List<OntologyMappingRule> getOntologyMappings(String filename) throws FileNotFoundException, IOException{
		List<OntologyMappingRule> results = new ArrayList<OntologyMappingRule>();
		try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
		    String line;
		    while ((line = br.readLine()) != null) {
		       String[] predicates= line.split("\t");
		       OntologyMappingRule rule = new OntologyMappingRule();
		       rule.setSourceClasses(processPredicates(predicates[0]));
		       rule.setTargetClasses(processPredicates(predicates[1]));
		       results.add(rule);
		    }
		}
		return results;
	}

	private static List<String> processPredicates(String string) {
		List<String> result = new ArrayList<String>();
		String owlproperties = string.substring(1, string.length()-1);
		for (String s : owlproperties.split(",")) {
			result.add(s);
		}
		return result;
	}


}
