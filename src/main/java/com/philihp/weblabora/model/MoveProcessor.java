package com.philihp.weblabora.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.StringTokenizer;

import org.apache.commons.chain.Command;
import org.apache.commons.chain.Context;

public final class MoveProcessor {
	private MoveProcessor() {
	}

	public static void processMoves(Board board, String moves) throws WeblaboraException {
		board.currentMove++;

		for (String move : moves.split("\\|")) {
			System.out.println("move: " + move);
			processSingleMove(board, move);
		}

		if (board.currentMove % 5 == 1) {
			board.getWheel().pushArm();
		}
	}

	public static void processSingleMove(Board board, String move) throws WeblaboraException {
		Scanner scanner = new Scanner(move);
		scanner.useDelimiter("\\(");
		char command = Character.toUpperCase(scanner.next().charAt(0));
		scanner.useDelimiter("[\\(,\\)]");
		List<String> paramList = new ArrayList<String>(2);
		while (scanner.hasNext()) {
			String param = scanner.next();
			paramList.add(param);
		}
		
		switch(command) {
		case 'F':
			CommandFellTrees.execute(board, Integer.parseInt(paramList.get(0)), Integer.parseInt(paramList.get(1)));
			break;
		case 'C':
			CommandCutPeat.execute(board, Integer.parseInt(paramList.get(0)), Integer.parseInt(paramList.get(1)));
			break;
		default:
			throw new WeblaboraException("Unknown Command \""+move+"\"");
		}

	}
}
