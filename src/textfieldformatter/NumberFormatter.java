package textfieldformatter;

import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.PlainDocument;

public class NumberFormatter extends PlainDocument{

	private static final long serialVersionUID = -2653881103994770914L;
	int limit = 2;
	public NumberFormatter() {
		super();
	}
	
	public void insertString(int offset, String str, AttributeSet attr) throws BadLocationException{
		if (str == null)
	         return;
		if (getLength() + str.length() <= limit){
			try {
				int x = Integer.parseInt(str); // Test if the input is integer
				super.insertString(offset, str, attr);
			}
			catch (Exception e) {
				
			}
			
		}
	}
}
