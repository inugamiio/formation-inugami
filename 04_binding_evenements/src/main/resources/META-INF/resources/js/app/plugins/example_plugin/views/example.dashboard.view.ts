// ANGULAR CORE MODULES --------------------------------------------------------
import {Component, AfterViewInit }                     from '@angular/core';

import {SessionScope}      from 'js/app/scopes/session.scope';
import {PluginMenuService} from './../services/plugin.menu.service';
import {PluginsService}    from 'js/app/services/plugins.service';

import {SSE}               from 'js/app/angular/sse';
import {EVENTS}            from 'js/app/angular/events';

// SERVICES --------------------------------------------------------------------
@Component({
    templateUrl: 'js/app/plugins/example_plugin/views/example.dashboard.view.html',
    directives : [
    ]
})
export class ExampleDashboardView implements AfterViewInit{
  /**************************************************************************
  * ATTRIBUTES
  **************************************************************************/
  private pluginName : string = "example_plugin";


  /**************************************************************************
  * CONSTRUCTORS
  **************************************************************************/
  constructor(private session:SessionScope,
              private pluginMenuService : PluginMenuService,
              private pluginService : PluginsService){
  }

  ngAfterViewInit() {
      this.pluginMenuService.buildMenu();
      this.session.closeMainMenu();
  
      SSE.register(this.pluginName);
      EVENTS.defaultEvents.onSseOpenOrAlreadyOpen.subscribe(()=>{
        this.pluginService.callPluginEventsProcessingBaseName(this.pluginName,null);
        EVENTS.updateResize();
      });
  }

  toggleMenu(){
      this.session.toggleMainMenu();
  }
  
}