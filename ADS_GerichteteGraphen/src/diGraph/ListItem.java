/**
 * implementation von ListItem (Tutoriumsblatt 3)
 * @author: andre
 */

package diGraph;

public class ListItem {
	public DiGraphNode key; // gespeicherter wert
	public ListItem prev; // naechstes element
	public ListItem next; // vorheriges element
	
	// erzeugt ein neues element mit gegebenem wert Double k. 
	// pointer sind von der List selbst zu setzen!
	public ListItem(DiGraphNode k) {
		this.key  = k;
		this.prev = null;
		this.next = null;
	}
 
}
