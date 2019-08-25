// ANGULAR CORE MODULES --------------------------------------------------------
import {Component, OnInit}                     from '@angular/core';

import {SessionScope} from 'js/app/scopes/session.scope';
import {PluginMenuService} from './../services/plugin.menu.service';



// SERVICES --------------------------------------------------------------------
@Component({
    templateUrl: 'js/app/plugins/example_plugin/views/example.dashboard.view.html',
    directives : [
    ]
})
export class ExampleDashboardView implements OnInit{
  /**************************************************************************
  * ATTRIBUTES
  **************************************************************************/



  /**************************************************************************
  * CONSTRUCTORS
  **************************************************************************/
  constructor(private session:SessionScope,
              private pluginMenuService : PluginMenuService){
  }

  ngOnInit() {
      this.pluginMenuService.buildMenu();
      this.session.closeMainMenu();
  }

  toggleMenu(){
      this.session.toggleMainMenu();
  }
  
}