package ps.iiproject.rdf.rewriting.parsers;

import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.jena.rdf.model.Resource;
import org.apache.jena.rdf.model.ResourceFactory;

import ps.iiproject.rdf.rewriting.structures.OWLProperty;
import ps.iiproject.rdf.rewriting.structures.OntologyMappingRule;
import ps.iiproject.rdf.rewriting.structures.RdfResource;

public class RDFRewriter {
	// private OntologyMappingParser ontologyMappingParser;
	// https://jena.apache.org/tutorials/rdf_api.html
	// Later this variable will be get from main arguments.
	public static String RdfSourceFile = "D:\\D&K\\Second Period\\InformationIntegration\\Project\\Restaurants\\restaurant1.rdf";

	public static void main(String[] args) {
		List<OntologyMappingRule> mappings;
		try {
			Map<RdfResource, List<OWLProperty>> triples = RdfTripleParser
					.parseFile(RdfSourceFile, "");
			mappings = OntologyMappingParser.getOntologyMappings("out.txt");
			String fileName = "out.rdf";
			FileWriter out = new FileWriter( fileName );
			
			// create an empty Model
			Model model = ModelFactory.createDefaultModel();
			int k = 0;
			for (Map.Entry<RdfResource, List<OWLProperty>> entry : triples
					.entrySet()) {
				List<OntologyMappingRule> validRules = new ArrayList<OntologyMappingRule>();
				for (OntologyMappingRule ontologyMappingRule : mappings) {
					OntologyMappingRule r =fireRuleIfAplicable(entry.getValue(),
							ontologyMappingRule);
					if(r!=null){
						validRules.add(r);
					}
					
//					if(rule != null){
//						break;
//					}
					
				}
				Resource res = model.createResource(entry.getKey().getURI());
				for (OntologyMappingRule rule : validRules) {
					for (OWLProperty prop : entry.getValue()) {
						try {
							if (rule.getSourceClasses()!=null) {
								if (rule.getSourceClasses().contains(prop.getUri())) {
									res.addProperty(ResourceFactory
											.createProperty(rule.getTargetClasses()
													.get(0)), prop.getValue());
								} else {
									res.addProperty(ResourceFactory
											.createProperty(prop.getUri()), prop
											.getValue());
								}
							}
						} catch (NullPointerException e) {
							
							e.printStackTrace();
						}
					}
				}
				
				model.createResource(res);
				k++;
			}
			//model.write(System.out, "RDF/XML-ABBREV");
			model.write( out, "RDF/XML-ABBREV" );
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private static OntologyMappingRule  fireRuleIfAplicable(List<OWLProperty> value,
			OntologyMappingRule ontologyMappingRule) {
		int i = 0;
		for (OWLProperty owlProperty : value) {
			if (ontologyMappingRule.getSourceClasses().contains(
					owlProperty.getUri())) {
				i++;
			}
		}
		if (i == ontologyMappingRule.getSourceClasses().size()) {
			return ontologyMappingRule;
		}
		return null;
	}

}
