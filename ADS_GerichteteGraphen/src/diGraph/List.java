/**
 * implementation von List 
 * @author: andre
 */

package diGraph;

public class List {
	private ListItem head; // anfang der liste
	
	// erzeugt eine neue leere liste
	public List() {
		this.head = null;
	}
	
	// fuegt die Double k der liste als neues element hinzu
	public ListItem insert(DiGraphNode k) {
		ListItem toIn = new ListItem(k);
		
		if (this.head == null) { // fall: leere liste -> erstes element
			this.head = toIn;
		} else { // fall: nicht leer -> pointer entsprechend umsetzen
			toIn.next = head;
			head.prev = toIn;
			this.head = toIn;
		}
		
		return toIn;
	} 
	
	// durchsucht die liste nach einem element mit Double k
	public ListItem search(DiGraphNode k) {
		ListItem curr = head;
		
		while(curr != null) { // iteriert ueber alle werte der liste
			if (curr.key.equals(k)) { // gefunden?
				return curr; // fertig!
			}
			curr = curr.next;
		}
		
		return null;
	}
	
	// loescht ein element der liste
	public ListItem delete(ListItem l) {
        // pointer merken
		ListItem pr = l.prev;
		ListItem ne = l.next;
		
		// sonderfaelle: erstes oder letztes element loeschen
		if (pr != null) pr.next = ne;
		if (ne != null) ne.prev = pr;
		
		// sonderfall: liste jetzt leer
		if (head.equals(l)) head = null;
		
		return l;
	} 
	
	// gibt das erste element der liste zurueck
	public ListItem getHead() {
		return this.head;
	}
	
	// gibt das letzte element der liste zurueck
	public ListItem getTail() {
		ListItem last = head;
		while (last.next != null) { // solange es noch ein weiteres element gibt ...
            last = last.next; // ... gehe zum naechsten element der liste
        }
		return last;
	}

}
 
