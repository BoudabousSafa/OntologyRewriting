package ps.iiproject.rdf.rewriting.structures;


public class OWLProperty {
	
	private String Name;
	private String Value;
	private String Ontology;
	private String Uri;
	
	public String getUri() {
		return Uri;
	}

	public void setUri(String uri) {
		Uri = uri;
	}

	public void setName(String name) {
		Name = name;
	}

	public void setValue(String value) {
		Value = value;
	}

	public void setOntology(String ontology) {
		Ontology = ontology;
	}

	public OWLProperty(String Name, String Uri,String Value, String Ontology){
		this.Name = Name;
		this.Uri =Uri;
		this.Value = Value;
		this.Ontology = Ontology; 
	}
	
	public String getName(){
		return this.Name;
	}
	
	public String getValue(){
		return this.Value;
	}
	
	public String getOntology(){
		return this.Ontology;
	}
	
	public String toString(){
		return this.getUri();
	}

}
