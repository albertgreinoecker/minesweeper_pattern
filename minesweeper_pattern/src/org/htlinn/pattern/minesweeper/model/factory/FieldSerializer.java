package org.htlinn.pattern.minesweeper.model.factory;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

import org.htlinn.pattern.minesweeper.model.Field;
import org.htlinn.pattern.minesweeper.model.Playground;

public class FieldSerializer {

	private FieldSerializer() {
	}

	public static void save(File f,Playground pg) throws IOException
	{
		FileWriter w = new FileWriter(f);
		w.write(pg.toString() );
		w.close();
	}
	
	/**
	 * @param f The file which holds the playground matrix
	 * @param pg The matrix is loaded into this playground instance
	 * @throws FileNotFoundException
	 */
	public static void load(File f, Playground pg) throws FileNotFoundException
	{
		Scanner s = new Scanner(f);
		Field[][] field = new Field[pg.getWidth()][pg.getHeight()];
		int lPos = 0;
		while (s.hasNextLine())
		{
			String l = s.nextLine();


			for (int i = 0; i < l.length();i+=2)
			{
				String part = l.substring(i,i+2);
				System.out.printf("(%d %d %d %s)\n", lPos, i, i/2, part);
				
				field[lPos][i/2] = FieldFactory.getField(part);
				System.out.printf("%s => %s", field[lPos][i/2], part);
			}
			lPos++;
		}
		s.close();
		pg.setMatrix(field);
	}
}
