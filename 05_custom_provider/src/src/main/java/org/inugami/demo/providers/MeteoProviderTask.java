/* --------------------------------------------------------------------
 *  Inugami  
 * --------------------------------------------------------------------
 * 
 * This program is free software: you can redistribute it and/or modify  
 * it under the terms of the GNU General Public License as published by  
 * the Free Software Foundation, version 3.
 *
 * This program is distributed in the hope that it will be useful, but 
 * WITHOUT ANY WARRANTY; without even the implied warranty of 
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU 
 * General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License 
 * along with this program. If not, see <http://www.gnu.org/licenses/>.
 */
package org.inugami.demo.providers;

import java.nio.charset.Charset;

import org.inugami.api.exceptions.services.ConnectorException;
import org.inugami.api.models.Gav;
import org.inugami.api.models.data.basic.Json;
import org.inugami.api.models.events.GenericEvent;
import org.inugami.api.models.events.SimpleEvent;
import org.inugami.api.processors.ConfigHandler;
import org.inugami.api.providers.task.ProviderFutureResult;
import org.inugami.api.providers.task.ProviderFutureResultBuilder;
import org.inugami.api.providers.task.ProviderTask;
import org.inugami.commons.connectors.HttpConnectorResult;
import org.inugami.core.services.connectors.HttpConnector;

public class MeteoProviderTask implements ProviderTask {
    
    // =========================================================================
    // ATTRIBUTES
    // =========================================================================
    private final static Charset                UTF_8 = Charset.forName("UTF-8");
    
    private final Gav                           gav;
    
    private final SimpleEvent                   event;
    
    private final HttpConnector                 httpConnector;
    
    private final ConfigHandler<String, String> config;
    
    // =========================================================================
    // CONSTRUCTORS
    // =========================================================================
    public MeteoProviderTask(final SimpleEvent event, final Gav gav, final HttpConnector httpConnector,
                              final ConfigHandler<String, String> config) {
        super();
        this.gav = gav;
        this.event = event;
        this.httpConnector = httpConnector;
        this.config = config;
    }
    
    // =========================================================================
    // METHODS
    // =========================================================================
    @Override
    public ProviderFutureResult callProvider() {
        ProviderFutureResult result = null;
        final String url = event.getQuery() == null ? null : config.applyProperties(event.getQuery());
        
        if (url != null) {
            try {
                result = processCallApi(url);
            }
            catch (ConnectorException e) {
                result = buildErrorResult(e);
            }
        }
        return result;
    }
    
    private ProviderFutureResult processCallApi(String url) throws ConnectorException {
        final HttpConnectorResult httpResult = httpConnector.get(url);
        final ProviderFutureResultBuilder result = initializeBuilder();
        
        if (httpResult.getStatusCode() >= 200 && httpResult.getStatusCode() < 400) {
            String json = new String(httpResult.getData(), UTF_8);
            
            result.addData(new Json(json));
        }
        return result.build();
    }
    
    // =========================================================================
    // BUILDER
    // =========================================================================
    private ProviderFutureResult buildErrorResult(Exception error) {
        final ProviderFutureResultBuilder builder = initializeBuilder();
        builder.addException(error);
        return builder.build();
    }
    
    private ProviderFutureResultBuilder initializeBuilder() {
        final ProviderFutureResultBuilder result = new ProviderFutureResultBuilder();
        result.addEvent(event);
        return result;
    }
    
    // =========================================================================
    // GETTERS & SETTERS
    // =========================================================================
    @Override
    public GenericEvent getEvent() {
        return event;
    }
    
    @Override
    public Gav getPluginGav() {
        return gav;
    }
    
}
