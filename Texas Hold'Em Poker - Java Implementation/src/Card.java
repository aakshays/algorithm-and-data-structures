public class Card {
	String suite;
	int type;
	Card(String suite, int type){
		this.suite = suite;
		this.type = type;
	}
	public String getPrint() {
		String type = this.getType() + "";
		if(this.getType() == 11) type = "Jack";
		else if(this.getType() == 12) type = "Queen";
		else if(this.getType() == 13) type = "King";
		else if(this.getType() == 14) type = "Ace";
		return type + " of " + this.getSuite();
	}	
	public String getSuite() {
		return suite;
	}
	public void setSuite(String suite) {
		this.suite = suite;
	}
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	@Override
	public int hashCode() {
	    final int prime = 31;
	    int result = 1;
	    result = prime * result + suite.charAt(0);
	    result = prime * result + type;
	    return result;
	}

	@Override
	public boolean equals(Object obj) {
	    if (this == obj)
	        return true;
	    if (obj == null)
	        return false;
	    if (getClass() != obj.getClass())
	        return false;
	    Card other = (Card) obj;
	    if (suite != other.getSuite())
	        return false;
	    if (type != other.getType())
	        return false;
	    return true;
	}
}
