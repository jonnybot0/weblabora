package com.philihp.weblabora.action;

import java.util.Arrays;
import java.util.List;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.philihp.weblabora.form.GameForm;
import com.philihp.weblabora.jpa.Game;
import com.philihp.weblabora.jpa.State;
import com.philihp.weblabora.jpa.User;
import com.philihp.weblabora.model.Board;
import com.philihp.weblabora.model.MoveProcessor;
import com.philihp.weblabora.model.Player;
import com.philihp.weblabora.model.WeblaboraException;
import com.philihp.weblabora.util.EntityManagerManager;
import com.philihp.weblabora.util.FacebookCredentials;

public class ShowGameState extends BaseAction {

	@Override
	public ActionForward execute(ActionMapping mapping, ActionForm actionForm, HttpServletRequest request,
			HttpServletResponse response, User user) throws Exception {

		request.setAttribute("myGames", ShowGame.findGamesForUser(user));
		
		Game game = (Game)request.getAttribute("game");
		
		request.setAttribute("board", prepareBoard(game));
		request.setAttribute("savedMove", findSavedMove(game, user)); 
		
		return mapping.findForward("view");
	}

	protected static Board prepareBoard(Game game) throws WeblaboraException {
		Board board = null;
		if(game != null) {
			board = new Board();
			board.populateDetails(game);
			MoveProcessor.processMoves(board, game.getStates());
			if(board.isGameOver() == false)
				board.preMove(new State()); //upkeep stuff before player makes a move	
		}
		return board;
	}

	
	protected static String findSavedMove(Game game, User user) {
		Game.Player player = ShowGame.findPlayerInGame(game, user);
		if(player == null) return null;
		return player.getMove();
	}
}
