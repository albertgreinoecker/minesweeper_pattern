package org.htlinn.pattern.minesweeper.model.factory;

import org.htlinn.pattern.minesweeper.model.BombField;
import org.htlinn.pattern.minesweeper.model.EmptyField;
import org.htlinn.pattern.minesweeper.model.Field;

public class FieldFactory {

	private FieldFactory() {
		
	}
	
	public static Field getField(String s)
	{
		char c1 = s.charAt(0);
		char c2 = s.charAt(1);
		boolean open = (c2 != 'C');
		int cnt = 0;
		if (c1 == '*') return new BombField(open);
		if (c1 != '_')
		{
			cnt = c1 -'0';
		}
		return new EmptyField(cnt,open);
	}

}
