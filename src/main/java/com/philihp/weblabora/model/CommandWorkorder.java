package com.philihp.weblabora.model;


public class CommandWorkorder implements MoveCommand, InvalidDuringSettlement {


	@Override
	public void execute(Board board, CommandParameters params)
			throws WeblaboraException {
		
		execute(board,
				Color.toColor(params.get(0).charAt(0)),
				new UsageParam(params.get(1))
				);
		
		//System.out.println("Workorder "+params.get(0)+" with payment "+params.get(1));
	}
	
	public static void execute(Board board, Color orderedPlayerColor, UsageParam payment)
			throws WeblaboraException {
		Player activePlayer = board.getPlayer(board.getActivePlayer());
		Player orderedPlayer = null;
		if(board.getMode().isNeutralPlayerUsed() && orderedPlayerColor.equals(Color.WHITE)) {
			activePlayer = board.getPlayer(0);
			orderedPlayer = board.getMode().getNeutralPlayer();
		}
		else {
			orderedPlayer = board.getPlayer(orderedPlayerColor.ordinal());
		}
		
		if(payment.isWithGift()) {
			activePlayer.subtractWhiskey(payment.getWhiskey());
			activePlayer.subtractWine(payment.getWine());
		}
		else {
			if(board.getStartingMarker().getCost() <= payment.getCoins()) {
				activePlayer.subtractCoins(board.getStartingMarker().getCost());
				orderedPlayer.addPenny(board.getStartingMarker().getCost());
			}
			else throw new WeblaboraException("Insufficient payment, workorders cost "+board.getStartingMarker().getCost()+" coins.");
		}
	}
}
