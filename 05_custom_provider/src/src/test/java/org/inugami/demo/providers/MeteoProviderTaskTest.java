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

import java.util.HashMap;
import java.util.Map;

import org.inugami.api.models.Gav;
import org.inugami.api.models.events.SimpleEvent;
import org.inugami.api.models.events.SimpleEventBuilder;
import org.inugami.configuration.services.ConfigHandlerHashMap;
import org.inugami.core.context.ApplicationContext;
import org.inugami.core.context.Context;
import org.inugami.core.services.connectors.HttpConnector;
import org.junit.Ignore;
import org.junit.Test;

public class MeteoProviderTaskTest {
    // =========================================================================
    // ATTRIBUTES
    // =========================================================================
    private final static Gav GAV = new Gav("org.inugami.demo", "example-plugin");
    // =========================================================================
    // CONSTRUCTORS
    // =========================================================================
    
    // =========================================================================
    // METHODS
    // =========================================================================
    @Ignore("Integration test")
    @Test
    public void testCallProvider() throws Exception {
        
        final ApplicationContext ctx = Context.initializeStandalone();
        //@formatter:off
        final HttpConnector httpConnector = ctx.getHttpConnector(
                                                     MeteoProviderTaskTest.class.getSimpleName(),
                                                     10,
                                                     5000,
                                                     500,
                                                     50,
                                                     6000);
        //@formatter:on
        final Map<String, String> properties = new HashMap<>();
        properties.put("baseUrl", "https://www.prevision-meteo.ch/services/json");
        
        final MeteoProviderTask task = new MeteoProviderTask(buildEvent(), GAV, httpConnector,
                                                             new ConfigHandlerHashMap(properties));
        
        task.call();
        
        ctx.forceShutdownSubContext();
    }
    
    // =========================================================================
    // BUILDER
    // =========================================================================
    private SimpleEvent buildEvent() {
        //@formatter:off
        return new SimpleEventBuilder()
                    .addQuery("{{baseUrl}}/geneve")
                    .build();
        //@formatter:on
    }
}
