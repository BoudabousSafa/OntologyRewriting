package ps.iiproject.rdf.rewriting.structures;
import java.util.List;


public class OntologyMappingRule {
	
	private List<String> sourceClasses;
	private List<String> targetClasses;
	public List<String> getSourceClasses() {
		return sourceClasses;
	}
	public void setSourceClasses(List<String> sourceClasses) {
		this.sourceClasses = sourceClasses;
	}
	public List<String> getTargetClasses() {
		return targetClasses;
	}
	public void setTargetClasses(List<String> targetClasses) {
		this.targetClasses = targetClasses;
	}
	

}
