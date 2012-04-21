package com.philihp.weblabora.model;

import static com.philihp.weblabora.model.TerrainTypeEnum.*;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.List;

import com.philihp.weblabora.model.building.*;

public class CommandBuild implements MoveCommand, InvalidDuringSettlement {


	@Override
	public void execute(Board board, CommandParameters params)
			throws WeblaboraException {
		
		execute(board,
				params.get(0),
				Integer.parseInt(params.get(1)),
				Integer.parseInt(params.get(2)));
		
		System.out.println("Building "+params.get(0)+" at ("+params.get(1)+","+params.get(2)+")");
	}
	
	public static void execute(Board board, String buildingId, int x, int y)
			throws WeblaboraException {
		Building building = null;
		Player player = board.getPlayer(board.getActivePlayer());
		Terrain location = player.getLandscape().getTerrain().get(y, x);
		
		//remove the building from the unused buildings
		for(Building possibleBuilding : board.getUnbuiltBuildings()) {
			if(possibleBuilding.getId().equals(buildingId)) {
				building = possibleBuilding;
				board.getUnbuiltBuildings().remove(building);
				break;
			}
		}
		if(building == null) {
			throw new WeblaboraException("Building "+buildingId+" was not be found in unbuilt buildings");
		}

		if(location.getErection() != null) {
			throw new WeblaboraException("There is already an erection at ("+x+","+y+"): "+location.getErection());
		}
		
		if(building.getTerrains().contains(location.getTerrainType()) == false) {
			throw new WeblaboraException("The location at ("+x+","+y+") has a terrain of "+location.getTerrainType()+", but "+building.getName()+" can only be built on "+building.getTerrains());
		}
		
		if(player.canAffordCost(building.getBuildCost()) == false) {
			throw new WeblaboraException("Player could not afford "+building.getBuildCost()+" to build "+building.getName());
		}
		
		player.subtractAll(building.getBuildCost());
		location.setErection(building);
		
		//call the building's hook, in case it needs to do something. i think this is just the winery.
		building.build(board);
	}
}
