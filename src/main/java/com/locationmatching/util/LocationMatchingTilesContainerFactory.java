package com.locationmatching.util;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.el.ArrayELResolver;
import javax.el.BeanELResolver;
import javax.el.CompositeELResolver;
import javax.el.ELResolver;
import javax.el.ListELResolver;
import javax.el.MapELResolver;
import javax.el.ResourceBundleELResolver;

import org.apache.tiles.TilesApplicationContext;
import org.apache.tiles.context.TilesRequestContextFactory;
import org.apache.tiles.definition.DefinitionsFactoryException;
import org.apache.tiles.el.ELAttributeEvaluator;
import org.apache.tiles.el.JspExpressionFactoryFactory;
import org.apache.tiles.el.TilesContextBeanELResolver;
import org.apache.tiles.el.TilesContextELResolver;
import org.apache.tiles.evaluator.AttributeEvaluatorFactory;
import org.apache.tiles.evaluator.BasicAttributeEvaluatorFactory;
import org.apache.tiles.evaluator.impl.DirectAttributeEvaluator;
import org.apache.tiles.factory.BasicTilesContainerFactory;
import org.apache.tiles.locale.LocaleResolver;

public class LocationMatchingTilesContainerFactory extends BasicTilesContainerFactory {
	
	protected List<URL> getSourceURLs(TilesApplicationContext applicationContext, TilesRequestContextFactory contextFactory) {
		List<URL> urls = new ArrayList<URL>();
		
		try{
			urls.add(applicationContext.getResource("/WEB-INF/tiles-ProviderDefs.xml"));
			urls.add(applicationContext.getResource("/WEB-INF/tiles-ScoutDefs.xml"));
		}
		catch(IOException ex) {
			ex.printStackTrace();
			throw new DefinitionsFactoryException("Cannot load definition URLs", ex);
		}
		return urls;
	}
	
	protected AttributeEvaluatorFactory createAttributeEvaluatorFactory(
	        TilesApplicationContext applicationContext,
	        TilesRequestContextFactory contextFactory, LocaleResolver resolver) {
	    //... Initialization of the factory.
	    BasicAttributeEvaluatorFactory attributeEvaluatorFactory = new BasicAttributeEvaluatorFactory(
	            new DirectAttributeEvaluator());
	    
	    ELAttributeEvaluator evaluator = new ELAttributeEvaluator();
	    evaluator.setApplicationContext(applicationContext);
	    JspExpressionFactoryFactory efFactory = new JspExpressionFactoryFactory();
	    efFactory.setApplicationContext(applicationContext);
	    evaluator.setExpressionFactory(efFactory.getExpressionFactory());
	    
	    // Create the different resolvers.
	    ELResolver elResolver = new CompositeELResolver() {
	        {
	            add(new TilesContextELResolver());
	            add(new TilesContextBeanELResolver());
	            add(new ArrayELResolver(false));
	            add(new ListELResolver(false));
	            add(new MapELResolver(false));
	            add(new ResourceBundleELResolver());
	            add(new BeanELResolver(false));
	        }
	    };
	    evaluator.setResolver(elResolver);
	    attributeEvaluatorFactory.registerAttributeEvaluator("EL", evaluator);

	    return attributeEvaluatorFactory;
	}

}
