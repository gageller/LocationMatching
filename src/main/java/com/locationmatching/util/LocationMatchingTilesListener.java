package com.locationmatching.util;

import org.apache.tiles.TilesApplicationContext;
import org.apache.tiles.factory.AbstractTilesContainerFactory;
import org.apache.tiles.startup.AbstractTilesInitializer;
import org.apache.tiles.startup.TilesInitializer;
import org.apache.tiles.web.startup.AbstractTilesListener;

public class LocationMatchingTilesListener extends AbstractTilesListener {

	@Override
	protected TilesInitializer createTilesInitializer() {
		return new LocationMatchingTilesInitializer();
	}
	
	private static class LocationMatchingTilesInitializer extends AbstractTilesInitializer {

		@Override
		protected AbstractTilesContainerFactory createContainerFactory(TilesApplicationContext context) {
			return new LocationMatchingTilesContainerFactory();
		}
		
	}
}
