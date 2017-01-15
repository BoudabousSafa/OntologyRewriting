package ps.iiproject.rdf.rewriting.parsers;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;

import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.jena.rdf.model.Property;
import org.apache.jena.rdf.model.RDFNode;
import org.apache.jena.rdf.model.Resource;
import org.apache.jena.rdf.model.Statement;
import org.apache.jena.rdf.model.StmtIterator;
import org.apache.jena.util.FileManager;

import ps.iiproject.rdf.rewriting.structures.OWLProperty;
import ps.iiproject.rdf.rewriting.structures.RdfResource;

public final class RdfTripleParser {
	
	
	@SuppressWarnings("rawtypes")
	public static LinkedHashMap<RdfResource, List<OWLProperty>> parseFile(String inputFileName, String ontologyName){
		
		LinkedHashMap<RdfResource, List<OWLProperty>> rdf = new LinkedHashMap<RdfResource, List<OWLProperty>>();
		 // create an empty model
		 Model model = ModelFactory.createDefaultModel();
		 // use the FileManager to find the input file
		 InputStream in = FileManager.get().open( inputFileName );
		 if (in == null) {
		    throw new IllegalArgumentException(
		                                 "File: " + inputFileName + " not found");
		}

		// read the RDF/XML file
		model.read(in, null);
		StmtIterator iter = model.listStatements();
		Property predicate;
		Resource subject;
		RDFNode object;
		
		while (iter.hasNext()) {
			Statement stmt = iter.next();
			subject = stmt.getSubject();
			//System.out.println("subject " + subject);
			predicate = stmt.getPredicate();
			object = stmt.getObject();
			boolean resourceExist = false;
			List<OWLProperty> owlProperties = new ArrayList<OWLProperty>();
			Iterator<Entry<RdfResource, List<OWLProperty>>> it = rdf.entrySet().iterator();
			RdfResource rdfRes = new RdfResource(subject.getLocalName(), subject.getURI(),
					ontologyName);
			OWLProperty owlProperty = new OWLProperty(predicate.getLocalName(),predicate.getURI() ,object.toString(),
					ontologyName);
			
			while (it.hasNext() && (resourceExist  == false)) {
		        Map.Entry resource = (Map.Entry)it.next();
		        if(rdfRes.equals(resource.getKey())) {
		        	resourceExist = true;
		        	rdfRes = (RdfResource)resource.getKey();
		        }
		    }
			
			if(!resourceExist) {
				owlProperties.add(owlProperty);
				rdf.put(rdfRes, owlProperties);
			}
			else {
				owlProperties = rdf.get(rdfRes);
				owlProperties.add(owlProperty);
			}
	    }
	//	System.out.println(rdf);
		return rdf;
	}
	
	
	public static void main(String[] args) throws Exception {
    	args = new String[] {"D:\\D&K\\Second Period\\InformationIntegration\\Project\\Restaurants\\restaurant1.rdf"};
    	LinkedHashMap<RdfResource, List<OWLProperty>>  triples = parseFile(args[0], "1");
    	for (Map.Entry<RdfResource, List<OWLProperty>> entry : triples.entrySet())
    	{
    	    System.out.println(entry.getKey() + "/" + entry.getValue().toString());
    	}
    	
	}
	
}
