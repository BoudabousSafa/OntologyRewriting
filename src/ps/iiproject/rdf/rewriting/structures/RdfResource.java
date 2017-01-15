package ps.iiproject.rdf.rewriting.structures;


public class RdfResource {
	
	String Name;
	String URI;
	String Ontology;
	
	public RdfResource(String Name, String URI, String Ontology){
		this.Name = Name;
		this.URI = URI;
		this.Ontology = Ontology; 
	}
	
	public String getName(){
		return this.Name;
	}
	
	public String getURI(){
		return this.URI;
	}
	
	public String getOntology(){
		return this.Ontology;
	}
	
	public boolean equals(Object obj){
        if (obj instanceof RdfResource) {
        	RdfResource res = (RdfResource) obj;
            return (res.Name.equals(this.Name) && res.URI.equals(this.URI)
            		&&  res.Ontology.equals(this.Ontology));
        } else {
            return false;
        }
    }
	
	public String toString(){
		return this.getURI();
	}

}
