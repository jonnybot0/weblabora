package com.philihp.weblabora.model;

import static com.philihp.weblabora.model.TerrainTypeEnum.*;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.List;

import com.philihp.weblabora.model.building.*;

public class CommandBuild implements MoveCommand {


	@Override
	public void execute(Board board, List<String> params)
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
		Terrain spot = player.getLandscape().getTerrain().get(y, x);
		
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

		if(spot.getErection() != null) {
			throw new WeblaboraException("There is already an erection at ("+x+","+y+"): "+spot.getErection());
		}
		
		if(building.getTerrains().contains(spot.getTerrainType()) == false) {
			throw new WeblaboraException("The location at ("+x+","+y+") has a terrain of "+spot.getTerrainType()+", which is not appropriate for "+building);
		}
		
		if(player.canAffordCost(building.getBuildCost()) == false) {
			throw new WeblaboraException("Player could not afford build cost");
		}
		
		player.payBuildCost(building.getBuildCost());
		spot.setErection(building);

	}
}