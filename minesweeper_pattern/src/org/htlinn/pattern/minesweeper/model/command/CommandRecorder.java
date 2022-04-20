package org.htlinn.pattern.minesweeper.model.command;

import java.util.Stack;

public class CommandRecorder 
	{
		private Stack<PlayCommandInterface> undo = new Stack<>();
		private Stack<PlayCommandInterface> redo = new Stack<>();
		
		private static CommandRecorder instance;
		
		private CommandRecorder()
		{
			
		}
		
		public static CommandRecorder instance()
		{
			if (instance == null)
			{
				instance = new CommandRecorder();
			}
			return instance;
		}
		
		
		public void doIt(PlayCommandInterface ci)
		{
			ci.doIt();
			undo.push(ci);
		}
		
		public void undo()
		{
			PlayCommandInterface ci = undo.pop();
			redo.push(ci);
			ci.undo();	
		}
		
		public void redo()
		{
			PlayCommandInterface ci = redo.pop();
			undo.push(ci);
			ci.doIt();
		}
	}
