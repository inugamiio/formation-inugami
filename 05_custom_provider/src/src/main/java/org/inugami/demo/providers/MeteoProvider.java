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

import java.util.List;

import org.inugami.api.exceptions.services.ProviderException;
import org.inugami.api.models.Gav;
import org.inugami.api.models.events.SimpleEvent;
import org.inugami.api.processors.ClassBehavior;
import org.inugami.api.processors.ConfigHandler;
import org.inugami.api.providers.AbstractProvider;
import org.inugami.api.providers.Provider;
import org.inugami.api.providers.ProviderRunner;
import org.inugami.api.providers.ProviderWithHttpConnector;
import org.inugami.api.providers.concurrent.FutureData;
import org.inugami.api.providers.concurrent.FutureDataBuilder;
import org.inugami.api.providers.task.ProviderFutureResult;
import org.inugami.commons.providers.MockJsonHelper;
import org.inugami.commons.spi.SpiLoader;
import org.inugami.core.context.ContextSPI;
import org.inugami.core.services.connectors.HttpConnector;

public class MeteoProvider extends AbstractProvider implements Provider, ProviderWithHttpConnector {
    // =========================================================================
    // ATTRIBUTES
    // =========================================================================
    private final FutureData<ProviderFutureResult> futureDataRef;
    
    private final ConfigHandler<String, String>    config;
    
    private final long                             timeout;
    
    private final HttpConnector                    httpConnector;
    
    private final String                           TYPE = "CUSTOM";
    
    private final String                           name;
    
    // =========================================================================
    // CONSTRUCTORS
    // =========================================================================
    public MeteoProvider(ClassBehavior classBehavior, ConfigHandler<String, String> config,
                          ProviderRunner providerRunner) {
        super(classBehavior, config, providerRunner);
        final ContextSPI ctx = new SpiLoader().loadSpiSingleService(ContextSPI.class);
        
        name = classBehavior.getName();
        this.config = config;
        timeout = Long.parseLong(config.grabOrDefault("timeout", "5000"));
        futureDataRef = FutureDataBuilder.buildDefaultFuture(timeout);
        
        //@formatter:off
        httpConnector = ctx.getHttpConnector(classBehavior.getClassName(),
                                             getMaxConnections(config, 10),
                                             getTimeout(config, 14500),
                                             getTTL(config, 500),
                                             getMaxPerRoute(config, 50),
                                             getSocketTimeout(config, 60000));
        //@formatter:on
    }
    // =========================================================================
    // METHODS
    // =========================================================================
    
    @Override
    public <T extends SimpleEvent> FutureData<ProviderFutureResult> callEvent(T event, Gav pluginGav) {
        final MeteoProviderTask task = new MeteoProviderTask(event, pluginGav, httpConnector,config);
        return runTask(task, event, futureDataRef);
    }
    
    @Override
    public ProviderFutureResult aggregate(List<ProviderFutureResult> data) throws ProviderException {
        return MockJsonHelper.aggregate(data);
    }
    
    @Override
    public String getType() {
        return TYPE;
    }
}
